(function (angular) {
    'use strict';

    angular
        .module('app')
        .controller('ConfiguracionController', ConfiguracionController);

    /* @ngInject */
    function ConfiguracionController(configuracion, Configuracion, AlertService) {
        var vm = this;
        vm.configuracion = configuracion;

        vm.save = function() {
            Configuracion.save(vm.configuracion).$promise;
        }

    }

})(angular);
