(function () {
    'use strict';

    angular
        .module('app')
        .config(stateConfig);

    /* @ngInject */
    function stateConfig($stateProvider) {
        var base = "paquetecreditos";

        $stateProvider
            .state(base, {
                parent: 'public',
                url: '/creditos',
                redirectTo: base + '/list',
                data: {
                    authorities: ['ROLE_ANONYMOUS', 'ROLE_ALUMNO'],
                    pageTitle: 'global.paquetescreditos'
                }
            })
            .state(base + '/list', {
                parent: base,
                url: '/listar',
                views: {
                    'content@': {
                        controllerAs: 'ctrl',
                        template: '<paquetecreditos-list></paquetecreditos-list>',
                        controller: function ($timeout, $state, Principal) {
                            $timeout(function () {
                                Principal.identity().then(function (data) {
                                    // Si es un alumno con empresa asociada, redirigimos a inicio
                                    if (data && data.rol === 'ROLE_ALUMNO' && data.empresa != null) {
                                        $state.go("home");
                                    }
                                });
                            });
                        }
                    }
                }
            })
            .state(base + '/comprar', {
                parent: base,
                url: '/comprar/:paqueteId',
                views: {
                    'content@': {
                        controllerAs: 'ctrl',
                        template: '<paquetecreditos-comprar paquete-creditos="$resolve.paqueteCreditos" ' +
                            'datos-facturacion="$resolve.datosFacturacion"></paquetecreditos-comprar>'
                    }
                },
                resolve: {
                    /* @ngInject */
                    paqueteId: function ($stateParams) {
                        return $stateParams.paqueteId;
                    },
                    /* @ngInject */
                    paqueteCreditos: function (paqueteId, Paquetecreditos) {
                        return Paquetecreditos.get({id: paqueteId}).$promise;
                    },
                    /* @ngInject */
                    datosFacturacion: function (DatosFacturacion) {
                        return DatosFacturacion.getDatosFacturacionDeUsuario().$promise;
                    }
                }
            });
    }
})();
