(function () {
    'use strict';

    angular
        .module('app')
        .component('login', {
            templateUrl: 'app/public/components/login/login.html',
            controller: LoginController,
            bindings: {
                foco: '<'
            }
        });

    /* @ngInject */
    function LoginController($state, $timeout, Auth, AlertService, ADMIN_PRINCIPAL) {
        var vm = this;

        vm.user = {};
        vm.rememberMe = false;

        if (vm.foco) {
            $timeout(function () {
                angular.element('.form-group>input#username').focus();
            });
        }

        vm.login = function () {
            Auth.login({
                username: vm.username,
                password: vm.password,
                rememberMe: vm.rememberMe
            }).then(function (data) {
                vm.authenticationError = false;
                if (data.rol === 'ROLE_ADMIN') {
                    $state.go(ADMIN_PRINCIPAL, {}, {reload: true});
                } else {
                    $state.go('miespacio/info', {}, {reload: true});
                }
            }).catch(function () {
                AlertService.error("login.form.error.authentication");
            });
        }
    }

})();
