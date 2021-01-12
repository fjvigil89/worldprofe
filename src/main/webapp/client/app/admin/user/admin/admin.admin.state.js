(function () {
    'use strict';

    angular
        .module('app')
        .config(stateConfig);

    /* @ngInject */
    function stateConfig($stateProvider, STATE_HELPER) {
        var base = "admin/admin";
        var params = {
            base: base,
            parent: 'admin',
            baseUrl: "/admin",
            templateUrlBase: "app/admin/user/admin/admin",
            controller: 'AdminAdminController',
            controllerForm: 'AdminFormController',
            translateBase: "admin.admin."
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
        angular.merge(state.create, {
            resolve: {
                /* @ngInject */
                itemData: function (User) {
                    return new User();
                }
            }
        });

        // Se definen los estados
        $stateProvider
            .state(params.base, state.parent)
            .state(state.list.name, state.list)
            .state(state.create.name, state.create);
    }
})();
