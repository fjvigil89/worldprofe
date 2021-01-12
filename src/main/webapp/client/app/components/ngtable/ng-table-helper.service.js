(function () {
    'use strict';

    angular.module('app')
        .factory('NgTableHelper', NgTableHelper);

    /* @ngInject */
    function NgTableHelper($state) {
        return {
            settings: settings
        };

        function settings(vm) {
            var firstLoad = true;

            if (!vm.function) {
                vm.function = "findAll";
            }

            return {
                defaultSort: 'asc',
                counts: [],
                getData: function (params) {
                    if (firstLoad || vm.reload) {
                        vm.loading = true;
                    }
                    firstLoad = false;

                    var sortProperty = null;
                    var sortDir = null;
                    if (params.orderBy()[0]) {
                        sortProperty = params.orderBy()[0].replace("-", "").replace("+", "");
                        sortDir = params.sorting()[sortProperty].toUpperCase();
                    }

                    var request = {
                        size: vm.elementosPorPagina,
                        page: params.page() - 1,
                        sortProperty: sortProperty,
                        sortDirection: sortDir,
                        genericFilter: vm.filter.key
                    };

                    angular.extend(request, vm.filter);

                    // Modificamos la URL con el nuevo número de página
                    $state.go('.', {page: params.page()}, {notify: false});

                    return vm.item[vm.function](request).$promise.then(function (data) {
                        vm.loading = false;
                        if (Array.isArray(data)) {
                            params.total(data.length);
                            params.count(data.length);
                            vm.empty = !data || data.length == 0;
                            return data;
                        } else {
                            params.total(data.totalElements);
                            vm.empty = !data.content || data.content.length == 0;
                            return data.content;
                        }
                    });
                }
            }
        }
    }
})
();
