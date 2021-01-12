(function () {
    'use strict';

    angular.module('app')
        .factory('DatosFacturacion', Service);

    /* @ngInject */
    function Service($resource) {
        var url = 'api/public/datosfacturacion';
        return $resource(url, {}, {
            'getDatosFacturacionDeUsuario': {
                method: 'GET',
                url: url + '/getDatosFacturacionDeUsuario'
            }
        });
    }

})();
