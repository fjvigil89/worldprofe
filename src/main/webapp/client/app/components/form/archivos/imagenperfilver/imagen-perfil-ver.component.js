(function () {
    'use strict';

    angular.module('app')
        .component('imagenPerfilVer', {
            templateUrl: "app/components/form/archivos/imagenperfilver/imagen-perfil-ver.html",
            controllerAs: 'ctrl',
            bindings: {
                usuario: '<',
                mini: '<'
            }
        });

})();
