(function () {
    'use strict';

    angular
        .module('app')
        .config(stateConfig);

    /* @ngInject */
    function stateConfig($stateProvider, STATE_HELPER) {
        var base = "admin/reserva";
        var params = {
            base: base,
            baseUrl: "/" + base,
            translateBase: "admin.reserva.",
            templateList: '<reserva-list estados="$resolve.estados"></reserva-list>',
            parent: 'admin'
        };

        var state = STATE_HELPER(params);
        // Estado Padre
        angular.merge(state.parent, {
            data: {
                authorities: ['ROLE_ADMIN']
            },
            params: {
                revisadas: null
            },
            resolve: {
                /* @ngInject */
                estados: function (EnumService) {
                    return EnumService.get('estado');
                }
            }
        });
        // Listar
        angular.merge(state.list, {});

        // Se definen los estados
        $stateProvider
            .state(params.base, state.parent)
            .state(state.list.name, state.list);
    }
})();
