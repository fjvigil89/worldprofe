(function () {
    'use strict';

    angular
        .module('app')
        .component('alumnoFormAdmin', {
            templateUrl: 'app/admin/alumno/alumno-form.admin.html',
            controller: Controller,
            controllerAs: 'ctrl',
            bindings: {
                item: '<',
                previousParams: '<',
                empresa: '<'
            }
        });

    /* @ngInject */
    function Controller($timeout, $state, EnumService, Usuario, AlertService, ModalService, $stateParams) {
        var vm = this;

        if (!vm.item.imagen) {
            vm.item.imagen = {};
        }
        if (vm.item.foto) {
            vm.item.imagen.path = vm.item.foto;
        }

        vm.onRemoveImage = function () {
            vm.item.imagen = {eliminar: true};
        };

        $timeout(function () {
            angular.element('.form-group:eq(0) input').focus();
        });

        vm.mode = $state.current.data.mode;
        vm.canSave = vm.mode === 'create' || vm.mode === 'edit';

        if (vm.empresa != null) {
            var creditosIniciales = vm.item.creditosDisponibles;
            vm.creditosMaximos = creditosIniciales + vm.empresa.creditosDisponibles;
        }

        // Guardar formulario
        vm.save = function () {
            vm.item.type = "alumno";
            if (vm.item.imagen) {
                vm.item.foto = vm.item.imagen.path;
            }
            if ($stateParams.empresaId) {
                vm.item.empresa = {};
                vm.item.empresa.id = $stateParams.empresaId;
                vm.item.empresa.type = 'empresa';
            }
            if ((vm.item.contrasenaClaro) && (vm.item.contrasenaClaro !== vm.confirmPassword)) {
                AlertService.error("admin.register.error.dontmatch");
            } else {
                if (vm.item.id) {
                    Usuario.edit(vm.item).$promise.then(function(data) {
                        $state.go('admin/empresa/alumno', {empresaId: vm.item.empresa.id});
                    });
                } else {
                    Usuario.register(vm.item).$promise.then(function(data) {
                        $state.go('admin/empresa/alumno', {empresaId: vm.item.empresa.id});
                    });
                }
            }
        };

    }

})();
