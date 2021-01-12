(function () {
    'use strict';

    angular.module('app')
        .factory('Nivel', Service);

    /* @ngInject */
    function Service(ResourceHelper) {
        return ResourceHelper.getResource("nivel");
    }

})();
