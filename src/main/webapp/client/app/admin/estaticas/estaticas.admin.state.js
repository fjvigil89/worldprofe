(function () {
    'use strict';

    angular
        .module('app')
        .config(stateConfig);

    /* @ngInject */
    function stateConfig($stateProvider) {
        $stateProvider
            .state('admin/estaticas', {
                parent: 'admin',
                url: '/admin/estaticas/:nombre',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'estaticas.title'
                },
                views: {
                    'content@': {
                        template: '<estatica-form estatica="$resolve.estatica" idiomas="$resolve.idiomas"></estatica-form>',
                        controllerAs: 'ctrl'
                    }
                },
                resolve: {
                    /* @ngInject */
                    estatica: function (Estatica, $stateParams) {
                        return Estatica.getByNombre({nombre: $stateParams.nombre}).$promise.then(function (data) {
                            return data;
                        });
                    },
                    /* @ngInject */
                    idiomas: function (EnumService) {
                        return EnumService.get('estaticas-idioma');
                    }
                },
                ncyBreadcrumb: {
                    label: 'estaticas.title'
                }
            });
    }
})();
