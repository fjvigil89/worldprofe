(function () {
    'use strict';

    angular
        .module('app')
        .config(stateConfig);

    /* @ngInject */
    function stateConfig($stateProvider) {
        $stateProvider
            .state('profesor/list', {
                parent: 'public',
                url: '/busqueda-profesores',
                data: {
                    pageTitle: 'global.profesores'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/public/profesor/list/profesor.list.html',
                        controller: 'ProfesorListController',
                        controllerAs: 'ctrl'
                    }
                },
                resolve: {
                    /* @ngInject */
                    idiomas: function (Idioma) {
                        return Idioma.findAll({sortProperty: 'nombre'}).$promise.then(function (data) {
                            return data.content;
                        });
                    },
                    /* @ngInject */
                    niveles: function (Nivel) {
                        return Nivel.query().$promise;
                    },
                    /* @ngInject */
                    paises: function (Pais) {
                        return Pais.getAllWithProfesores().$promise;
                    },
                    /* @ngInject */
                    /*disponibilidades: function (EnumService) {
                        return EnumService.get('disponibilidad');
                    },*/
                    /* @ngInject */
                    capacidades: function (Capacidad) {
                        return Capacidad.findAll({sortProperty: 'nombre'}).$promise.then(function (data) {
                            return data.content;
                        });
                    },
                    /* @ngInject */
                    horadias: function (EnumService) {
                        return EnumService.get('horadia');
                    },
                    /* @ngInject */
                    dias: function (EnumService) {
                        return EnumService.get('dia');
                    }
                 }

            })
            .state('profesor/info', {
                parent: 'profesor/list',
                url: '/:id',
                data: {
                    pageTitle: 'global.menu.profesor.info'
                },
                views: {
                    'content@': {
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
