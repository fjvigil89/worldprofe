(function (angular) {
    'use strict';

    angular
        .module('app')
        .controller('RecuperarFinishController', RecuperarFinishController);

    /* @ngInject */
    function RecuperarFinishController($timeout, $state, $stateParams, AlertService, Auth) {
        var vm = this;

        $timeout(function () {
            angular.element('.form-group:eq(0)>input').focus();
        });

        vm.save = function () {
            if (vm.newPassword !== vm.confirmPassword) {
                AlertService.error("admin.register.error.dontmatch");
            } else {
                Auth.resetPasswordFinish({
                    email: $stateParams.email,
                    key: $stateParams.key,
                    newPassword: vm.newPassword
                }).then(function () {
                    $state.go('home');
                });
            }
        }
    }

})(angular);
