(function () {
    'use strict';

    angular.module('app')
        .factory('ResourceHelper', ResourceHelper);

    // La ruta para las operaciones de crear, actualizar y eliminar es la de admin.

    /* @ngInject */
    function ResourceHelper($resource) {

        return {
            getResource: getResource
        };

        function getResource(entidad, params, newFunctions, publico) {
            var functions = {
                'query': {
                    method: 'GET',
                    url: 'api/public/' + entidad + '/query',
                    isArray: true
                },
                'get': {
                    method: 'GET',
                    url: 'api/public/' + entidad + '/:id',
                    isArray: false
                },
                'findAll': {
                    method: 'GET',
                    url: 'api/public/' + entidad + '/findAll',
                    isArray: false
                }
            };
            angular.extend(functions, newFunctions);

            var baseUrl = 'admin';
            if (publico) {
                baseUrl = 'public';
            }

            return $resource('api/' + baseUrl + '/' + entidad + '/:id', params, functions);
        }
    }
})();
