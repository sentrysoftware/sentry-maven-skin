/**
 * zoomable-img
 *
 * A component that shows picture as a thumbnail
 * and expands it to full size when clicked
 **/

angular.module("sentry.site").directive("zoomableImg", function() {
	return {
		restrict: "A",
		scope: {
			src: "@",
			alt: "@"
		},
		replace: true,
		link: {
			post: function postLink(scope, element) {
				// For the smoothest animation, we will need the real picture size
				// We'll get its size only once it's loaded
				element.find("img").one("load", function(event) {
					scope.naturalHeight = event.target.naturalHeight;
					scope.naturalWidth = event.target.naturalWidth;
					scope.$apply();
				});
			}
		},
		template: "<div class='zoomable-img' ng-class='{zoomed: zoomed}'>" +
							"<img ng-src='{{src}}' alt='{{alt}}' ng-style='{ \"max-height\": zoomed ? naturalHeight + \"px\" : \"150px\" }' ng-click='zoomed = !zoomed'>" +
							"<div ng-if='alt' class='zoomable-img-title' ng-style='{ width: naturalWidth + \"px\" }'>{{alt}}</div>" +
							"</div>"
	};
});
