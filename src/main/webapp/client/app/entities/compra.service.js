(function () {
    'use strict';

    angular.module('app')
        .factory('Compra', Compra);

    /* @ngInject */
    function Compra(ResourceHelper) {
        return ResourceHelper.getResource("compra", {}, {
            'findAllByUsuarioActivo': {
                method: 'GET',
                url: 'api/public/compra/findAllByUsuarioActivo',
                isArray: false
            },
/*            'findAllByUsuario': {
                method: 'GET',
                url: 'api/admin/compra/findAllByUsuario',
                isArray: false
            },*/
            'crear': {
                method: 'GET',
                url: 'api/public/compra/crear',
                isArray: false
            },
            'iniciarPaypal': {
                method: 'GET',
                url: 'api/public/compra/iniciar-paypal',
                isArray: false
            },
            'confirmarPaypal': {
                method: 'GET',
                url: 'api/public/compra/confirmar-paypal',
                isArray: false
            },
            'iniciarPaypalAlumno': {
                method: 'GET',
                url: 'api/public/compra/iniciar-paypal-alumno',
                isArray: false
            },
            'cancelar': {
                method: 'GET',
                url: 'api/public/compra/cancelar',
                isArray: false
            }
        });
    }

})();
