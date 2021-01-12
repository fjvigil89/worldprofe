(function () {
    'use strict';

    angular
        .module('app')
        .config(stateConfig);

    /* @ngInject */
    function stateConfig($stateProvider) {
        $stateProvider
            .state('miespacio/perfil', {
                parent: 'miespacio',
                url: '/perfil',
                data: {
                    authorities: ['ROLE_AUTHENTICATED'],
                    pageTitle: 'global.menu.miespacio.title'
                },
                views: {
                    'contentEspacio': {
                        templateUrl: 'app/public/cuenta/miespacio/perfil/perfil.form.html',
                        controller: 'PerfilFormController',
                        controllerAs: 'ctrl'
                    }
                },
                resolve: {
                    /* @ngInject */
                    itemData: function (Principal) {
                        return Principal.identity().then(function (data) {
                            return data;
                        });
                    }
                }
            })
            .state('miespacio/perfil/contrasena', {
                parent: 'miespacio',
                url: '/perfil/modificar-contrasena',
                views: {
                    'contentEspacio': {
                        template: "<h1>{{'miespacio.miperfil.cambiarcontrasena' | translate}}</h1>" +
                        "<change-password></change-password>"
                    }
                }
            });
    }
})();
