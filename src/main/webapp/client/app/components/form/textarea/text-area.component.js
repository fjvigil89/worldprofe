(function () {
    'use strict';

    // id: Identificador.
    // label: Etiqueta.
    // model: Campo que se modificará.
    // disabled: Boolean.
    // required: Boolean.
    // tipo: String que identifica el tipo de editor (los botones que incluye). Tipos: codigo

    // TODO: Lo ideal sería que al pulsar el submit no se enviase la información y se activasen los textareas con errores.
    // TODO: i18n

    angular
        .module('app')
        .component('textArea', {
            template: '<label-form key="{{ctrl.label}}" for="{{ctrl.id}}" required="ctrl.required"></label-form>' +
            '<text-angular ng-model="ctrl.model" id="{{ctrl.id}}" ta-toolbar="{{ctrl.toolbar}}" ng-required="{{ctrl.required}}"></text-angular>',
            bindings: {
                id: '@',
                label: '@',
                model: '=', // required
                disabled: '<',
                required: '<',
                tipo: '@'
            },
            controller: Controller,
            controllerAs: 'ctrl'
        });

    /* @ngInject */
    function Controller($timeout) {
        var vm = this;

        vm.toolbar = [
            ['html', 'bold', 'italics', 'underline'],
            ['undo', 'redo']
        ];

        switch (vm.tipo) {
            case 'codigo':
                vm.toolbar.push(['codigo']);
                break;
            case 'estatica':
                vm.toolbar.push(['h1', 'h2', 'p']);
                vm.toolbar.push(['ul', 'ol']);
                vm.toolbar.push(['justifyLeft', 'justifyCenter', 'justifyRight', 'justifyFull', 'indent', 'outdent']);
                vm.toolbar.push(['insertLink']);
                break;
        }

        $timeout(function () {
            angular.element("text-angular#" + vm.id).blur(function (data) {
                var element = angular.element(data.target);
                if (data.target.classList.contains("ng-invalid-required")) {
                    element.addClass("error");
                } else {
                    element.removeClass("error");
                }
            });
            angular.element("text-angular#" + vm.id).focus(function (data) {
                angular.element(data.target).removeClass("error");
            });
        });
    }

})();
