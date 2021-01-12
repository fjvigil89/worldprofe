(function () {
    'use strict';

    angular
        .module('app')
        .component('paquetecreditosComprar', {
            templateUrl: 'app/public/components/paquetecreditos/paquetecreditos-comprar.html',
            controller: Controller,
            controllerAs: 'ctrl',
            bindings: {
                paqueteCreditos: '<',
                datosFacturacion: '<'
            }
        });

    /* @ngInject */
    function Controller($state, $stateParams, NG_TABLE_DEFAULT_PARAMS, NgTableParams, NgTableHelper,
                        ModalService, Paquetecreditos, Principal, DatosFacturacion, Auth) {
        var vm = this;

        vm.solicitarFactura = false;

        vm.item = {datosFacturacion: vm.datosFacturacion};

        Principal.identity().then(function (data) {
            vm.usuarioAutenticado = data;
        });

        vm.comprar = function () {
            if (vm.usuarioAutenticado == null) {
                Auth.authorize(false, "ROLE_AUTHENTICATED");
            } else {
                if (vm.solicitarFactura) {
                    DatosFacturacion.save(vm.item.datosFacturacion).$promise.then(function () {
                        comprar();
                    });
                } else {
                    comprar();
                }
            }
        };

        function comprar() {
            $state.go('compra/crear', {
                paquetecreditosId: vm.paqueteCreditos.id,
                usuario: vm.usuarioAutenticado,
                solicitarFactura: vm.solicitarFactura
            });
        }

    }
})();
