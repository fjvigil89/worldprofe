(function () {
    'use strict';

    angular
        .module('app')
        .component('reservaCount', {
            template: '<span class="reserva-count" ng-if="ctrl.estados[ctrl.estado] > 0">({{ctrl.estados[ctrl.estado]}})</span>',
            controllerAs: 'ctrl',
            bindings: {
                estado: '@',
                estados: '='
            }
        });

})();
