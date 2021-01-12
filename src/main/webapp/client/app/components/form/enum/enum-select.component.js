(function () {
    'use strict';

    // id: Identificador del select.
    // label: etiqueta
    // model: Campo que se modificará.
    // disabled
    // required
    // options: Opciones del select.
    // base: Clave base para los enumerados.
    // display: Propiedad del objeto que se usará para internacionalizar el contenido.
    // empty: Clave de mensaje para mostrar en la opción vacía

    // Nota: Para utilizar como un campo booleano (Null/Sí/No), pasar como options: [true, false].

    angular
        .module('app')
        .component('enumSelect', {
            templateUrl: 'app/components/form/enum/enum-select.html',
            controllerAs: 'ctrl',
            bindings: {
                id: '@',
                label: '@',
                model: '=',
                change: '=',
                display: '@',
                empty: '@',
                disabled: '=',
                required: '=',
                options: '=',
                base: '@'
            }
        });

})();
