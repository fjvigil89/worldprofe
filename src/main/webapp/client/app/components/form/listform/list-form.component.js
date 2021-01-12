(function () {
    'use strict';

    // Lista de elementos dinámicos. Este componente incluye la gestión: añadir y eliminar.

    // label: Texto para la etiqueta.
    // tooltip: Texto para el tooltip.
    // list: Lista de elementos que se modificará.
    // resolve: Información del controller desde donde es invocado el componente. Ej: resolve="{agenteService: ctrl.agenteService, openAgenteModal: ctrl.openAgenteModal}"
    // templateUrl: Plantilla HTML que se mostrará, con los diferentes inputs.
    // required: Boolean. Si es true, siempre habrá al menos un elemento en la lista.
    // acordeon: Boolean. Indica si cada elemento se incluye en un acordeón. Se usa para ajustar el botón de eliminar.

    // Ejemplo: original-form.admin.html
    // Hay que trabajar sobre la variable element, ya que será así como se llame a cada objeto sobre el que se itere en el array.

    angular
        .module('app')
        .component('listForm', {
            templateUrl: 'app/components/form/listform/list-form.html',
            controller: Controller,
            controllerAs: 'ctrl',
            bindings: {
                label: '@',
                tooltip: '@',
                list: '=',
                resolve: '<',
                templateUrl: '@',
                required: '<',
                acordeon: '<'
            }
        });

    /* @ngInject */
    function Controller() {
        var vm = this;

        function initList() {
            if (!vm.list) {
                vm.list = [];
                if (vm.required) {
                    vm.list.push({});
                }
            } else if (!vm.list.length && vm.required) {
                vm.list.push({});
            }
        }

        vm.addElement = function () {
            vm.list.push({desplegado: true});
        };

        vm.removeElement = function (element) {
            vm.list.splice(vm.list.indexOf(element), 1);
            initList();
        };

        initList();

    }

})();
