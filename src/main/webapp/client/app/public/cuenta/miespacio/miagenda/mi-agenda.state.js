(function () {
    'use strict';

    angular
        .module('app')
        .config(stateConfig);

    /* @ngInject */
    function stateConfig($stateProvider) {
        $stateProvider
            .state('miespacio/miagenda', {
                parent: 'miespacio',
                url: '/mi-agenda',
                redirectTo: 'miespacio/miagenda/estado',
                data: {
                    // Gestión de roles en el resolve
                    authorities: ['ROLE_ALUMNO', 'ROLE_PROFESOR'],
                    pageTitle: 'global.menu.miespacio.title'
                },
                views: {
                    'contentEspacio': {
                        templateUrl: 'app/public/cuenta/miespacio/miagenda/mi-agenda.html',
                        /* @ngInject */
                        controller: function (AlertService, Principal) {
                            Principal.identity().then(function (data) {
                                AlertService.info("profesor.zonahoraria", {gmt: data.zonaHoraria.gmt});
                            });
                        },
                        controllerAs: 'ctrl'
                    }
                }
            })
            .state('miespacio/miagenda/estado', {
                parent: 'miespacio/miagenda',
                url: '/canceladas-o-rechazadas/:canceladasRechazadas',
                params: {
                    canceladasRechazadas: 'false'
                },
                views: {
                    'contentMiagenda': {
                        template: '<mi-agenda usuario-autenticado="$resolve.usuarioAutenticado"></mi-agenda>',
                        controllerAs: 'ctrl'
                    }
                },
                resolve: {
                    /* @ngInject */
                    usuarioAutenticado: function (Principal) {
                        return Principal.identity().then(function (data) {
                            return data;
                        });
                    }
                }
            });
    }
})();
