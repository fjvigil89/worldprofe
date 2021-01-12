(function () {
    'use strict';

    angular.module('app')
        .factory('EnumService', EnumService);

    /* @ngInject */
    function EnumService($http, $q) {
        var cache = {};

        return {
            get: get
        };

        function get(tipo) {
            if (cache[tipo]) {
                var defered = $q.defer();
                var promise = defered.promise;
                defered.resolve(cache[tipo]);
                return promise;
            }

            // FIXME: Así sea cachea durante la sesión. Una vez se recarga la página la caché se pierde. Se puede también cachear la petición cambiando la URL base (lo que está en /api no se cachea)
            return $http.get('api/public/enum/' + tipo, {cache: true}).then(function (result) {
                cache[tipo] = result.data;
                return result.data;
            });
        }
    }
})();
