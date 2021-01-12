(function () {
    'use strict';

    angular.module('app')
        .config(stateConfig);

    /* @ngInject */
    function stateConfig($stateProvider) {
        $stateProvider
            .state('admin', {
                abstract: true,
                parent: 'site',
                views: {
                    'navbar@': {
                        template: '<mi-menu menu="$ctrl.menu" nav-collapsed="$ctrl.navCollapsed" modal="$ctrl.modal(id)"></mi-menu>',
                        controller: ['menuData', '$window', 'StaticModal', function (menuData, $window) {
                            this.menu = menuData;
                            this.navCollapsed = $window.innerWidth < 992;
                        }],
                        controllerAs: '$ctrl',
                        resolve: {
                            menuData: ['Menu',
                                function (Menu) {
                                    return Menu.getMenu('menu-admin');
                                }
                            ]
                        }
                    },
                    'footer@': {
                        templateUrl: 'app/layouts/footer/footer.admin.html'
                    }
                },
                resolve: {
                    /* @ngInject */
                    isAdmin: function ($rootScope) {
                        $rootScope.isAdmin = true;
                    }
                }
            })
            .state('admin/login', {
                url: '/admin',
                parent: 'admin',
                redirectTo: 'login'
            });
    }
})();
