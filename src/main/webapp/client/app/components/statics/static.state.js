(function (angular) {
    'use strict';

    angular.module('app').config(config);

    config.$inject = ['$stateProvider'];

    function config($stateProvider) {
        $stateProvider.state('static', {
            parent: 'site',
            url: '/estatica/:id',
            params: {
                language: ['Language',
                    function (Language) {
                        return Language.getCurrent();
                    }
                ]
            },
            data: {
                authorities: []
            },
            // FIXME: Se puede comprobar si existe la página? En caso de que no exista, apuntar a una que sí. Si no, la página se queda en blanco sin dar error.
            views: {
                'content@': {
                    templateUrl: function (params) {
                        return 'statics/' + params.id + '/' + params.id + '.' + params.language + '.html';
                    }
                }
            }
        });
    }
})(angular);
