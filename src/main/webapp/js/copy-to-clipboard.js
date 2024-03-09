/*-
 * ╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲
 * Sentry Maven Skin
 * ჻჻჻჻჻჻
 * Copyright 2017 - 2024 Sentry Software
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
(function() {
	/**
	 * Directive: copy-to-clipboard
	 * Description: Copy to clipboard content set in element
	 * The copied element is triggered when clicking on a specific button 'btnEl' created by the directive
	 * Usage: <pre copy-to-clipboard>Some Content</pre>
	 **/
	angular.module("sentry.site").directive("copyToClipboard", ["$document", "$window", "$timeout", function ($document, $window, $timeout) {
		return {
			restrict: "A",
			link: function(scope, element) {

				// Wrap element into a div containing the element and a 'fas fa-clipboard' button
				var wrapper = element.wrap("<div class='copy-to-clipboard'></div>").parent();

				// Create the button 'btnEl' and append it to wrapper
				var btnEl = angular.element("<button type='button' title='Copy to clipboard' class='btn btn-default hidden-xs'><i class='fas fa-clipboard'></i> Copy</button>");
				wrapper.append(btnEl);

				// Copy element's content on click on btnEl
				btnEl.on("click", function() {
					var range = $document[0].createRange();
					range.selectNode(element[0]);
					$window.getSelection().removeAllRanges();
					$window.getSelection().addRange(range);
					$document[0].execCommand("copy");
					$window.getSelection().removeAllRanges();
					var btnContent = btnEl.html();
					btnEl.removeClass("btn-default");
					btnEl.addClass("btn-success");
					btnEl.html("<i class='fa-solid fa-check'></i> Copied!");
					$timeout(function() { btnEl.html(btnContent); btnEl.removeClass("btn-success"); btnEl.addClass("btn-default"); }, 1000);
				});

				// We're going to select all content when the element gets focus
				// BUT NOT when the focus comes from a click our touch event
				var preventSelectAll = false;

				// Listen for mousedown and touchstart events to set the flag
				element.on("mousedown touchstart", function() {
					preventSelectAll = true;
				});

				// On focus of the <pre> element, select all, for easy copy-paste
				element.on("focus", function() {
					if (!preventSelectAll) {
						var range = $document[0].createRange();
						range.selectNode(element[0]);
						$window.getSelection().removeAllRanges();
						$window.getSelection().addRange(range);
					}
					preventSelectAll = false;
				});
			}
		};
	}])
})();
