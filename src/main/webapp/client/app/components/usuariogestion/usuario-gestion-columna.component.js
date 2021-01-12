(function (angular) {
    'use strict';

    /**
     * Añade los botones de editar, desactivar/activar y baja.
     */
    angular
        .module('app')
        .component('usuarioGestionColumna', {
            bindings: {
                tableParams: '<',
                element: '<',
                entidad: '@'
            },
            templateUrl: 'app/components/usuariogestion/usuario-gestion-columna.html',
            controllerAs: 'ctrl',
            /* @ngInject */
            controller: function (ModalService) {
                var vm = this;

                // Desactivar, eliminar y recuperar

                vm.showDesactivarConfirmation = function (usuario) {
                    ModalService.open({
                        templateUrl: 'app/components/form/confirm/confirm.modal.html',
                        controller: 'ConfirmModalController',
                        controllerAs: 'ctrl',
                        resolve: {
                            item: function () {
                                return usuario;
                            },
                            params: function () {
                                var mensaje = 'admin.list-users.desactivar.confirm';
                                if (usuario.rol === 'ROLE_EMPRESA') {
                                    mensaje = 'admin.list-users.desactivar.confirmempresa';
                                }
                                return {
                                    title: 'admin.list-users.desactivar.title',
                                    body: mensaje,
                                    property: 'email'
                                };
                            },
                            /* @ngInject */
                            tipo: function (User) {
                                return User;
                            },
                            funcion: function () {
                                return "remove";
                            },
                            parametros: function () {
                                return {id: usuario.id};
                            }
                        }
                    }).result.then(function () {
                        vm.tableParams.reload();
                    });
                };

                vm.showBajaConfirmation = function (usuario) {
                    ModalService.open({
                        templateUrl: 'app/components/form/confirm/confirm.modal.html',
                        controller: 'ConfirmModalController',
                        controllerAs: 'ctrl',
                        resolve: {
                            item: function () {
                                return usuario;
                            },
                            params: function () {
                                var mensaje = 'admin.list-users.delete.confirm';
                                if (usuario.rol === 'ROLE_EMPRESA') {
                                    mensaje = 'admin.list-users.delete.confirmempresa';
                                }
                                return {
                                    title: 'admin.list-users.delete.title',
                                    body: mensaje,
                                    property: 'email'
                                };
                            },
                            /* @ngInject */
                            tipo: function (User) {
                                return User;
                            },
                            funcion: function () {
                                return "baja";
                            },
                            parametros: function () {
                                return {email: usuario.email};
                            }
                        }
                    }).result.then(function () {
                        vm.tableParams.reload();
                    });
                };

                vm.showRestaurarConfirmation = function (usuario) {
                    ModalService.open({
                        templateUrl: 'app/components/form/confirm/confirm.modal.html',
                        controller: 'ConfirmModalController',
                        controllerAs: 'ctrl',
                        resolve: {
                            item: function () {
                                return usuario;
                            },
                            params: function () {
                                return {
                                    title: 'admin.list-users.restaurar.title',
                                    body: 'admin.list-users.restaurar.confirm',
                                    property: 'email'
                                };
                            },
                            /* @ngInject */
                            tipo: function (User) {
                                return User;
                            },
                            funcion: function () {
                                return "restaurar";
                            },
                            parametros: function () {
                                return {id: usuario.id};
                            }
                        }
                    }).result.then(function () {
                        vm.tableParams.reload();
                    });
                };

            }
        });
})(angular);
