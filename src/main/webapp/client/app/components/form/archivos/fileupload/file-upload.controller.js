(function () {
    'use strict';

    angular.module('app').controller('FileUploadController', FileUploadController);

    /* @ngInject */
    function FileUploadController(Upload, AlertService) {
        var vm = this;

        vm.onArchivoSelect = onArchivoSelect;
        vm.eliminarArchivo = eliminarArchivo;

        vm.isSubmitted = isSubmitted;

        vm.upload = upload;

        function upload() {
            setTimeout(function () {
                angular.element("input#" + vm.id).trigger('click');
            }, 0);
        }

        function isSubmitted(form) {
            while (!!form) {
                if (form.$submitted)
                    return true;
                form = form.$$parentForm;
            }
            return false;
        }

        function onArchivoSelect(files, form) {
            console.log(form);

            if (files && files.length) {
                vm.progresoArchivo = true;
                vm.archivo = files[0].name;
                form.$setValidity('archivoSubido', false);
                Upload.upload({
                    url: "api/archivos-temporales",
                    data: {
                        file: files[0],
                        ext: vm.accept
                    },
                    method: 'POST'
                }).success(function (response) {
                    console.log("Archivo subido");
                    vm.progresoArchivo = false;
                    vm.archivoTemporal = response.msg;
                    form.$setValidity('archivoSubido', true);

                    if (vm.onSelected) {
                        vm.onSelected(vm.archivoTemporal, vm.archivo);
                    }

                }).error(function (data) {
                    console.log("Error subiendo");
                    vm.progresoArchivo = false;
                    vm.archivo = null;
                    form.$setValidity('archivoSubido', true);
                    if (data && data.msg === 'invalid extension') {
                        AlertService.error("error.invalidextension", {archivo: files[0].name});
                    } else {
                        AlertService.error("admin.form.error.fail");
                    }
                });
            }
        }

        function eliminarArchivo() {
            vm.archivo = null;
        }

    }

})();
