(function () {
    'use strict';

    angular
        .module('app')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('site', {
            abstract: true,
            data: {
                authorities: []
            },
            resolve: {
                authorize: ['Auth', function (Auth) {
                    return Auth.authorize();
                }]
            }
        });
    }
})();
