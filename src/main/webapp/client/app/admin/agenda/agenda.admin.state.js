(function () {
    'use strict';

    angular
        .module('app')
        .config(stateConfig);

    /* @ngInject */
    function stateConfig($stateProvider) {
        $stateProvider
            .state('admin/profesor/agenda', {
                parent: 'admin/profesor',
                url: '/agenda/:usuarioId?:mes&:ano',
                data: {
                    authorities: ['ROLE_ADMIN']
                },
                params: {
                    // canceladasRechazadas: 'false'
                    usuarioId: null,
                    mes: null,
                    ano: null
                },
                views: {
                    'content@': {
                        template: '<h1>{{$resolve.usuario.nombre}} {{$resolve.usuario.apellidos}} <small>({{$resolve.usuario.email}})</small></h1>' +
                            '<mi-agenda usuario-autenticado="$resolve.usuario" admin="true"' +
                            ' init-month="$resolve.mes" init-year="$resolve.ano"></mi-agenda>',
                        controllerAs: 'ctrl'
                    }
                },
                resolve: {
                    /* @ngInject */
                    usuario: function ($stateParams, User) {
                        return User.getById({id: $stateParams.usuarioId}).$promise;
                    },
                    /* @ngInject */
                    mes: function ($stateParams) {
                        return $stateParams.mes;
                    },
                    /* @ngInject */
                    ano: function ($stateParams) {
                        return $stateParams.ano;
                    }
                },
                ncyBreadcrumb: {
                    label: "admin.profesor.agenda.title",
                    parent: 'admin/profesor/list'
                }
            });
    }
})();
