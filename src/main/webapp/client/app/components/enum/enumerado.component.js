(function () {
    'use strict';

    // base: Clave base para el fichero de mensajes.
    // value: Valor.

    var enumerado = {
        template: '<span ng-if="ctrl.value">{{ctrl.base + "." + ctrl.value | translate}}</span>',
        controllerAs: 'ctrl',
        bindings: {
            value: '@',
            base: '@'
        }
    };

    angular
        .module('app')
        .component('enumerado', enumerado);

})();
