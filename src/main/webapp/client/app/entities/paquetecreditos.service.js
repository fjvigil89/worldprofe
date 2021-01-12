(function () {
    'use strict';

    angular.module('app')
        .factory('Paquetecreditos', Paquetecreditos);

    /* @ngInject */
    function Paquetecreditos(ResourceHelper) {
        return ResourceHelper.getResource("paquetecreditos", {}, {
        });
    }

})();
