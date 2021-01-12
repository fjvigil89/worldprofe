(function () {
    'use strict';

    angular
        .module('app')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('validar', {
                parent: 'public',
                url: '/validar?key',
                data: {
                    pageTitle: 'global.menu.activar'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/public/cuenta/validar/validar.html',
                        controller: 'ValidarController',
                        controllerAs: 'ctrl'
                    }
                }
            });
    }
})();
