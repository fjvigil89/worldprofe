(function (angular) {
    'use strict';

    /**
     * Añade un checkbox para mostrar/ocultar los usuarios eliminados.
     */
    angular
        .module('app')
        .component('usuarioGestionEliminados', {
            bindings: {
                filter: '<'
            },
            templateUrl: 'app/components/usuariogestion/usuario-gestion-eliminados.html',
            controllerAs: 'ctrl',
            /* @ngInject */
            controller: function () {
                var vm = this;

                vm.onChangeEliminados = function () {
                    if (vm.eliminados) {
                        vm.filter.activos = null;
                    } else {
                        vm.filter.activos = true;
                    }
                };

            }
        });
})(angular);
