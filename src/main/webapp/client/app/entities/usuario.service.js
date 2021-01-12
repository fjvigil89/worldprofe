(function () {
    'use strict';

    angular.module('app')
        .factory('Usuario', Usuario);

    /* @ngInject */
    function Usuario(ResourceHelper) {
        return ResourceHelper.getResource("usuario", {}, {
            'register': {
                method: 'POST',
                url: 'api/public/perfil/register',
                isArray: false
            },
            'edit': {
                method: 'POST',
                url: 'api/public/perfil/edit',
                isArray: false
            },
            'queryByEmail': {
                method: 'GET',
                url: 'api/public/perfil/queryByEmail',
                isArray: true
            },
            'queryByLogin': {
                method: 'GET',
                url: 'api/public/perfil/queryByLogin',
                isArray: true
            },
            'queryCentro': {
                method: 'GET',
                url: 'api/public/perfil/centro/query',
                isArray: true
            }
        });
    }

})();
