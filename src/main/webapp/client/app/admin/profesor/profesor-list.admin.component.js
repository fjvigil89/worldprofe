(function () {
    'use strict';

    angular
        .module('app')
        .component('profesorListAdmin', {
            templateUrl: 'app/admin/profesor/profesor-list.admin.html',
            controller: Controller,
            controllerAs: 'ctrl'
        });

    /* @ngInject */
    function Controller($stateParams, NG_TABLE_DEFAULT_PARAMS, NgTableParams, NgTableHelper,
                        ModalService, User, DescargaUtil) {
        var vm = this;

        vm.item = User;
        vm.function = 'findProfesoresAdmin';
        vm.pagina = $stateParams.page ? $stateParams.page : 1;
        vm.elementosPorPagina = NG_TABLE_DEFAULT_PARAMS.size;
        vm.filter = {
            key: undefined,
            rol: 'ROLE_PROFESOR',
            activos: true
        };
        vm.tableParams = new NgTableParams({
            count: vm.elementosPorPagina,
            page: vm.pagina,
            filter: vm.filter,
            sorting: {nombre: 'asc'}
        }, NgTableHelper.settings(vm));

        // Descarga

        vm.profesoresExcel = function () {
            DescargaUtil.descargar('api/admin/user/profesorExcel', {
                    activos: vm.filter.activos,
                    mes: vm.filter.mes,
                    ano: vm.filter.ano
                },
                'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8');
        };

        // Lista de años
        vm.years = [];
        for (var i = 2017; i <= (new Date()).getFullYear(); i++) {
            vm.years.push(i);
        }

        // Lista de meses
        vm.months = [];
        for (var j = 1; j <= 12; j++) {
            vm.months.push(j);
        }

    }

})();
