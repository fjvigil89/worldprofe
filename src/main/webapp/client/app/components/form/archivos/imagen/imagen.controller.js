(function () {
    'use strict';

    angular.module('app').controller('ImagenController', ImagenController);

    /* @ngInject */
    function ImagenController($timeout, Upload, AlertService) {

        var vm = this;

        activate();

        function activate() {
            if (!vm.ngModel.rutaImagen) {
                if (vm.ngModel.path) {
                    vm.ngModel.rutaImagen = vm.rutaCarpetaImagen + "/n_" + vm.ngModel.path;
                } else {
                    vm.ngModel.rutaImagen = null;
                }
            }

            if (vm.edicion === null || vm.edicion === undefined) {
                vm.edicion = true;
            }
        }

        function onUploaded() {
            vm.onUploadedImage();
        }

        vm.isSubmitted = function (form) {
            while (!!form) {
                if (form.$submitted) return true;
                form = form.$$parentForm;
            }
            return false;
        };

        vm.eliminarImagen = function () {
            vm.ngModel.eliminar = true;
            vm.ngModel.rutaImagen = null;
            vm.ngModel.path = null;
            vm.ngModel.archivoTemporal = null;

            // Llamamos al callback para eliminar el elemento de la lista
            vm.onRemoveImage({imagen: vm.ngModel});
        };

        function generateThumb(file) {
            if (file != null) {
                if (file.type.indexOf('image') > -1) {
                    $timeout(function () {
                        var fileReader = new FileReader();
                        fileReader.readAsDataURL(file);
                        fileReader.onload = function (e) {
                            $timeout(function () {
                                vm.ngModel.rutaImagen = e.target.result;
                            });
                        }
                    });
                }
            }
        }

        vm.onImagenSelect = function (files, form, indice) {
            vm.ngModel.eliminar = false;
            if (files && files.length) {

                vm.ngModel.path = null;
                vm.progresoImagen = true;

                form.$setValidity('imagenSubida', false);

                // Previsualizamos imagen
                generateThumb(files[0]);

                // Subimos imagen
                Upload.upload({
                    url: "api/archivos-temporales",
                    data: {
                        file: files[0],
                        ext: "jpeg,png,gif,jpg"
                    },
                    method: 'POST'
                }).success(function (response) {
                    vm.progresoImagen = null;
                    vm.ngModel.archivoTemporal = response.msg;
                    vm.ngModel.path = files[0].name;
                    form.$setValidity('imagenSubida', true);
                    onUploaded();
                }).error(function (data) {
                    vm.progresoImagen = null;
                    form.$setValidity('imagenSubida', true);
                    vm.eliminarImagen();
                    if (data && data.msg === 'invalid extension') {
                        AlertService.error("error.invalidextension", {archivo: files[0].name});
                    } else {
                        AlertService.error("admin.form.error.fail");
                    }
                });
            }
        };

        vm.clickUpload = function () {
            document.getElementById('inputFoto' + vm.indice).click();
        };
    }

})();
