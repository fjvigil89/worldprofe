(function () {
    'use strict';

    angular
        .module('app')
        .component('paquetecreditosFormAdmin', {
            templateUrl: 'app/admin/paquetecreditos/paquetecreditos-form.admin.html',
            controller: Controller,
            controllerAs: 'ctrl',
            bindings: {
                item: '<',
                previousParams: '<'
            }
        });

    /* @ngInject */
    function Controller($timeout, $state, Paquetecreditos, ModalService, $stateParams) {
        var vm = this;

        $timeout(function () {
            angular.element('.form-group:eq(0) input').focus();
        });

        vm.mode = $state.current.data.mode;
        vm.canSave = vm.mode === 'create' || vm.mode === 'edit';

        // Guardar formulario
        vm.save = function () {
            if ($stateParams.paquetecreditosId) {
                vm.item.paquetecreditos = {};
                vm.item.paquetecreditos.id = $stateParams.empresaId;
            }
            Paquetecreditos.save(vm.item).$promise.then(function(data) {
                $state.go('admin/paquetecreditos/list');
            });
        };

    }

})();
