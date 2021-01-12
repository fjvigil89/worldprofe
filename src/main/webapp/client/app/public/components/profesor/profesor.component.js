(function () {
    'use strict';

    angular
        .module('app')
        .component('profesor', {
            templateUrl: 'app/public/components/profesor/profesor.html',
            controller: ProfesorController,
            controllerAs: 'ctrl',
            bindings: {
                profesor: '<'
            }
        });

    /* @ngInject */
    function ProfesorController() {
        var vm = this;
        var d = new Date();
        var minutos = '0';
        if (d.getMinutes() < 10) {
            minutos = minutos + d.getMinutes();
        } else {
            minutos = d.getMinutes();
        }
        if (vm.profesor.zonaHoraria) {
            vm.now = d.getHours() + parseInt(vm.profesor.zonaHoraria.gmt.substring(3, 6)) + (d.getTimezoneOffset() / 60) + ":" + minutos;
        }

    }

})();
