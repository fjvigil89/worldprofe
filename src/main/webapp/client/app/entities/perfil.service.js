(function () {
    'use strict';

    angular.module('app')
        .factory('Perfil', service);

    /* @ngInject */
    function service($resource) {
        var url = 'api/public/perfil';
        return $resource(url, {}, {
            'changePassword': {
                method: 'POST',
                url: url + '/change_password'
            },
            'baja': {
                method: 'GET',
                url: url + '/baja'
            }
        });
    }
})();
