(function () {
    'use strict';

    // Componente que mete en una variable el día, mes y año de nacimiento/muerte/fecha genérica.
    // Es importante que las variables en el modelo se llamen prefijoX para que se pueda utilizar este componente.

    // model: Campo que se modificará.
    // prefijo: String. nacimiento | muerte | fecha. Se concatena este prefijo con el campo. Ej: nacimientoDia.

    angular
        .module('app')
        .component('fecha', {
            templateUrl: 'app/components/form/fecha/fecha.html',
            bindings: {
                model: '=',
                prefijo: '@',
                label: '@'
            },
            controllerAs: 'ctrl'
        });

})();
