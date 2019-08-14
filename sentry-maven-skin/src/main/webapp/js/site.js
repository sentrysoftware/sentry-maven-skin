/**
 * The site AngularJS application
 **/
angular.module("sentry.site", ["ngAnimate", "ui.bootstrap", "matchMedia", "duScroll"]);

/**
 * Prevent animations for anything not marked with the class "animate"
 **/
angular.module("sentry.site").config(["$animateProvider", function($animateProvider) {
	$animateProvider.classNameFilter(/( |^)animate( |$)/);
}]);

/**
 * The main controller
 **/
angular.module("sentry.site").controller(
	"siteController",
	["$scope", "$http", "$location", "screenSize", "siteIndex", "$document", "$anchorScroll", "RELATIVE_ROOT", "$timeout",
	function($scope, $http, $location, screenSize, siteIndex, $document, $anchorScroll, RELATIVE_ROOT, $timeout) {

	/**
	 * initialize
	 **/
	$scope.initialize = function() {

		// What to highlight?
		$scope.mark = new Mark(".search-results-content");

		// Watch changes in search value in the URL
		$scope.$on("$locationChangeSuccess", function() {
			if ($scope.siteSearch != $location.search().search) {

				// If different from what we have, then update the search information of the controller
				$scope.siteSearch = $location.search().search;

				// If needed, do search
				if ($scope.siteSearch) {
					$scope.search($scope.siteSearch);
				}
			}
		});

		// If something is being searched, then the nav bar is not collapsed
		$scope.isNavCollapsed = !$scope.siteSearch;

		// Setup the screen size listener, so that the HTML can leverage this, the AngularJS way
		$scope.media = screenSize.onRuleChange($scope, function(media) {
			$scope.media = media;
		});

		// Watch scroll and show the "back-to-top" arrow if we are not at the top
		$document.on("scroll", function() {
			var prevValue = !!$scope.showBackToTop;
			$scope.showBackToTop = ($document.scrollTop() > 500);
			// Trigger an AngularJS $digest only if value changed (as the "scroll" event may fire often and quickly)
			if ($scope.showBackToTop != prevValue) {
				$scope.$applyAsync("showBackToTop");
			}
		});

		// Initially, scroll to the proper hash tag specified in the URL
		$anchorScroll();

	};

	/**
	 * search
	 **/
	$scope.search = function(what) {

		// Normalize the keywords being searched (this is for the highlighting)
		var keywords = elasticlunr
			.tokenizer(what)
			.map(elasticlunr.trimmer)
			.filter(elasticlunr.stopWordFilter);
		keywords = keywords.concat(keywords.map(elasticlunr.stemmer));

		if (keywords.length > 0) {

			// Build a regex that will search for any of these words
			var keywordsRegex = new RegExp(keywords.join("|"), "i");

			// Clear the highlighting
			$scope.mark.unmark();

			siteIndex.get().then(function(index) {

				$scope.resultArray = index.search(what, {
					bool: "AND",
					expand: true
				}).map(function(result) {

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
					if (pos > body.length - 400) { pos = body.length - 400; }
					if (pos < 0) { pos = 0; }
					if (pos > 0) {
						body = "..." + body.substring(pos);
					}


					return {
						href: RELATIVE_ROOT + "/" + doc.id,
						title: doc.title,
						body: body,
						score: result.score
					};
				});

				// Highlight these keywords... after the digest cycle is run,
				// so the DOM is ready with the result of the search
				$timeout(function() {
					$scope.mark.mark(keywords, { accuracy: "complementary", diacritics: true });
				}, 0, false);

			});

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
	 * siteSearchKeyDown
	 **/
	$scope.siteSearchKeyDown = function($event) {

		// Was the [ESC] key pressed?
		if ($scope.siteSearch && $event.key == "Escape") {

			// Clear
			$scope.clearSearch();

			// Important, prevent the default behavior of the browser
			$event.stopPropagation();
			$event.preventDefault();

		}

	};

	/**
	 * clearSearch
	 **/
	$scope.clearSearch = function() {
		$scope.siteSearch = "";
		$scope.search("");
	};

	/**
	 * clickNavToggle
	 **/
	$scope.clickNavToggle = function() {

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
	$scope.backToTop = function() {
		$document.scrollTopAnimated(0);
	};

	// Init
	$scope.initialize();

}]);


/**
 * Disable debug and other unnecessary AngularJS features
 **/

angular.module("sentry.site").config(["$compileProvider", function($compileProvider) {
	$compileProvider.debugInfoEnabled(false);
	$compileProvider.commentDirectivesEnabled(false);
	$compileProvider.cssClassDirectivesEnabled(false);
}]);