(function () {
    'use strict';

    angular.module('app')
        .config(stateConfig);

    /* @ngInject */
    function stateConfig($stateProvider) {
        $stateProvider.state('public', {
            abstract: true,
            parent: 'site',
            views: {
                'header@': {
                    templateUrl: 'app/layouts/header/header.html',
                    controller: "HeaderController",
                    controllerAs: 'ctrl'
                },
                'navbar@': {
                    template: '<mi-menu menu="$ctrl.menu" nav-collapsed="$ctrl.navCollapsed" modal="$ctrl.modal(id)"></mi-menu>',
                    /* @ngInject */
                    controller: function (menuData, $timeout, $window, Principal) {
                        this.menu = menuData;
                        this.navCollapsed = $window.innerWidth < 992;

                        $timeout(function () {
                            Principal.identity().then(function (data) {
                                // Si es un alumno con empresa asociada, eliminamos la sección Mis compras
                                if (data && data.rol === 'ROLE_ALUMNO' && data.empresa != null) {
                                    angular.element(".menu [ui-sref='paquetecreditos']").parent().remove();
                                    angular.element(".menu [ui-sref='miespacio/miscompras']").parent().remove();
                                }
                            });
                        });
                    },
                    controllerAs: '$ctrl',
                    resolve: {
                        /* @ngInject */
                        menuData: function (Menu) {
                            return Menu.getMenu('menu-main');
                        }
                    }
                },
                'footer@': {
                    templateUrl: 'app/layouts/footer/footer.html'
                }
            },
            resolve: {
                /* @ngInject */
                isAdmin: function ($rootScope) {
                    $rootScope.isAdmin = false;
                }
            }
        });
    }
})();
