(function (angular) {
    'use strict';

    // Componente para la paginación. Se modifica el parámetro :page del estado.
    // El nº de elementos por página va en una constante.

    // currentPage: Página activa.
    // totalElements: Número de elementos totales.

    angular
        .module('app')
        .component('pagination', {
            bindings: {
                currentPage: "<",
                totalElements: "<"
            },
            templateUrl: 'app/components/pagination/pagination.html',
            /* @ngInject */
            controller: function ($state, NG_LIST_DEFAULT_PARAMS_PUBLIC) {
                var vm = this;

                vm.itemsPerPage = NG_LIST_DEFAULT_PARAMS_PUBLIC.size;

                vm.pageChanged = function () {
                    var params = angular.copy($state.params);
                    params.page = vm.currentPage;
                    $state.go($state.current, params);
                };

            },
            controllerAs: 'ctrl'
        });
})(angular);
