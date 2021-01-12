(function () {
    'use strict';

    // id: Identificador del select.
    // label: etiqueta
    // model: Campo que se modificará.
    // disabled
    // required
    // empty: Clave de mensaje para mostrar en la opción vacía

    // Nota: Para utilizar como un campo booleano (Null/Sí/No), pasar como options: [true, false].

    angular
        .module('app')
        .component('enumSelectBoolean', {
            templateUrl: 'app/components/form/enum/enum-select-boolean.html',
            controllerAs: 'ctrl',
            bindings: {
                id: '@',
                label: '@',
                model: '=',
                change: '=',
                empty: '@',
                disabled: '=',
                required: '='
            }
        });

})();
