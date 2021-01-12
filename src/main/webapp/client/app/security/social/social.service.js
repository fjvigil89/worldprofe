(function () {
    'use strict';

    angular
        .module('app')
        .factory('SocialService', SocialService);

    /* @ngInject */
    function SocialService($http, $cookies, $state, Auth, AlertService) {
        var socialService = {
            loginWithToken: loginWithToken,
            getProviderSetting: getProviderSetting,
            getProviderURL: getProviderURL,
            getCSRF: getCSRF
        };

        return socialService;

        function loginWithToken(registro) {
            var token = $cookies.get('social-authentication');

            Auth.loginWithToken(token, false).then(function () {
                console.log("login con exito");
                if (registro) {
                    AlertService.success("social.success.registro");
                }
                $cookies.remove('social-authentication');
                Auth.authorize(true);
                $state.go("miespacio/miagenda");
            }, function () {
                console.log("login sin exito");
                if (registro) {
                    AlertService.error("social.error.registro");
                } else {
                    AlertService.error("social.error.login");
                }
                $state.go('registro/info');
            });
        }

        function getProviderSetting(provider) {
            switch (provider) {
                case 'google':
                    return 'https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email';
                case 'facebook':
                    return 'public_profile,email';
                default:
                    return 'Provider setting not defined';
            }
        }

        function getProviderURL(provider) {
            return 'signin/' + provider;
        }

        function getCSRF() {
            return $cookies.get($http.defaults.xsrfCookieName);
        }
    }
})();
