(function () {
    'use strict';

    angular
        .module('app')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('error', {
                parent: 'site',
                url: '/error',
                data: {
                    authorities: [],
                    pageTitle: 'error.error.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/layouts/error/error.html'
                    }
                },
                ncyBreadcrumb: {
                    skip: true
                }
            })
            .state('accessdenied', {
                parent: 'public',
                url: '/acceso-denegado',
                data: {
                    authorities: [],
                    pageTitle: 'error.accessdenied.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/layouts/error/accessdenied.html'
                    }
                },
                ncyBreadcrumb: {
                    skip: true
                }
            });
    }
})();
