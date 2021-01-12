(function () {
    'use strict';

    angular.module('app')
        .factory('Idioma', Service);

    /* @ngInject */
    function Service(ResourceHelper) {
        return ResourceHelper.getResource("idioma");
    }

})();
