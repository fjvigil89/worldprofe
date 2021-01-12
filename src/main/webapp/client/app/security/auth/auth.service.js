(function (angular) {
    'use strict';

    angular.module('app')
        .factory('Auth', Auth);

    /* ngInject */
    function Auth($rootScope, $state, $sessionStorage, $q, $http, AuthServerProvider, Principal, User, Perfil, Account, ADMIN_PRINCIPAL) {
        return {
            login: login,
            loginWithToken: loginWithToken,
            logout: logout,
            authorize: authorize,
            createAccount: createAccount,
            validateAccount: validateAccount,
            changePassword: changePassword,
            resetPasswordInit: resetPasswordInit,
            resetPasswordFinish: resetPasswordFinish
        };

        function validateAccount(key) {
            return $http({
                method: 'GET',
                url: 'api/public/cuenta/validar',
                params: {key: key}
            });
        }

        function login(credentials, callback) {
            /* jshint validthis:true */
            var cb = callback || angular.noop;
            var deferred = $q.defer();

            var login = credentials.username;

            AuthServerProvider.login(credentials).then(function (jwt) {
                // retrieve the logged account information
                Principal.identity(true, login).then(function (account) {
                    // After the login the language will be changed to
                    // the language selected by the user during his registration

                    // $translate.use(account.langKey);

                    if (account.empresa != null) {
                        angular.element(".menu [ui-sref='paquetecreditos']").parent().hide();
                    }
                    if (account.rol === 'ROLE_PROFESOR') {
                        angular.element(".menu [ui-sref='paquetecreditos']").parent().hide();
                        angular.element(".menu [ui-sref='profesor/list']").parent().hide();
                    }
                    deferred.resolve(account);
                });
                return cb();
            }).catch(function (err) {
                this.logout();
                deferred.reject(err);
                return cb(err);
            }.bind(this));

            return deferred.promise;
        }

        function loginWithToken(jwt, rememberMe) {
            return AuthServerProvider.loginWithToken(jwt, rememberMe);
        }

        function logout() {
            AuthServerProvider.logout();
            Principal.authenticate(null);
            angular.element(".menu [ui-sref='paquetecreditos']").parent().show();
            angular.element(".menu [ui-sref='profesor/list']").parent().show();
        }

        function authorize(force, authorities) {
            var authReturn = Principal.identity(force).then(authThen);

            return authReturn;

            function authThen() {
                var isAuthenticated = Principal.isAuthenticated();

                // an authenticated user can't access to login and register pages
                if (isAuthenticated && $rootScope.toState.parent === 'account'
                    && ($rootScope.toState.name === 'login'
                    || $rootScope.toState.name === 'register' || $rootScope.toState.name === 'social-auth'
                    || $rootScope.toState.name === 'recuperar/init' || $rootScope.toState.name === 'recuperar/finish')) {
                    $state.go('home');
                }

                // recover and clear previousState after external login redirect (e.g. oauth2)
                if (isAuthenticated && (!$rootScope.fromState || !$rootScope.fromState.name) && $sessionStorage.previousStateName) {
                    var previousStateName = $sessionStorage.previousStateName;
                    var previousStateParams = $sessionStorage.previousStateParams;
                    delete $sessionStorage.previousStateName;
                    delete $sessionStorage.previousStateParams;
                    $state.go(previousStateName, previousStateParams);
                }

                // Si estamos autenticados y nos dirigimos a la pantalla de login/registro
                if (isAuthenticated && $rootScope.toState.name === "registro/info") {

                    // Obtenemos el usuario autenticado
                    Account.get().$promise
                        .then(getAccountThen);

                    function getAccountThen(account) {
                        // Teniendo en cuenta el rol del usuario autenticado,
                        // nos dirigimos a un estado u otro
                        if (account && account.data && account.data.rol) {
                            switch (account.data.rol) {
                                case 'ROLE_ADMIN':
                                    $state.go(ADMIN_PRINCIPAL);
                                    break;
                                case 'ROLE_ALUMNO':
                                case 'ROLE_PROFESOR':
                                    $state.go('miespacio/info');
                                    break;
                                case 'ROLE_EMPRESA':
                                    $state.go('miespacio/misalumnos');
                                    break;
                            }
                        }
                    }
                }

                if (($rootScope.toState.data.authorities && $rootScope.toState.data.authorities.length > 0 && !Principal.hasAnyAuthority($rootScope.toState.data.authorities))
                    || (authorities && authorities.length > 0 && !Principal.hasAnyAuthority(authorities))) {
                    if (isAuthenticated) {
                        // user is signed in but not authorized for desired state
                        $state.go('accessdenied');
                    } else {
                        // user is not authenticated. stow the state they wanted before you
                        // send them to the login service, so you can return them when you're done
                        $sessionStorage.previousStateName = $rootScope.toState.name;
                        $sessionStorage.previousStateParams = $rootScope.toStateParams;

                        if ($rootScope.toState.name.startsWith("admin/")) {
                            // now, send them to the signin state so they can log in
                            $state.go('login');
                        } else {
                            $state.go('registro/info');
                        }
                    }
                }
            }
        }

        function createAccount(account, callback) {
            /* jshint validthis:true */
            var cb = callback || angular.noop;

            return User.save(account,
                function () {
                    return cb(account);
                },
                function (err) {
                    // this.logout();
                    return cb(err);
                }.bind(this)).$promise;
        }

        function changePassword(newPassword, callback) {
            var cb = callback || angular.noop;

            return Perfil.changePassword(newPassword, function () {
                return cb();
            }, function (err) {
                return cb(err);
            }).$promise;
        }

        function resetPasswordInit(email) {
            return $http({
                method: 'GET',
                url: 'api/public/cuenta/reset-contrasena/init',
                params: {email: email}
            });
        }

        function resetPasswordFinish(keyAndPassword) {
            return $http({
                method: 'GET',
                url: 'api/public/cuenta/reset-contrasena/finish',
                params: {
                    email: keyAndPassword.email,
                    key: keyAndPassword.key,
                    newPassword: keyAndPassword.newPassword
                }
            });
        }
    }

})(angular);
