(function () {
    'use strict';

    angular
        .module('app')
        .config(stateConfig);

    /* @ngInject */
    function stateConfig($stateProvider) {
        $stateProvider
        // Nuevo usuario
            .state('social-register', {
                parent: 'public',
                url: '/social-register/:provider',
                views: {
                    'content@': {
                        /* @ngInject */
                        controller: function login(SocialService) {
                            SocialService.loginWithToken(true);
                        }
                    }
                }
            })
            // Login de un usuario ya existente
            .state('social-auth', {
                parent: 'public',
                url: '/social-auth',
                views: {
                    'content@': {
                        /* @ngInject */
                        controller: function login(SocialService) {
                            SocialService.loginWithToken(false);
                        }
                    }
                }
            });

    }
})();
