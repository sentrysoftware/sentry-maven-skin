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
	angular.module("sentry.site").directive("copyToClipboard", ["$document", "$window", function ($document, $window) {
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
				});
				// On focus of the <pre> element, select all, for easy copy-paste
				element.on("focus", function() {
					var range = $document[0].createRange();
					range.selectNode(element[0]);
					$window.getSelection().removeAllRanges();
					$window.getSelection().addRange(range);
				});
			}
		};
	}])
})();
