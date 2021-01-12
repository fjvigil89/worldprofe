(function () {
    'use strict';

    angular
        .module('app')
        .component('paquetecreditosList', {
            templateUrl: 'app/public/components/paquetecreditos/paquetecreditos-list.html',
            controller: Controller,
            controllerAs: 'ctrl'
        });

    /* @ngInject */
    function Controller($stateParams, NG_TABLE_DEFAULT_PARAMS, NgTableParams, NgTableHelper,
                        ModalService, Paquetecreditos, Principal, $state, Auth) {
        var vm = this;

        Principal.identity().then(function (data) {
            vm.usuarioAutenticado = data;
        });

        vm.item = Paquetecreditos;
        vm.pagina = $stateParams.page ? $stateParams.page : 1;
        vm.elementosPorPagina = NG_TABLE_DEFAULT_PARAMS.size;
        vm.paquetecreditosId = $stateParams.paquetecreditosId;
        vm.filter = {
            key: undefined,
            size: null
        };
        vm.tableParams = new NgTableParams({
            count: vm.elementosPorPagina,
            page: vm.pagina,
            filter: vm.filter,
            sorting: {creditos: 'asc'}
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
                            return Paquetecreditos.get({id: id}).$promise;
                        },
                        params: function () {
                            return {
                                title: 'admin.paquetecreditos.delete.title',
                                body: 'admin.paquetecreditos.delete.confirm',
                                property: 'creditos'
                            };
                        }
                    }
                }
            ).result.then(function () {
                vm.tableParams.reload();
            });
        };

        vm.comprarPaquetecreditos = function (id) {
            if (vm.usuarioAutenticado == null) {
                Auth.authorize(false, "ROLE_AUTHENTICATED");
            } else {
                $state.go('paquetecreditos/comprar', {paqueteId: id});
            }
        }
    }

})();
