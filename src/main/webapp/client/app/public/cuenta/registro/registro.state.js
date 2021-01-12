(function () {
    'use strict';

    angular
        .module('app')
        .config(stateConfig);

    // Solo se permite el tipo de registro 'ROLE_ALUMNO'

    /* @ngInject */
    function stateConfig($stateProvider) {
        $stateProvider
            .state('registro/info', {
                parent: 'public',
                url: '/entrar',
                data: {
                    authorities: ['ROLE_ANONYMOUS'],
                    pageTitle: 'global.menu.registro'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/public/cuenta/registro/registro.info.html'
                    }
                }
            })
            .state('registro', {
                parent: 'public',
                url: '/registro/:tipo',
                data: {
                    pageTitle: 'global.menu.registro'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/public/cuenta/registro/registro.html',
                        controller: 'RegistroController',
                        controllerAs: 'ctrl'
                    }
                }
            });
    }
})();
