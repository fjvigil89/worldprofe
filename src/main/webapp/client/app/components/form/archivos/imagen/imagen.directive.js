(function () {
    'use strict';

    angular.module('app').directive('imagen', imagen);

    function imagen() {
        return {
            restrict: 'E',
            scope: {
                ngModel: "=",
                indice: "=",
                ngModelRadio: "=",
                imagenTemporal: "=",
                rutaCarpetaImagen: "@", // Opcional
                rutaImagenDefault: "@", // Opcional
                onUploadedImage: '&',
                onRemoveImage: '&',
                edicion: "=", // Opcional (default = true)
                error: "="
            },
            templateUrl: "app/components/form/archivos/imagen/imagen.html",
            controllerAs: 'ctrl',
            bindToController: true,
            controller: 'ImagenController'
        }
    }

})();
