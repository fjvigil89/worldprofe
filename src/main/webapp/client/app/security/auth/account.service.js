(function () {
    'use strict';

    angular.module('app')
        .factory('Account', Account);

    /* @ngInject */
    function Account($resource) {
        return $resource('api/account/account', {}, {
            'get': {
                method: 'GET',
                params: {login: '@login'},
                isArray: false,
                interceptor: {
                    response: function (response) {
                        // expose response
                        return response;
                    }
                }
            }
        });
    }
})();
