(function (angular) {
    'use strict';

    angular
        .module('app')
        .controller('RegistroController', RegistroController);

    /* @ngInject */
    function RegistroController($timeout, $stateParams, Usuario, EnumService, AlertService) {
        var vm = this;

        $timeout(function () {
            angular.element('.form-group>input:eq(0)').focus();
        });

        vm.mode = 'create';

        function initForm() {
            vm.item = new Usuario();
            vm.item.type = vm.tipo = $stateParams.tipo;
            vm.confirmPassword = "";

            initImagen();
        }

        initForm();

        // Guardar formulario
        vm.save = function () {
            if (vm.item.contrasenaClaro !== vm.confirmPassword) {
                AlertService.error("admin.register.error.dontmatch");
            } else if (!vm.acepto) {
                AlertService.error("registro.error.acepto");
            } else {
                vm.item.$register().then(function (data) {
                    vm.success = true;
                    initForm();
                });
            }
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

        vm.usuarioService = Usuario;

    }
})(angular);
