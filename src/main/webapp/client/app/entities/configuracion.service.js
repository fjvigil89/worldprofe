(function () {
    'use strict';

    angular.module('app')
        .factory('Configuracion', Service);

    /* @ngInject */
    function Service(ResourceHelper) {
        return ResourceHelper.getResource("configuracion");
    }

})();
