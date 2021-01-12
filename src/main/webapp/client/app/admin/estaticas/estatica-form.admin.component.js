(function (angular) {
    'use strict';

    // estatica: Objeto a modificar.
    // idiomas: Idiomas en los que pueden estar las páginas estáticas.

    angular
        .module('app')
        .component('estaticaForm', {
            bindings: {
                estatica: '<',
                idiomas: '<'
            },
            templateUrl: 'app/admin/estaticas/estatica-form.admin.html',
            controllerAs: 'ctrl',
            controller: function (Estatica) {
                var vm = this;

                vm.mode = 'edit';
                vm.canSave = true;

                vm.init = function (estatica, index, idioma) {
                    if (!estatica.estaticasI18n[index]) estatica.estaticasI18n[index] = {};

                    if (!estatica.estaticasI18n[index].idioma) {
                        estatica.estaticasI18n[index].idioma = idioma;
                    }
                };

                vm.save = function () {
                    // FIXME: La validación dejó de funcionar al utilizar el required. Intentar utilizar la librería de validación para cambiar la pestaña activa
                    var hayPrimeraEstaticaSinTitulo = false;

                    vm.idiomas.forEach(function (idioma, index) {
                        vm.estatica.estaticasI18n.forEach(function (estaticaI18n) {
                            if (estaticaI18n.idioma === idioma && estaticaI18n.titulo === undefined && !hayPrimeraEstaticaSinTitulo) {
                                vm.active = index;
                                hayPrimeraEstaticaSinTitulo = true;
                            }
                        });
                    });

                    if (!hayPrimeraEstaticaSinTitulo) {
                        Estatica.save(vm.estatica).$promise.then(function (data) {
                        });
                    }
                };
            }
        });
})(angular);
