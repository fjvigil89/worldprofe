(function (angular) {
    'use strict';

    angular
        .module('app')
        .controller('ValidarController', ValidarController);

    /* @ngInject */
    function ValidarController($stateParams, Auth) {
        var vm = this;

        Auth.validateAccount($stateParams.key).then(function (result) {
            vm.error = null;
            vm.success = true;
            vm.rol = result.data;
        }).catch(function () {
            vm.error = 'ERROR';
        });
    }

})(angular);
