(function () {
    'use strict';

    angular
        .module('app')
        .config(stateConfig);

    /* @ngInject */
    function stateConfig($stateProvider) {
        $stateProvider
            .state('miespacio/misalumnos', {
                parent: 'miespacio',
                url: '/mis-alumnos',
                data: {
                    authorities: ['ROLE_EMPRESA'],
                    pageTitle: 'global.menu.miespacio.title'
                },
                views: {
                    'contentEspacio': {
                        templateUrl: 'app/public/cuenta/miespacio/misalumnos/mis-alumnos.html',
                        controllerAs: 'ctrl',
                        /* @ngInject */
                        controller: function (Principal) {
                            var vm = this;
                            Principal.identity().then(function (data) {
                                vm.usuario = data;
                            });
                        }
                    }
                },
                resolve: {}
            });
    }
})();
