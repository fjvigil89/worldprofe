(function () {
    'use strict';

    angular.module('app')
        .component('imagenPerfil', {
            bindings: {
                ngModel: "=",
                indice: "=",
                imagenTemporal: "=",
                rutaCarpetaImagen: "@", // Opcional
                rutaImagenDefault: "@", // Opcional
                onUploadedImage: '&',
                onRemoveImage: '&'
            },
            templateUrl: "app/components/form/archivos/imagenperfil/imagen-perfil.html",
            controllerAs: 'ctrl',
            controller: ImagenPerfil
        });

    /* @ngInject */
    function ImagenPerfil(Upload, AlertService) {

        var vm = this;
        vm.editPreview = false;
        vm.myCroppedImage = '';
        vm.myImage = '';

        activate();

        function activate() {
            if (!vm.ngModel.rutaImagen) {
                if (vm.ngModel.path) {
                    vm.ngModel.rutaImagen = vm.rutaCarpetaImagen + "/" + vm.ngModel.path;
                    vm.myCroppedImage = vm.ngModel.rutaImagen;
                } else {
                    vm.ngModel.rutaImagen = null;
                }
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
            vm.myCroppedImage = '';

            // Llamamos al callback para eliminar el elemento de la lista
            vm.onRemoveImage({imagen: vm.ngModel});
        };

        function blobToFile(theBlob, fileName) {
            // A Blob() is almost a File() - it's just missing the two
            // properties below which we will add
            theBlob.lastModifiedDate = new Date();
            theBlob.name = fileName;
            return theBlob;
        }

        function dataURItoBlob(dataURI, type) {
            // convert base64 to raw binary data held in a string
            var byteString = atob(dataURI.split(',')[1]);

            // write the bytes of the string to an ArrayBuffer
            var ab = new ArrayBuffer(byteString.length);
            var ia = new Uint8Array(ab);
            for (var i = 0; i < byteString.length; i++) {
                ia[i] = byteString.charCodeAt(i);
            }

            // write the ArrayBuffer to a blob, and you're done
            return new Blob([ab], {type: type});
        }

        vm.onImagenSelect = function (files, form) {
            vm.editPreview = false;
            vm.ngModel.eliminar = false;
            if (files && files.length) {

                vm.ngModel.path = null;
                vm.progresoImagen = true;

                form.$setValidity('imagenSubida', false);

                // Subimos imagen
                Upload.upload({
                    url: "api/archivos-temporales",
                    data: {
                        file: blobToFile(dataURItoBlob(files, 'image/png'), vm.myImage.name),
                        ext: "jpeg,png,gif,jpg"
                    },
                    method: 'POST'
                }).success(function (response) {
                    vm.progresoImagen = null;
                    vm.ngModel.archivoTemporal = response.msg;
                    vm.ngModel.path = vm.myImage.name;
                    form.$setValidity('imagenSubida', true);
                    onUploaded();
                }).error(function (data) {
                    vm.progresoImagen = null;
                    form.$setValidity('imagenSubida', true);
                    vm.eliminarImagen();
                    if (data && data.msg === 'invalid extension') {
                        AlertService.error("error.invalidextension", {archivo: vm.myImage.name});
                    } else {
                        AlertService.error("admin.form.error.fail");
                    }
                });
            }
        };

        vm.clickUpload = function () {
            var indice = vm.indice ? vm.indice : '';
            document.getElementById('inputFoto' + indice).click();
        };

        vm.preview = function (files) {
            vm.myImage = files[0];
            vm.editPreview = true;
        }


    }

})();
