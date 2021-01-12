(function () {
    'use strict';

    angular
        .module('app')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('logout', {
            parent: 'public',
            data: {
                authorities: ['ROLE_AUTHENTICATED']
            },
            views: {
                'content@': {
                    controller: 'LogoutController'
                }
            }
        });
    }
})();
