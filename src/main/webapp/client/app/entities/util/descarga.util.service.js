(function () {
    'use strict';

    angular.module('app')
        .factory('DescargaUtil', Service);

    /* @ngInject */
    function Service($http) {
        return {
            descargar: descargar
        };

        function descargar(url, params, type) {
            $http({
                method: 'GET',
                url: url,
                params: params,
                responseType: "arraybuffer"
            }).then(function (result) {
                var file = new Blob([result.data], {type: type});
                var contentDisposition = result.headers("Content-Disposition");
                var filename = contentDisposition.split(";")[1].trim().split('=')[1].replace(/"/g, '');
                saveAs(file, filename);
            });
        }
    }

})();
