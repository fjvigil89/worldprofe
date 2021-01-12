(function () {
    'use strict';

    // value: Valor booleano (true/false -> Sí/No).

    var booleano = {
        template: '{{"enum.boolean." + ctrl.value | translate}}',
        controllerAs: 'ctrl',
        bindings: {
            value: '@'
        }
    };

    angular
        .module('app')
        .component('booleano', booleano);

})();
