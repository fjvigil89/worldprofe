(function () {
    'use strict';

    angular
        .module('app')
        .config(stateConfig);

    /* @ngInject */
    function stateConfig($stateProvider) {
        $stateProvider
            .state('admin/configuracion', {
                parent: 'admin',
                url: '/admin/configuracion',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'admin.configuracion.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/admin/configuracion/configuracion.html',
                        controller: 'ConfiguracionController',
                        controllerAs: 'ctrl'
                    }
                },
                resolve: {
                    /* @ngInject */
                    configuracion: function (Configuracion) {
                        return Configuracion.get({id: '0'}).$promise.then(function (data) {
                            return data;
                        });
                    }
                },
                ncyBreadcrumb: {
                    label: 'admin.configuracion.title'
                }
            });
    }
})();
