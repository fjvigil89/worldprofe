(function () {
    'use strict';

    angular
        .module('app')
        .component('empresaListAdmin', {
            templateUrl: 'app/admin/empresa/empresa-list.admin.html',
            controller: Controller,
            controllerAs: 'ctrl'
        });

    /* @ngInject */
    function Controller($stateParams, NG_TABLE_DEFAULT_PARAMS, NgTableParams, NgTableHelper,
                        ModalService, User, DescargaUtil) {
        var vm = this;

        vm.item = User;
        vm.pagina = $stateParams.page ? $stateParams.page : 1;
        vm.elementosPorPagina = NG_TABLE_DEFAULT_PARAMS.size;
        vm.filter = {
            key: undefined,
            rol: 'ROLE_EMPRESA',
            activos: true
        };
        vm.tableParams = new NgTableParams({
            count: vm.elementosPorPagina,
            page: vm.pagina,
            filter: vm.filter,
            sorting: {email: 'asc'}
        }, NgTableHelper.settings(vm));

        // Descargar

        vm.empresasExcel = function () {
            DescargaUtil.descargar('api/admin/user/empresasExcel', null,
                'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8');
        };
    }

})();
