(function () {
    'use strict';

    // Requiere: tooltip

    // key: Clave del fichero de mensajes.
    // for: ID del input asociado.
    // tooltip: Mensaje mostrado como ayuda
    // required: Muestra u oculta el * en función de si es obligatorio o no.

    var labelForm = {
        template: '<label ng-if="ctrl.key" class="control-label" ng-class="{\'required\' : ctrl.required}" for="{{ctrl.for}}">' +
            '{{ctrl.key | translate}}{{ctrl.sufijo}}<span ng-if="ctrl.required">*</span>' +
            '</label><tooltip ng-if="ctrl.tooltip" label="{{ctrl.tooltip}}"></tooltip>',
        bindings: {
            key: '@', // required
            for: '@',
            tooltip: '@',
            required: '=',
            sufijo: '@'
        },
        controllerAs: 'ctrl'
    };

    angular
        .module('app')
        .component('labelForm', labelForm);

})();
