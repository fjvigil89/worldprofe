(function () {
    'use strict';

    angular
        .module('app')
        .config(stateConfig);

    /* @ngInject */
    function stateConfig($stateProvider, STATE_HELPER, ALUMNO_FORM_ADMIN) {
        var config = ALUMNO_FORM_ADMIN();
        var base = "admin/empresa/alumno";
        var params = {
            base: base,
            baseUrl: "/:empresaId/alumno",
            translateBase: "admin.alumno.",
            templateList: '<alumno-list-admin empresa="$resolve.empresa"></alumno-list-admin>',
            templateForm: config.component,
            parent: 'admin/empresa'
        };

        var state = STATE_HELPER(params);
        // Estado Padre
        angular.merge(state.parent, {
            data: {
                authorities: ['ROLE_ADMIN']
            },
            resolve: angular.merge(config.resolve, {
                /* @ngInject */
                empresa: function (User, $stateParams) {
                    return User.getEmpresaDTO({id: $stateParams.empresaId}).$promise.then(function(data) {
                        return data;
                    });
                }
            }),
            ncyBreadcrumb: {
                parent: 'admin/empresa/list'
            }
        });
        // Listar
        angular.merge(state.list, {

        });
        // Crear
        angular.merge(state.create, { resolve: config.resolve});
        // Editar
        angular.merge(state.edit, {
            resolve: angular.merge(config.resolve, {
                /* @ngInject */
                item: function (User, $stateParams) {
                    return User.getById({id: $stateParams.id}).$promise;
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
