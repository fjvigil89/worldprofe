(function (angular) {
    'use strict';

    angular
        .module('app')
        .controller('LoginController', LoginController);

    /* @ngInject */
    function LoginController($state, $timeout, Auth, Principal, AlertService, ADMIN_PRINCIPAL) {
        var vm = this;

        vm.user = {};
        vm.rememberMe = false;

        Principal.identity().then(function () {
            $state.go(ADMIN_PRINCIPAL);
        });

        $timeout(function () {
            angular.element('.form-group:eq(0)>input').focus();
        });

        vm.login = function () {
            Auth.login({
                username: vm.username,
                password: vm.password,
                rememberMe: vm.rememberMe
            }).then(function () {
                vm.authenticationError = false;
                $state.go(ADMIN_PRINCIPAL);
            }).catch(function () {
                AlertService.error("login.form.error.authentication");
            });
        }
    }

})(angular);
