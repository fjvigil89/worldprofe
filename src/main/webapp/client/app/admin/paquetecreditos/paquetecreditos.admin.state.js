(function () {
    'use strict';

    angular
        .module('app')
        .config(stateConfig);

    /* @ngInject */
    function stateConfig($stateProvider, STATE_HELPER, PAQUETECREDITOS_FORM_ADMIN) {
        var config = PAQUETECREDITOS_FORM_ADMIN();
        var base = "admin/paquetecreditos";
        var params = {
            base: base,
            baseUrl: "/" + base,
            translateBase: "admin.paquetecreditos.",
            templateList: '<paquetecreditos-list></paquetecreditos-list>',
            templateForm: config.component,
            parent: 'admin'
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
        angular.merge(state.create, { resolve: config.resolve});
        // Editar
        angular.merge(state.edit, {
            resolve: angular.merge(config.resolve, {
                /* @ngInject */
                item: function (Paquetecreditos, $stateParams) {
                    return Paquetecreditos.get({id: $stateParams.id}).$promise;
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
