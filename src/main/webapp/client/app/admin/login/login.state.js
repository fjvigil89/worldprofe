(function () {
    'use strict';

    angular
        .module('app')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('login', {
            parent: 'admin',
            url: '/login',
            data: {
                authorities: [],
                pageTitle: 'login.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/admin/login/login.html',
                    controller: 'LoginController',
                    controllerAs: 'loginCtrl'
                }
            },
            ncyBreadcrumb: {
                skip: true
            }
        });
    }
})();
