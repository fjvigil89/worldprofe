(function (angular) {
    'use strict';

    angular
        .module('app')
        .controller('RecuperarInitController', RecuperarInitController);

    /* @ngInject */
    function RecuperarInitController($timeout, Auth) {
        var vm = this;

        $timeout(function () {
            angular.element('.form-group:eq(0)>input').focus();
        });

        vm.recuperar = function (email) {
            Auth.resetPasswordInit(email).then(function () {
                vm.success = true;
            });
        }
    }

})(angular);
