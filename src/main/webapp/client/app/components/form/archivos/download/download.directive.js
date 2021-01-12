(function () {
    'use strict';

    angular.module('app').directive('download', download);

    function download() {

        return {
            restrict: 'EA',
            scope: {
                archivo: "=",
                url: "@",
                showLabel: "="
            },
            templateUrl: 'app/components/form/archivos/download/download.html',
            controllerAs: 'ctrl',
            bindToController: true,
            controller: 'DownloadController'
        }
    }

})();
