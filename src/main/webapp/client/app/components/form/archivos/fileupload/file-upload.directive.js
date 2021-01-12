(function () {
    'use strict';

    angular.module('app').directive('fileUpload', fileUpload);

    function fileUpload() {
        return {
            restrict: 'EA',
            scope: {
                id: '@',
                archivo: "=",
                archivoTemporal: "=",
                accept: "@",  // Opcional (default === todas las extensiones)
                error: "=", // Opcional
                url: "@", // Opcional (si no se pasa, no se permite descargar el archivo)
                authorities: "@", // Opcional (si no se indica ningún rol, será visible para todos. Ej:ROLE_ADMIN,ROLE_EDITOR_CONTENIDOS)
                onSelected: "<"
            },
            templateUrl: 'app/components/form/archivos/fileupload/file-upload.html',
            controllerAs: 'ctrl',
            bindToController: true,
            controller: 'FileUploadController'
        }
    }

})();
