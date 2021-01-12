(function () {
    'use strict';

    // Componente para modificar la contraseña

    angular
        .module('app')
        .component('changePassword', {
            templateUrl: 'app/public/components/password/password.html',
            controller: PasswordController,
            controllerAs: 'ctrl'
        });

    /* @ngInject */
    function PasswordController($timeout, Auth, AlertService) {
        var vm = this;

        $timeout(function () {
            angular.element('.form-group:eq(0) input').focus();
        });

        vm.save = function () {
            if (vm.form.newpassword !== vm.confirmPassword) {
                AlertService.error("admin.register.error.dontmatch");
            } else {
                Auth.changePassword(vm.form).then(function () {
                    vm.form.password = "";
                    vm.form.newpassword = "";
                    vm.form.$setPristine();
                    vm.confirmPassword = "";
                });
            }
        };
    }

})();
