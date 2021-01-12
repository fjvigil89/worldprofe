(function () {
    'use strict';

    angular
        .module('app')
        .factory('AuthServerProvider', AuthServerProvider);

    AuthServerProvider.$inject = ['$http', '$localStorage', '$sessionStorage', '$q'];

    function AuthServerProvider($http, $localStorage, $sessionStorage, $q) {
        var service = {
            getToken: getToken,
            hasValidToken: hasValidToken,
            login: login,
            loginWithToken: loginWithToken,
            storeAuthenticationToken: storeAuthenticationToken,
            logout: logout
        };

        return service;

        function getToken() {
            return $localStorage.authenticationToken || $sessionStorage.authenticationToken;
        }

        function hasValidToken() {
            var token = this.getToken();
            return token && token.expires && token.expires > new Date().getTime();
        }

        function login(credentials) {
            var data = {
                username: credentials.username,
                password: credentials.password,
                rememberMe: credentials.rememberMe
            };
            return $http.post('api/account/authenticate', data).then(authenticateSuccess);

            function authenticateSuccess(data) {
                var status = data.status;
                var headers = data.headers;
                var bearerToken = headers('Authorization');
                if (angular.isDefined(bearerToken) && bearerToken.slice(0, 7) === 'Bearer ') {
                    var jwt = bearerToken.slice(7, bearerToken.length);
                    service.storeAuthenticationToken(jwt, credentials.rememberMe);
                    return jwt;
                }
            }
        }

        function loginWithToken(jwt, rememberMe) {
            var deferred = $q.defer();

            if (angular.isDefined(jwt)) {
                this.storeAuthenticationToken(jwt, rememberMe);
                deferred.resolve(jwt);
            } else {
                deferred.reject();
            }

            return deferred.promise;
        }

        function storeAuthenticationToken(jwt, rememberMe) {
            if (true) {
                $localStorage.authenticationToken = jwt;
            } else {
                $sessionStorage.authenticationToken = jwt;
            }
        }

        function logout() {
            delete $localStorage.authenticationToken;
            delete $sessionStorage.authenticationToken;
        }
    }
})();
