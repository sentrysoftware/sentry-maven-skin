angular.module("sentry.site").factory("siteIndex", ["$http", "$q", "RELATIVE_ROOT", function($http, $q, RELATIVE_ROOT) {

	/**
	 * The index
	 **/
	var index = null;

	/**
	 * get
	 **/
	function get() {

		var deferred = $q.defer();
		if (index) {

			// Already got it, return the classMap (sort of Cache)
			deferred.resolve(index);

		} else {

			// First time, so go fetch!

			$http.get(RELATIVE_ROOT + "/index.json", { responseType: "json" }).then(function(response) {

				// Store the index
				index = elasticlunr.Index.load(response.data);

				// Resolve the deferred
				deferred.resolve(index);
			});
		}

		// Return the promise
		return deferred.promise;
	}

	return {
		get: get
	};
}]);
