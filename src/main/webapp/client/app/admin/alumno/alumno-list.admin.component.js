(function () {
    'use strict';

    angular
        .module('app')
        .component('alumnoListAdmin', {
            templateUrl: 'app/admin/alumno/alumno-list.admin.html',
            controller: Controller,
            controllerAs: 'ctrl',
            bindings: {
                empresa: '<'
            }
        });

    /* @ngInject */
    function Controller($stateParams, NG_TABLE_DEFAULT_PARAMS, NgTableParams, NgTableHelper,
                        ModalService, User, DescargaUtil) {
        var vm = this;

        vm.estadoEditar = vm.empresa != null ? 'empresa/alumno' : 'alumno';

        vm.item = User;
        vm.pagina = $stateParams.page ? $stateParams.page : 1;
        vm.elementosPorPagina = NG_TABLE_DEFAULT_PARAMS.size;
        vm.empresaId = $stateParams.empresaId;

        vm.filter = {
            key: undefined,
            rol: 'ROLE_ALUMNO',
            activos: true,
            empresa: vm.empresaId
        };
        vm.tableParams = new NgTableParams({
            count: vm.elementosPorPagina,
            page: vm.pagina,
            filter: vm.filter,
            sorting: {nombre: 'asc'}
        }, NgTableHelper.settings(vm));

        // Descargar

        vm.empresaAlumnosExcel = function (id) {
            DescargaUtil.descargar('api/admin/user/empresaAlumnosExcel', {id: id},
                'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8');
        };
    }

})();
