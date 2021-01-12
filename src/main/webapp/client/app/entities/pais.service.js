(function () {
    'use strict';

    angular.module('app')
        .factory('Pais', Service);

    /* @ngInject */
    function Service(ResourceHelper) {
        return ResourceHelper.getResource("pais", {}, {
            'getAllWithProfesores': {
                method: 'GET',
                url: 'api/public/pais/getAllWithProfesores',
                isArray: true
            }
        });
    }

})();
