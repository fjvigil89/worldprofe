(function () {
    'use strict';

    angular
        .module('app')
        .component('empresaFormAdmin', {
            templateUrl: 'app/admin/empresa/empresa-form.admin.html',
            controller: Controller,
            controllerAs: 'ctrl',
            bindings: {
                item: '<',
                previousParams: '<'
            }
        });

    /* @ngInject */
    function Controller($timeout, $state, EnumService, Capacidad, Idioma, Pais, Nivel, Usuario, AlertService, ModalService, CAPACIDAD_FORM_ADMIN) {
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

        // Guardar formulario
        vm.save = function () {
            vm.item.type = "empresa";
            if (vm.item.imagen) {
                vm.item.foto = vm.item.imagen.path;
            }
            if ((vm.item.contrasenaClaro) && (vm.item.contrasenaClaro !== vm.confirmPassword)) {
                AlertService.error("admin.register.error.dontmatch");
            } else {
                if (vm.item.id) {
                    Usuario.edit(vm.item).$promise.then(function(data) {
                        $state.go('admin/empresa/list');
                    });
                } else {
                    Usuario.register(vm.item).$promise.then(function(data) {
                        $state.go('admin/empresa/list');
                    });
                }
            }
        };

    }

})();
