(function () {
    'use strict';

    angular
        .module('app')
        .config(stateConfig);

    /* @ngInject */
    function stateConfig($stateProvider) {
        $stateProvider.state('contrasena', {
            parent: 'admin',
            url: '/admin/modificar-contrasena',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'admin.register.title'
            },
            views: {
                'content@': {
                    template: '<div class="row"><div class="col-md-8 col-md-offset-2">' +
                    '<h1 translate="admin.changepassword.title"></h1><change-password></change-password>' +
                    '</div></div>'
                }
            },
            ncyBreadcrumb: {
                skip: true
            }
        });
    }
})();
