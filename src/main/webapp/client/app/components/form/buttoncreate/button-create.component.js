(function () {
    'use strict';

    // Botón para la creación de entidades dentro de otras. Usado para los formularios dentro de ventanas modales.

    // click: Función que se va a llamar al pulsar el botón.
    // params: Parámetros que se enviará a la función.

    // Ejemplo de uso: FILO1701, traduccion-form.admin.*

    angular
        .module('app')
        .component('buttonCreate', {
            template: '<a ng-click="ctrl.click(ctrl.params)" class="button-create"' +
            ' title="{{\'admin.form.button.create\' | translate}}"><i class="fa fa-plus-square"></i></a>',
            controllerAs: 'ctrl',
            bindings: {
                click: '<',
                params: '<'
            }
        });

})();
