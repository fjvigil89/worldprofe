(function (angular) {
    'use strict';

    // Componente para la selección del idioma de la aplicación (Español e inglés).
    // Métodos relacionados: app.module.js y app.config.js

    // TODO: Mejorar haciendo que obtenga los idiomas de un enumerado del servidor.

    angular
        .module('app')
        .component('idiomas', {
            templateUrl: 'app/components/idiomas/idiomas.html',
            /* @ngInject */
            controller: function ($rootScope, $localStorage) {
                var vm = this;

                vm.changeLanguage = function (lang) {
                    if (lang) {
                        $rootScope.changeLanguage(lang);
                    }
                    vm.lang = $localStorage.lang;
                };

                vm.changeLanguage();

            },
            controllerAs: 'ctrl'
        });
})(angular);
