(function () {
    'use strict';

    angular.module('app')
        .factory('EstaticaIdiomaService', EstaticaIdiomaService);

    /* @ngInject */
    function EstaticaIdiomaService($resource) {
        return $resource('api/public/estaticas/idioma', {}, {
            'get': {
                method: 'GET',
                isArray: true
            }
        });
    }
})();
