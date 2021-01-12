(function () {
    'use strict';

    angular
        .module('app')
        .component('buttons', {
            templateUrl: 'app/components/form/buttons/buttons-form.template.html',
            controller: Controller,
            controllerAs: 'ctrl',
            bindings: {
                canSave: '<',
                mode: '<'
            }
        });

    function Controller($rootScope, ModalService) {
        var vm = this;

        // Si hay una ventana modal abierta, se cierra
        vm.back = function () {
            if (ModalService.isOpen()) {
                ModalService.dismiss();
            } else {
                $rootScope.back();
            }
        };

    }

})();
