(function () {
    'use strict';

    // Este componente permite establecer una estructura básica para abrir un formulario o cualquier otro componente dentro de una ventana modal.

    // resolve.component: String que se convertirá en el componente (Ej: <pais-form-admin item=\'ctrl.resolve.item\'></pais-form-admin>)
    // resolve: Información que tiene que recibir el componente. En el componente se accederá a través de ctrl.resolve.
    // resolve.title: Título de la ventana modal

    // Ejemplo de uso: ciudad-form.admin.component.js

    angular
        .module('app')
        .component('entityModal', {
            templateUrl: 'app/components/modal/entity.modal.html',
            controller: Controller,
            controllerAs: 'ctrl',
            bindings: {
                resolve: '<'
            }
        });

    /* @ngInject */
    function Controller(ModalService) {
        var vm = this;

        vm.dismiss = function () {
            ModalService.dismiss();
        }
    }

})();
