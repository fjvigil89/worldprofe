(function () {
    'use strict';

    angular
        .module('app')
        .component('misAlumnosList', {
            templateUrl: 'app/public/components/misalumnos/mis-alumnos-list.html',
            controller: Controller,
            controllerAs: 'ctrl'
        });

    /* @ngInject */
    function Controller($state, $stateParams, NG_TABLE_DEFAULT_PARAMS_PUBLIC, NgTableParams, NgTableHelper,
                        User, Principal) {
        var vm = this;

        Principal.identity().then(function (data) {
            vm.usuarioAutenticado = data;
        });

        vm.item = User;
        vm.function = 'findAlumnosByCentro';
        vm.pagina = $stateParams.page ? $stateParams.page : 1;
        vm.elementosPorPagina = NG_TABLE_DEFAULT_PARAMS_PUBLIC.size;
        vm.filter = {
            key: undefined
        };
        vm.tableParams = new NgTableParams({
            count: vm.elementosPorPagina,
            page: vm.pagina,
            filter: vm.filter,
            sorting: {nombre: 'asc'}
        }, NgTableHelper.settings(vm));


    }
})();
