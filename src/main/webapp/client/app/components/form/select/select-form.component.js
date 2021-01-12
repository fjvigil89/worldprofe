(function () {
    'use strict';

    // Requiere: label-form

    // id: Identificador del select.
    // label: etiqueta
    // model: Campo que se modificará.
    // disabled
    // required
    // options: Opciones del select.
    // base: Clave base para los enumerados.
    // display: Propiedad del objeto que se usará para internacionalizar el contenido.

    // FORMATEADO: Se acepta dar un formato especial a los selects. Para esto habrá que pasar listas de elementos, separadas por comas, en display y base, además del campo format.
    // Este campo tendrá un _ en cada variable que se quiera rellenar (uno por cada display).

    angular
        .module('app')
        .component('selectForm', {
            templateUrl: 'app/components/form/select/select-form.html',
            controllerAs: 'ctrl',
            bindings: {
                id: '@',
                label: '@',
                tooltip: '@',
                model: '=', // required
                change: '=',
                display: '@', // required
                format: '@',
                disabled: '=',
                required: '=',
                options: '=', // required
                base: '@', // required
                empty: '@'
            },
            /* @ngInject */
            controller: function ($translate) {
                var vm = this;

                if (!vm.display) {
                    vm.display = "nombre";
                }
                var list = vm.display.split(",");

                if (!vm.base) {
                    vm.base = "";
                }
                var base = vm.base.split(",");

                if (!vm.format) {
                    vm.format = "_";
                }

                // FIXME: Esta operación se llama cada vez que se interactúa con un select para recalcular el valor de TODOS los options de todos los selects.
                vm.formatElement = function (element) {
                    var formated = vm.format;
                    angular.forEach(list, function (elementDisplay, index) {
                        var baseText = base[index] ? base[index].trim() : "";
                        var translated = $translate.instant(baseText + element[elementDisplay.trim()]);
                        formated = formated.replace("_", translated);
                    });
                    return formated;
                }
            }
        });

})();
