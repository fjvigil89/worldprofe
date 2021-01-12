(function () {
    'use strict';

    angular
        .module('app')
        .config(stateConfig);

    /* @ngInject */
    function stateConfig($stateProvider) {
        $stateProvider
            .state('miespacio/miscompras', {
                parent: 'miespacio',
                url: '/mis-compras',
                data: {
                    authorities: ['ROLE_ALUMNO'],
                    pageTitle: 'global.menu.miespacio.title'
                },
                views: {
                    'contentEspacio': {
                        template: '<compra-list usuario="$resolve.usuario"></compra-list>',
                        controllerAs: 'ctrl',
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
                },
                resolve: {
                    /* @ngInject */
                    usuario: function (Principal) {
                        return Principal.identity().then(function (data) {
                            return data;
                        });
                    }
                }
            })
    }
})();
