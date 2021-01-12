(function () {
    'use strict';

    angular
        .module('app')
        .config(stateConfig);

    /* @ngInject */
    function stateConfig($stateProvider) {
        $stateProvider
            .state('miespacio/info', {
                parent: 'public',
                url: '/mi-espacio',
                data: {
                    pageTitle: 'global.menu.miespacio.title'
                },
                views: {
                    'content@': {
                        /* @ngInject */
                        controller: function ($state, Principal) {
                            if (Principal.isAuthenticated()) {
                                Principal.identity().then(function (data) {
                                    if (data && data.rol === 'ROLE_ALUMNO' || data.rol === 'ROLE_PROFESOR') {
                                        $state.go('miespacio/miagenda');
                                    } else if (data.rol === 'ROLE_EMPRESA') {
                                        $state.go('miespacio/misalumnos');
                                    } else {
                                        $state.go('home');
                                    }
                                });
                            } else {
                                $state.go('registro/info');
                            }
                        },
                        controllerAs: 'ctrl'
                    }
                }
            })
            .state('miespacio', {
                parent: 'public',
                redirectTo: 'miespacio/miagenda',
                url: '/mi-espacio',
                data: {
                    authorities: ['ROLE_AUTHENTICATED'],
                    pageTitle: 'global.menu.miespacio.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/public/cuenta/miespacio/mi-espacio.template.html',
                        controllerAs: 'ctrl'
                    }
                }
            });
    }
})();
