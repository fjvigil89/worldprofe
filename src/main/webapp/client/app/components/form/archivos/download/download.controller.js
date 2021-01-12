(function () {
    'use strict';

    angular.module('app').controller('DownloadController', DownloadController);

    function DownloadController() {
        var vm = this;

        vm.descargarArchivo = descargarArchivo;

        function descargarArchivo() {
            window.open(vm.url + vm.archivo, '_blank');
        }
    }

})();
