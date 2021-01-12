(function () {
    'use strict';

    angular
        .module('app')
        .config(stateConfig);

    /* @ngInject */
    function stateConfig($stateProvider, STATE_HELPER, CAPACIDAD_FORM_ADMIN) {
        var config = CAPACIDAD_FORM_ADMIN();
        var base = "admin/capacidad";
        var params = {
            base: base,
            baseUrl: "/" + base,
            translateBase: "admin.capacidad.",
            templateList: '<capacidad-list-admin></capacidad-list-admin>',
            templateForm: config.component
        };

        var state = STATE_HELPER(params);
        // Estado Padre
        angular.merge(state.parent, {
            data: {
                authorities: ['ROLE_ADMIN']
            }
        });
        // Listar
        angular.merge(state.list, {});
        // Crear
        angular.merge(state.create, {resolve: config.resolve});
        // Editar
        angular.merge(state.edit, {
            resolve: angular.merge(config.resolve, {
                /* @ngInject */
                item: function (Capacidad, $stateParams) {
                    return Capacidad.get({id: $stateParams.id}).$promise;
                }
            })
        });

        // Se definen los estados
        $stateProvider
            .state(params.base, state.parent)
            .state(state.list.name, state.list)
            .state(state.create.name, state.create)
            .state(state.edit.name, state.edit);
    }
})();
