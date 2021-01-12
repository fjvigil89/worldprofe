(function () {
    'use strict';

    angular.module('app')
        .factory('ClaseLibre', ClaseLibre);

    /* @ngInject */
    function ClaseLibre(ResourceHelper) {
        return ResourceHelper.getResource("clase-libre", {}, {
            'save': {
                method: 'POST',
                url: 'api/public/clase-libre',
                isArray: false
            },
            'findAll': {
                method: 'GET',
                url: 'api/public/clase-libre/findAll',
                isArray: false
            },
            'subtract': {
                method: 'POST',
                url: 'api/public/clase-libre/subtract',
                isArray: false
            },
            'addlist': {
                method: 'POST',
                url: 'api/public/clase-libre/add-list',
                isArray: false
            },
            'duplicarsemana': {
                method: 'POST',
                url: 'api/public/clase-libre/duplicar-semana',
                isArray: false
            }
        });
    }

})();
