(function () {
    'use strict';

    // http://dotansimha.github.io/angularjs-dropdown-multiselect/docs
    // Requiere: label-form, tooltip, ficheros de mensaje

    // id: ID del elemento
    // label: Etiqueta a mostrar
    // tooltip: Tooltip que se añade a la etiqueta
    // model: Objeto que se actualizará
    // options: Lista de opciones
    // display: Propiedad que se mostrará al seleccionar
    // template: Información que se va a mostrar. Tiene que ir entre comillas. Ej: '{{option.nombre}} <span ng-if=\'option.pais.nombre\'>({{option.pais.nombre}})</span>'
    // enum-base: Texto base del enumerado. Con este String se sabe si se está mostrando un enumerado o un objeto. Ej: enum.idiomas.
    // extra: Propiedades para sobreescribir en formato json. Sobreescriben las indicadas anteriormente
    // empty: Propiedad que sobreescribirá al valor por defecto del componente cuando ninguna opción está seleccionada

    // Rendimiento:
    // Hasta 50 no se nota ningún tiempo de espera. Sin embargo, a partir de 100 se nota que le cuesta abrir, pero es asumible.
    // Con 1000 elementos ya tarda varios segundos.

    var component = {
        templateUrl: 'app/components/form/multiselect/multi-select.html',
        bindings: {
            id: '@',
            label: '@',
            tooltip: '@',
            model: '=',
            options: '<',
            display: '@',
            template: '<',
            enum: '<',
            enumBase: '@',
            extra: '<',
            empty: '@'
        },
        controller: Controller,
        controllerAs: 'ctrl'
    };

    /* @ngInject */
    function Controller($translate) {
        var vm = this;

        if (!vm.model) {
            vm.model = [];
        }

        vm.extraSettings = {
            smartButtonMaxItems: 4,

            enableSearch: true,
            clearSearchOnClose: true,

            selectedToTop: true,
            showUncheckAll: false,
            showCheckAll: false
        };

        if (!vm.enumBase) {
            angular.merge(vm.extraSettings, {
                displayProp: vm.display ? vm.display : 'nombre',
                searchField: vm.display ? vm.display : 'nombre',
                idProperty: 'id'
            });
        } else {
            // Enumerados
            angular.merge(vm.extraSettings, {
                // Función para personalizar el texto que se muestra en el botón
                smartButtonTextProvider: function (selectionArray) {
                    var text = "";
                    selectionArray.forEach(function (element, index) {
                        text += $translate.instant(vm.enumBase + element);
                        if (index + 1 < selectionArray.length) {
                            text += ", ";
                        }
                    });
                    return text;
                }
            });
        }

        if (vm.template) {
            vm.extraSettings.template = vm.template;
        }

        if (vm.extra) {
            angular.merge(vm.extraSettings, vm.extra);
        }

        // Traducciones de los textos
        vm.translations = {
            buttonDefaultText: $translate.instant("multiselect.default"),
            searchPlaceholder: $translate.instant("multiselect.search"),
            selectionCount: $translate.instant("multiselect.selectioncount")
        };
        if (vm.empty != null && vm.empty.length) {
            vm.translations.buttonDefaultText = $translate.instant(vm.empty);
        }

        // Elemento seleccionado
        vm.onItemSelect = function (item) {

        };

    }

    angular
        .module('app')
        .component('multiSelect', component);

})();
