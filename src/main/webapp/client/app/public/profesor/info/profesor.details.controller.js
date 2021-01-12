(function (angular) {
    'use strict';

    angular
        .module('app')
        .controller('ProfesorDetailsController', ProfesorDetailsController);

    /* @ngInject */
    function ProfesorDetailsController(profesor) {
        var vm = this;
        vm.profesor = profesor;

    }

})(angular);
