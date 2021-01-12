(function () {
    'use strict';

    angular
        .module('app')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('recuperar/init', {
                parent: 'public',
                url: '/recuperar-contrasena',
                data: {
                    pageTitle: 'recuperar.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/public/cuenta/recuperar/recuperar.init.html',
                        controller: 'RecuperarInitController',
                        controllerAs: 'ctrl'
                    }
                }
            })
            .state('recuperar/finish', {
                parent: 'public',
                url: '/nueva-contrasena?key&email',
                data: {
                    pageTitle: 'recuperar.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/public/cuenta/recuperar/recuperar.finish.html',
                        controller: 'RecuperarFinishController',
                        controllerAs: 'ctrl'
                    }
                }
            });
    }
})();
