(function () {
    'use strict';

    angular.module('app')
        .factory('Capacidad', Service);

    /* @ngInject */
    function Service(ResourceHelper) {
        return ResourceHelper.getResource("capacidad");
    }

})();
