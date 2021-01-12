(function () {
    'use strict';

    angular
        .module('app')
        .config(stateConfig);

    /* @ngInject */
    function stateConfig($stateProvider) {
        $stateProvider
            .state('miespacio/profesor/info', {
                parent: 'miespacio',
                url: '/busqueda-profesores/:id',
                data: {
                    pageTitle: 'global.menu.profesor.info'
                },
                views: {
                    'contentEspacio': {
                        templateUrl: 'app/public/profesor/info/profesor.details.html',
                        controller: 'ProfesorDetailsController',
                        controllerAs: 'ctrl'
                    }
                },
                resolve: {
                    /* @ngInject */
                    profesor: function (User, $stateParams) {
                        return User.getProfesorById({id: $stateParams.id}).$promise;
                    }
                 }
            });
    }
})();
