(function () {
    'use strict';

    angular.module('app')
        .controller('AdminFormController', AdminFormController);

    /* @ngInject */
    function AdminFormController($timeout, $state, AlertService, Auth) {
        var vm = this;

        vm.registerAccount = {};
        vm.roles = [];
        $timeout(function () {
            angular.element('.form-group:eq(0)>input')[0].focus();
        });

        vm.register = function () {
            if (vm.registerAccount.password !== vm.confirmPassword) {
                AlertService.error("admin.register.error.dontmatch");
            } else {
                //En caso de que más roles sean admin, añadir al enumerado Rol y obtenerlos
                vm.registerAccount.rol = 'ROLE_ADMIN';
                vm.registerAccount.email = vm.registerAccount.login;
                Auth.createAccount(vm.registerAccount).then(function () {
                    $state.go("admin/admin/list");
                });
            }
        };
    }
})();
