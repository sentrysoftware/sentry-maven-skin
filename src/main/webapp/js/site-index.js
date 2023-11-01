/*-
 * ╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲╱╲
 * Sentry Maven Skin
 * ჻჻჻჻჻჻
 * Copyright 2017 - 2023 Sentry Software
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
