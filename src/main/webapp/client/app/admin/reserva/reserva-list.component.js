(function () {
    'use strict';
//TODO: Mover a componentes
    angular
        .module('app')
        .component('reservaList', {
            templateUrl: 'app/admin/reserva/reserva-list.html',
            controller: Controller,
            controllerAs: 'ctrl',
            bindings: {
                estados: '<'
            }
        });

    /* @ngInject */
    function Controller($stateParams, NG_TABLE_DEFAULT_PARAMS, NgTableParams, NgTableHelper,
                        ModalService, Reserva, Principal, $state) {
        var vm = this;

        vm.item = Reserva;
        vm.pagina = $stateParams.page ? $stateParams.page : 1;
        vm.elementosPorPagina = NG_TABLE_DEFAULT_PARAMS.size;
        vm.revisadas = $stateParams.revisadas;
        vm.filter = {
            key: undefined,
            estado: undefined,
            revisadas: $stateParams.revisadas
        };
        vm.function = 'filterByAdmin';
        vm.tableParams = new NgTableParams({
            count: vm.elementosPorPagina,
            page: vm.pagina,
            filter: vm.filter,
            sorting: {fecha: 'desc'}
        }, NgTableHelper.settings(vm));

        // Acciones

        vm.devolver = function (id) {
            Reserva.devolverCreditos({id: id}).$promise.then(function () {
                $state.go('admin/reserva/list', {revisadas: false}, {reload: true});
            });
        };

        vm.rechazar = function (id) {
            Reserva.rechazarCreditos({id: id}).$promise.then(function () {
                $state.go('admin/reserva/list', {revisadas: false}, {reload: true});
            });
        }
    }

})();
