(function () {
    'use strict';

    angular
        .module('app')
        .component('profesorFormAdmin', {
            templateUrl: 'app/admin/profesor/profesor-form.admin.html',
            controller: Controller,
            controllerAs: 'ctrl',
            bindings: {
                item: '<',
                previousParams: '<'
            }
        });

    /* @ngInject */
    function Controller($timeout, $state, EnumService, Capacidad, Idioma, Pais, Nivel, Usuario, User, AlertService, ModalService, CAPACIDAD_FORM_ADMIN) {
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

        vm.openCapacidadModal = function () {
            ModalService.openComponent('admin.capacidad.create.title', CAPACIDAD_FORM_ADMIN, {size: 'lg'}).result.then(function (result) {
                // Añadimos el nuevo elemento al select
                vm.capacidades.push(result);
                // Asociamos el nuevo elemento a la entidad
                vm.item.capacidades.push(result);
            });
        };

        // Guardar formulario
        vm.save = function () {
            vm.item.type = "profesor";
            if (vm.item.imagen) {
                vm.item.foto = vm.item.imagen.path;
            }
            // FIXME: Apaño temporal para la edición de fechas...
            if (angular.isNumber(vm.item.fechaNacimiento)) {
                vm.item.fechaNacimiento = vm.item.fechaNacimiento / 1000;
            }
            if ((vm.item.contrasenaClaro) && (vm.item.contrasenaClaro !== vm.confirmPassword)) {
                AlertService.error("admin.register.error.dontmatch");
            } else {
                if (vm.item.id) {
                    Usuario.edit(vm.item).$promise.then(function(data) {
                        $state.go('admin/profesor/list');
                    });
                } else {
                    Usuario.register(vm.item).$promise.then(function(data) {
                        $state.go('admin/profesor/list');
                    });
                }
            }
        };

        Capacidad.findAll({sortProperty: 'nombre'}).$promise.then(function (data) {
            vm.capacidades = data.content;
        });

        /*EnumService.get("disponibilidad").then(function(data) {
            vm.disponibilidades = data;
        });*/

        Nivel.query().$promise.then(function(data) {
            vm.niveles = data;
        });

        vm.idiomaService = Idioma;
        vm.nivelService = Nivel;
        vm.paisService = Pais;

        User.zonasHorarias().$promise.then(function(data) {
            vm.zonasHorarias = data;
        });
    }

})();
