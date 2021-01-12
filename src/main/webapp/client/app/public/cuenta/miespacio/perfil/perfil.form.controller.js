(function (angular) {
    'use strict';

    angular
        .module('app')
        .controller('PerfilFormController', PerfilFormController);

    /* @ngInject */
    function PerfilFormController($timeout, $state, itemData, ModalService,
                                  Usuario, EnumService, Principal, Capacidad, Idioma, Pais, Nivel, User) {
        var vm = this;

        $timeout(function () {
            angular.element('.form-group>input#nombre').focus();
        });

        vm.mode = 'create';

        function initForm() {
            vm.item = angular.copy(itemData);

            initImagen();
        }

        initForm();

        // Guardar formulario
        vm.save = function () {
            if (vm.item.centro) {
                vm.item.centro.type = 'centro';
            }

            // FIXME: Apaño temporal para la edición de fechas...
            if (angular.isNumber(vm.item.fechaNacimiento)) {
                vm.item.fechaNacimiento = vm.item.fechaNacimiento / 1000;
            }

            Usuario.edit(vm.item).$promise.then(function (data) {
                vm.item.fechaNacimiento = data.fechaNacimiento * 1000;
                Principal.identity(true);
            });
        };

        // Foto de perfil
        function initImagen() {
            if (!vm.item.imagen) {
                vm.item.imagen = {};
            }
            if (vm.item.foto) {
                vm.item.imagen.path = vm.item.foto;
            }
        }

        vm.onRemoveImage = function () {
            vm.item.imagen = {eliminar: true};
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

        // Darse de baja
        vm.confirmarBaja = function () {
            ModalService.open({
                templateUrl: 'app/components/form/confirm/confirm.modal.html',
                controller: 'ConfirmModalController',
                controllerAs: 'ctrl',
                resolve: {
                    item: function () {
                        return vm.item;
                    },
                    params: function () {
                        return {
                            title: 'miespacio.miperfil.baja.titulo',
                            body: 'miespacio.miperfil.baja.confirmacion'
                        };
                    },
                    /* @ngInject */
                    tipo: function (Perfil) {
                        return Perfil;
                    },
                    funcion: function () {
                        return "baja";
                    },
                    parametros: function () {
                        return {};
                    }
                }
            }).result.then(function () {
                $state.go("logout");
            });
        }

    }
})(angular);
