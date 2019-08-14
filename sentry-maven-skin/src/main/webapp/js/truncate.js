/**
 * truncate, a small filter
 * Example:
 * {{ 'abcdefghijklmnopqrstuvwxyz' | truncate:'3' }}
 * ==> abc...
 **/
angular.module("sentry.site").filter("truncate", [function() {

	return function(s, limit) {

		// Not a string?
		if (!angular.isString(s)) {
			return "";
		}

		// String already shorter than limit?
		if (s.length < limit) {
			return s;
		}

		// Truncate
		return s.substring(0, limit) + "...";
	};

}]);
