(function (angular) {
    'use strict';

    angular
        .module('app')
        .controller('ProfesorListController', ProfesorListController);

    /* @ngInject */
    function ProfesorListController(User, $state, $scope) {
        var vm = this;

        vm.profesores = [];

        vm.filter = {
            activos: true,
            idioma: null,
            nivel: null,
            paises: [], /*disponibilidad: 'AMBOS',*/
            capacidades: [],
            horadia: null,
            dia: null
        };

        $scope.$watch(function () {
            return vm.filter;
        }, function (newValue, oldValue) {
            if (newValue.idioma === oldValue.idioma && newValue.nivel === oldValue.nivel &&
                newValue.paises.length === oldValue.paises.length /*&& newValue.disponibilidad === oldValue.disponibilidad*/ &&
                newValue.capacidades.length === oldValue.capacidades.length && newValue.horadia === oldValue.horadia &&
                newValue.dia === oldValue.dia) {
                return;
            }
            vm.filter.size = 10;
            vm.filter.page = 0;
            User.findAllProfesor(vm.filter).$promise.then(function (data) {
                vm.profesores = data.content;
            });
        }, true);

        vm.abrir = function (id) {
            $state.go("profesor/info", {id: id});
        };

        // Infinite scroll

        vm.loading = false;
        vm.loaded = false;

        vm.filter.size = 10;
        vm.filter.page = 0;

        vm.cargar = function () {
            if (vm.loading) return;
            vm.loading = true;
            User.findAllProfesor(vm.filter).$promise.then(function (data) {
                for (var i = 0; i <= data.content.length - 1; i++) {
                    vm.profesores.push(data.content[i]);
                }
                vm.filter.page += 1;
                vm.loading = false;
                if (data.content.length < vm.filter.size) vm.loaded = true;
            });
        };

    }

})(angular);
