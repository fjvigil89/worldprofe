(function () {
    'use strict';

    //

    var date = {
        template: '{{ctrl.result | date:ctrl.format}}',
        bindings: {
            model: '<', // required
            format: '@',
            fecha: '<',
            hora: '<'
        },
        /* @ngInject */
        controller: function ($scope) {
            var vm = this;
            if (!vm.format) {
                if (vm.fecha) {
                    vm.format = 'dd/MM/yyyy';
                } else {
                    if (vm.hora) {
                        vm.format = 'HH:mm';
                    } else {
                        vm.format = 'dd/MM/yyyy, HH:mm';
                    }
                }
            }

            function init() {
                // Si recibimos la fecha del servidor, convertimos segundos a milisegundos
                if (angular.isNumber(vm.model)) {
                    vm.result = vm.model * 1000;
                }
            }

            $scope.$watch(function () {
                return vm.model;
            }, function () {
                init();
            });
        },
        controllerAs: 'ctrl'
    };

    angular
        .module('app')
        .component('date', date);

})();
