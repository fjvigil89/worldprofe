(function () {
    'use strict';

    angular.module('app')
        .factory('Estatica', Service);

    /* @ngInject */
    function Service($resource) {
        return $resource('api/admin/estaticas/:id', {}, {
            'get': {
                method: 'GET',
                url: 'api/public/estaticas/:id'
            },
            'getByNombre': {
                method: 'GET',
                url: 'api/public/estaticas/nombre/:nombre'
            },
            'getByNombreIdioma': {
                method: 'GET',
                url: 'api/public/estaticas/getByNombreIdioma'
            },
            'findAll': {
                method: 'GET',
                isArray: true,
                url: 'api/public/estaticas/findAll'
            }
        });
    }
})();
