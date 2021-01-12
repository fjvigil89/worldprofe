(function () {
    'use strict';

    angular.module('app')
        .factory('Reserva', Reserva);

    /* @ngInject */
    function Reserva(ResourceHelper) {
        return ResourceHelper.getResource("reserva", {}, {
            'save': {
                method: 'POST',
                url: 'api/public/reserva',
                isArray: false
            },
            'findAll': {
                method: 'GET',
                url: 'api/public/reserva/findAll',
                isArray: false
            },
            'filterByAdmin': {
                method: 'GET',
                url: 'api/admin/reserva/filterByAdmin',
                isArray: false
            },
            'update': {
                method: 'POST',
                url: 'api/public/reserva/update',
                isArray: false
            },
            'devolverCreditos': {
                method: 'POST',
                url: 'api/admin/reserva/devolverCreditos',
                params: {
                    id: '@id'
                }
            },
            'rechazarCreditos': {
                method: 'POST',
                url: 'api/admin/reserva/rechazarCreditos',
                params: {
                    id: '@id'
                }
            }
        });
    }

})();
