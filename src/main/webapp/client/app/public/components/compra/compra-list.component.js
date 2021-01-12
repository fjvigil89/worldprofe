(function () {
    'use strict';

    angular
        .module('app')
        .component('compraList', {
            templateUrl: 'app/public/components/compra/compra-list.html',
            controller: Controller,
            controllerAs: 'ctrl',
            bindings: {
                usuario: '<'
            }
        });

    /* @ngInject */
    function Controller($stateParams, NG_TABLE_DEFAULT_PARAMS, NgTableParams, NgTableHelper,
                        ModalService, Compra) {
        var vm = this;

        vm.item = Compra;
        vm.pagina = $stateParams.page ? $stateParams.page : 1;
        vm.elementosPorPagina = NG_TABLE_DEFAULT_PARAMS.size;
        //vm.paquetecreditosId = $stateParams.paquetecreditosId;
        vm.filter = {
            key: undefined
        };
        vm.function = 'findAllByUsuarioActivo';
        vm.tableParams = new NgTableParams({
            count: vm.elementosPorPagina,
            page: vm.pagina,
            filter: vm.filter,
            sorting: {fecha: 'desc'}
        }, NgTableHelper.settings(vm));

        // Eliminar
        vm.showRemoveConfirmation = function (id) {
            ModalService.open({
                    templateUrl: 'app/components/form/delete/entity.delete.modal.html',
                    controller: 'EntityDeleteModalController',
                    controllerAs: 'ctrl',
                    resolve: {
                        /* @ngInject */
                        item: function () {
                            return Compra.get({id: id}).$promise;
                        },
                        params: function () {
                            return {
                                title: 'admin.compra.delete.title',
                                body: 'admin.compra.delete.confirm',
                                property: 'fecha'
                            };
                        }
                    }
                }
            ).result.then(function () {
                vm.tableParams.reload();
            });
        };
    }

})();
