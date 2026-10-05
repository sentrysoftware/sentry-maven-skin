/*-
 * ╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲
 * Sentry Maven Skin
 * ჻჻჻჻჻჻
 * Copyright (C) 2017 - 2024 Sentry Software
 * ჻჻჻჻჻჻
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱
 */
/**
 * The site AngularJS application
 **/
angular.module("sentry.site", ["ngAnimate", "ngSanitize", "matchMediaLight", "duScroll", "ui.toggle", "ui.bootstrap"]);
angular.module("sentry.site").value("duScrollGreedy", true);

/**
 * Protect document literals before Angular compiles descendants. Skin-generated
 * component templates and the search panel remain outside this protection.
 **/
angular.module("sentry.site").directive("sentryProtectAngular", function () {
	return {
		compile: function (element) {
			var root = element[0];
			var document = root.ownerDocument;
			root.querySelectorAll("code").forEach(function (code) {
				code.setAttribute("ng-non-bindable", "");
			});
			root.querySelectorAll("*").forEach(function (node) {
				var literals = Object.create(null);
				Array.from(node.attributes).forEach(function (attribute) {
					if (attribute.value.indexOf("{{") !== -1) {
						literals[attribute.name] = attribute.value;
						attribute.value = "";
					}
				});
				if (Object.keys(literals).length) {
					// No braces in the marker: Angular interpolates every attribute.
					node.setAttribute("sentry-literal-attributes", encodeURIComponent(JSON.stringify(literals)));
				}
			});
			var walker = document.createTreeWalker(root, NodeFilter.SHOW_TEXT);
			var text;
			while ((text = walker.nextNode())) {
				if (text.parentElement.closest("code, script, style")) {
					continue;
				}
				var index = text.nodeValue.indexOf("{{");
				if (index !== -1) {
					// A comment separates the braces without changing visible text.
					var remainder = text.splitText(index + 1);
					text.parentNode.insertBefore(document.createComment(""), remainder);
				}
			}
		}
	};
});

angular.module("sentry.site").directive("sentryLiteralAttributes", function () {
	return {
		// Restore after directive collection, before ng-non-bindable/components.
		priority: 1001,
		compile: function (element, attrs) {
			var literals = JSON.parse(decodeURIComponent(attrs.sentryLiteralAttributes));
			Object.keys(attrs.$attr).forEach(function (name) {
				var attribute = attrs.$attr[name];
				if (Object.prototype.hasOwnProperty.call(literals, attribute)) {
					attrs.$set(name, literals[attribute]);
				}
			});
			element.removeAttr("sentry-literal-attributes");
		}
	};
});

/**
 * Prevent animations for anything not marked with the class "animate"
 * Exception: carousel elements need animations to work properly
 * - "carousel" class is added to the carousel container
 * - "item" class is added to each slide (where animations actually happen)
 **/
angular.module("sentry.site").config([
	"$animateProvider",
	function ($animateProvider) {
		$animateProvider.classNameFilter(/animate|carousel|item/);
	}
]);

/**
 * The main controller
 **/
angular.module("sentry.site").controller("siteController", [
	"$scope",
	"$location",
	"siteIndex",
	"$document",
	"$anchorScroll",
	"RELATIVE_ROOT",
	"SITE_MENU",
	"$timeout",
	"$rootScope",
	"mediaWatcher",
	function (
		$scope,
		$location,
		siteIndex,
		$document,
		$anchorScroll,
		RELATIVE_ROOT,
		SITE_MENU,
		$timeout,
		$rootScope,
		mediaWatcher
	) {
		/**
		 * initialize
		 **/
		$scope.initialize = function () {
			// What to highlight?
			$scope.mark = new Mark(".search-results-content");

			// Watch changes in search value in the URL
			$scope.$on("$locationChangeSuccess", function () {
				if ($scope.siteSearch != $location.search().search) {
					// If different from what we have, then update the search information of the controller
					$scope.siteSearch = $location.search().search;

					// If needed, do search
					if ($scope.siteSearch) {
						$scope.showSearchInput = true;
						$scope.search($scope.siteSearch);
					}
				}
			});

			// Setup the screen size listener, so that the HTML can leverage this, the AngularJS way
			// $scope.media = screenSize.onRuleChange($scope, function(media) {
			// 	$scope.media = media;
			// });

			// Watch scroll and show the "back-to-top" arrow if we are not at the top
			$document.on("scroll", function () {
				var prevValue = !!$scope.showBackToTop;
				$scope.showBackToTop = $document.scrollTop() > 500;
				// Trigger an AngularJS $digest only if value changed (as the "scroll" event may fire often and quickly)
				if ($scope.showBackToTop != prevValue) {
					$scope.$applyAsync("showBackToTop");
				}
			});

			// Initially, scroll to the proper hash tag specified in the URL
			$anchorScroll();
		};

		/**
		 * prefetchSearchIndex - Pre-fetches the search index when user focuses on search
		 **/
		$scope.prefetchSearchIndex = function () {
			siteIndex.prefetch();
		};

		/**
		 * search
		 **/
		$scope.search = function (what) {
			// Normalize the keywords being searched (this is for the highlighting)
			var keywords = elasticlunr.tokenizer(what).map(elasticlunr.trimmer).filter(elasticlunr.stopWordFilter);
			keywords = keywords.concat(keywords.map(elasticlunr.stemmer));

			if (keywords.length > 0) {
				// Build a regex that will search for any of these words
				var keywordsRegex = new RegExp(keywords.join("|"), "i");

				// Clear the highlighting
				$scope.mark.unmark();

				// Set loading state
				$scope.searchLoading = siteIndex.isLoading();

				siteIndex.get().then(
					function (index) {
						// Clear loading state
						$scope.searchLoading = false;

						$scope.resultArray = index
							.search(what, {
								bool: "AND",
								expand: true
							})
							.map(function (result) {
								// Get the doc details from the elasticlunr index (or a default doc, if not found)
								var doc = index.documentStore.docs[result.ref] || {
									id: result.ref,
									title: "Unknown",
									body: "Unknown",
									score: result.score
								};

								// Trim the title from the beginning of the document, as it's not necessary and is overfluous
								var body = doc.body;
								if (body.startsWith(doc.title)) {
									body = body.substring(doc.title.length);
								}

								// Search for the first occurrence of the searched words
								var pos = body.search(keywordsRegex) - 20;

								// Stay in reasonable boundaries, from beginning to 400 chars before the end
								if (pos > body.length - 400) {
									pos = body.length - 400;
								}
								if (pos < 0) {
									pos = 0;
								}
								if (pos > 0) {
									body = "..." + body.substring(pos);
								}

								return {
									href: RELATIVE_ROOT + "/" + doc.id,
									title: doc.title,
									body: body,
									score: result.score,
									parents: (
										SITE_MENU.find(function (i) {
											return i.path == doc.id;
										}) || {}
									).parents
								};
							});

						// Highlight these keywords... after the digest cycle is run,
						// so the DOM is ready with the result of the search
						$timeout(
							function () {
								$scope.mark.mark(keywords, { accuracy: "complementary", diacritics: true });
							},
							0,
							false
						);
					},
					function () {
						// Error fetching index
						$scope.searchLoading = false;
					}
				);

				// Update URL
				$location.search("search", what);
			} else {
				// Nothing to search, reset the results
				$scope.mark.unmark();
				$scope.resultArray = [];
				if (!what) {
					$location.search("search", null);
				}
			}
		};

		/**
		 * clearSearch
		 **/
		$scope.clearSearch = function () {
			$scope.siteSearch = "";
			$scope.search("");
		};

		/**
		 * clickNavToggle
		 **/
		$scope.clickNavToggle = function () {
			// Toggle
			$scope.isNavCollapsed = !$scope.isNavCollapsed;

			// If we collapse the nav bar, we clear the search
			if ($scope.isNavCollapsed) {
				$scope.clearSearch();
			}
		};

		/**
		 * backToTop
		 **/
		$scope.backToTop = function () {
			$document.scrollTopAnimated(0);
		};

		/**
		 * handleKeyboard
		 */
		$scope.handleKeyboard = function ($event) {
			if (($scope.siteSearch || $scope.showSearchInput) && ($event.key == "Cancel" || $event.key == "Escape")) {
				// Escape (erase input)
				$scope.clearSearch();
				$scope.showSearchInput = false;
			} else {
				return true;
			}

			// Make sure other actions are not taken by event bubbling
			$event.stopPropagation();
			$event.preventDefault();
			return false;
		};

		/**
		 * Switches color schemes between light and dark
		 */
		$scope.switchColors = function () {
			if ($rootScope.$matchMedia.dark) {
				mediaWatcher.forceDark();
			} else {
				mediaWatcher.forceLight();
			}
		};

		// Init
		$scope.initialize();
	}
]);

/**
 * Disable debug and other unnecessary AngularJS features
 **/

angular.module("sentry.site").config([
	"$compileProvider",
	function ($compileProvider) {
		$compileProvider.debugInfoEnabled(false);
		$compileProvider.commentDirectivesEnabled(false);
		$compileProvider.cssClassDirectivesEnabled(false);
	}
]);

/**
 * Directive: focus-me
 * Description: Sets the focus on the element with the focus-me='true' attribute
 * Usage: <input name="test" focus-me="true">
 **/
angular.module("sentry.site").directive("focusMe", [
	"$timeout",
	"$parse",
	function ($timeout, $parse) {
		return {
			link: function (scope, element, attrs) {
				var model = $parse(attrs.focusMe);
				scope.$watch(model, function (value) {
					if (value === true) {
						$timeout(function () {
							element[0].focus();
						});
					}
				});
			}
		};
	}
]);
