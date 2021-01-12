(function () {
    'use strict';

    // id: Identificador del select.
    // label: etiqueta
    // model: Campo que se modificará.
    // disabled
    // required
    // options: Opciones del select.
    // format: Formato de la fecha
    // change: Función que se llamará al cambiar el input.
    // showBar: Booleano para mostrar u ocultar la barra inferior del DatePicker. (default: true)
    // readonly: Booleano para deshabilitar el input (default: false)
    // disabled: No permite modificar el input.

    var datePicker = {
        templateUrl: 'app/components/form/date/date-picker.html',
        bindings: {
            id: '@',
            label: '@',
            model: '=', // required
            disabled: '<',
            required: '<',
            options: '<',
            format: '@',
            change: '<',
            showBar: '<?', // opcional
            readonly: '<?' // opcional
        },
        controller: Controller,
        controllerAs: 'ctrl'
    };

    angular
        .module('app')
        .component('datePicker', datePicker);

    /* @ngInject */
    function Controller() {
        var vm = this;

        function init() {
            // Si recibimos la fecha del servidor, convertimos segundos a milisegundos
            if (angular.isNumber(vm.model)) {
                vm.model = vm.model * 1000;
            }
        }

        vm.opened = false;
        if (!vm.format) {
            vm.format = 'dd/MM/yyyy';
        }

        if (typeof(vm.showBar) === 'undefined') {
            vm.showBar = true;
        }

        if (typeof(vm.readonly) === 'undefined') {
            vm.readonly = false;
        }

        if (vm.options && vm.options.minDate === 'today') {
            vm.options.minDate = new Date();
        }

        vm.dateOptions = {
            datepickerMode: 'year',
            formatYear: 'yyyy',
            maxDate: new Date(),
            startingDay: 1,
            showWeeks: false
        };
        angular.merge(vm.dateOptions, vm.options);

        vm.openDatePicker = function () {
            vm.opened = true;
        };

        init();
    }

})();
