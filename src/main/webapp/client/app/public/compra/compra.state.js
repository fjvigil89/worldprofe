(function () {
    'use strict';

    angular
        .module('app')
        .config(stateConfig);

    /* @ngInject */
    function stateConfig($stateProvider) {
        $stateProvider.state('compra', {
            parent: 'public',
            redirectTo: 'compra/get',
            url: '/compra',
            params: {
                compra: null
            },
            data: {
                authorities: ['ROLE_AUTHENTICATED'],
                pageTitle: 'global.menu.compra'
            }
        }).state('compra/crear', {
            parent: 'compra',
            params: {
                paquetecreditosId: null,
                solicitarFactura: false,
                usuario: null
            },
            views: {
                'content@': {
                    controllerAs: 'ctrl',
                    template: '<div class="text-center">' +
                        '<div class="lead" translate="comun.cargando"></div>' +
                        '<loading is-loading="true" class="text-center"></divloading></div>'
                }
            },
            resolve: {
                /* @ngInject */
                foo: function ($state, $stateParams, Compra, $window, AlertService) {
                    console.log("Creando compra:", $stateParams);
                    Compra.crear({
                        paquetecreditosId: $stateParams.paquetecreditosId,
                        solicitarFactura: $stateParams.solicitarFactura
                    }).$promise.then(function (result) {
                        Compra.iniciarPaypal({compraId: result.id}).$promise.then(function (result) {
                            if (!result.msg) {
                                // Mensaje de error
                                AlertService.error("compra.error.paypal");
                            } else {
                                $window.location.href = result.msg;
                            }
                        });
                    });
                }
            }
        }).state('compra/paypal', {
            parent: 'compra',
            url: '/confirmar-paypal?compraId&paymentId&token',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/public/compra/compra.success.html',
                    /* @ngInject */
                    controller: function ($state, $stateParams, Compra, Principal) {
                        var vm = this;
                        vm.loading = true;
                        console.log("Finalizando compra");

                        Compra.confirmarPaypal({
                            compraId: $stateParams.compraId,
                            paymentId: $stateParams.paymentId,
                            token: $stateParams.token
                        }).$promise.then(function (result) {
                            vm.loading = false;
                            console.log("Compra terminada:" + result);
                            Principal.identity(true).then(function (data) {
                                $state.go("miespacio/miscompras");
                            });
                        }, function () {
                            vm.loading = false;
                            vm.error = true;
                        });
                    },
                    controllerAs: 'ctrl'
                }
            }
        }).state('compra/cancel', {
            parent: 'compra',
            url: '/cancelar?compraId&token',
            views: {
                'content@': {
                    templateUrl: 'app/public/compra/compra.success.html',
                    /* @ngInject */
                    controller: function ($state, $stateParams, Compra, AlertService) {
                        console.log("Cancelando compra");

                        Compra.cancelar({compraId: $stateParams.compraId}).$promise.then(function (result) {
                            console.log("Compra cancelada: " + result);
                            AlertService.warning("compra.cancel");
                            $state.go('paquetecreditos');
                        });
                    },
                    controllerAs: 'ctrl'
                }
            }
        });
    }
})();
