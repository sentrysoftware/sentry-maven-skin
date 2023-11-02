/*-
 * ╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲
 * Sentry Maven Skin
 * ჻჻჻჻჻჻
 * Copyright (C) 2017 - 2023 Sentry Software
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
 * zoomable
 *
 * A component that shows picture as a thumbnail
 * and expands it to full size when clicked
 **/

(function() {

	function zoomableController($element, $scope, $window) {

		var $ctrl = this;

		/**
		 * Initialization
		 */
		$ctrl.$postLink = function() {

			// Add a click handler on the component element itself
			$element.on("click", function() { $ctrl.zoomClick(); $scope.$applyAsync(); });

			// Find the image "alt" text, display it in a specific div, and remove it from alt
			var imgElement = $element.find("img");
			$ctrl.alt = imgElement.attr("alt");
			imgElement.attr("alt", "");

			// Initial state
			$ctrl.zoomed = false;
			$ctrl.fullScreen = false;

		};

		/**
		 * Click handler on the component element
		 */
		$ctrl.zoomClick = function() {

			// Toggle between zoomed and not-zoomed
			if (!$ctrl.zoomed) {

				// If 'xs' screen, go direct to full screen
				if ($window.matchMedia("(max-width: 576px)").matches) {
					$ctrl.enterFullScreen();
				} else {
					$ctrl.enterZoom();
				}


			} else {

				$ctrl.exitZoom();

			}

		};

		/**
		 * Enter zoom
		 */
		$ctrl.enterZoom = function() {

			$ctrl.zoomed = true;

			// Add the 'zoomed' class to the component element
			// to trigger some CSS magic
			$element.toggleClass("zoomed", $ctrl.zoomed);

		};

		/**
		 * Exit zoom (back to thumbnail)
		 */
		$ctrl.exitZoom = function() {

			$ctrl.zoomed = false;

			// Remove the 'zoomed' class and trigger some more
			// CSS magic
			$element.toggleClass("zoomed", $ctrl.zoomed);

		};

		/**
		 * Enter full screen (from zoomed to full screen)
		 *
		 * @param {object} $event Event that triggered the full screen and which we want not to propagate
		 */
		$ctrl.enterFullScreen = function($event) {

			// Prevent the event from propagating (and its default behavior if any)
			if ($event) {
				$event.preventDefault();
				$event.stopPropagation();
			}

			$ctrl.fullScreen = true;

			// Move the <DIV> element under the component element to <BODY> to avoid z-index issues
			// (make sure the backdrop is on top of everything else) and add the 'full-screen' class
			// to trigger some magic.
			$ctrl.modal = $element.children();
			$ctrl.modal.detach();
			$ctrl.modal.toggleClass("full-screen", true);
			angular.element($window.document.body).append($ctrl.modal);

			// Add the keyboard handler
			angular.element($window).on("keydown", $ctrl.fullScreenKeyboard);

			// Update the component state so that we'll get the thumbnail when closing the full-screen
			$ctrl.exitZoom();

		};

		/**
		 * Exit fullscreen and get back to normal state (thumbnail)
		 *
		 * @param {object} $event Event we need to stop propagating
		 */
		$ctrl.exitFullScreen = function($event) {

			// Prevent propagation
			$event.stopPropagation();

			$ctrl.fullScreen = false;

			// Restore the <DIV> where it belongs, i.e. under our component element
			// And remove the full-screen CSS class
			$ctrl.modal.detach();
			$ctrl.modal.toggleClass("full-screen", false);
			$element.append($ctrl.modal);

			// Remove the keyboard handler
			angular.element($window).off("keydown", $ctrl.fullScreenKeyboard);

		};

		/**
		 * Handler for the click event on the backdrop.
		 *
		 * @param {object} $event Click event
		 */
		$ctrl.fullScreenClick = function($event) {

			// Well, if we're not in full-screen, do nothing
			if (!$ctrl.fullScreen) {
				return;
			}

			// Prevent propagation of the event
			$event.stopPropagation();

			// Exit full-screen if and only if the click was in the backdrop
			// (not on the image, or the title)
			if ($event.target.className == "zoomable-shadow") {
				$ctrl.exitFullScreen($event);
			}

		};

		/**
		 * Handler for the keyboard events on the backdrop.
		 *
		 * @param {object} $event Keyboard event
		 */
		$ctrl.fullScreenKeyboard = function($event) {

			// Well, if we're not in full-screen, do nothing
			if (!$ctrl.fullScreen) {
				return;
			}

			// Which key was pressed?
			if ($event.key == "Escape") {

				// [Esc] exits full screen
				$ctrl.exitFullScreen($event);
				$scope.$applyAsync();

			} else if ($event.key == "Tab") {

				// Prevent tab from doing anything

			} else {
				// Do nothing and don't prevent the keyboard event from bubbling
				return;
			}

			// Prevent the keyboard event from bubbling to other handlers
			$event.preventDefault();
			$event.stopPropagation();

		};

	}
	zoomableController.$inject = ["$element", "$scope", "$window"];

	angular.module("sentry.site").component("zoomable", {
		templateUrl: "zoomable.html",
		transclude: true,
		controller: zoomableController,
		bindings: {}
	});
})();
