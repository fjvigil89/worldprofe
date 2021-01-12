(function () {
    'use strict';

    angular
        .module('app')
        .factory('notificationInterceptor', notificationInterceptor);

    notificationInterceptor.$inject = ['AlertService'];

    function notificationInterceptor(AlertService) {
        var service = {
            response: response
        };

        return service;

        function response(response) {
            var alertKey = response.headers('X-app-alert');
            if (angular.isString(alertKey)) {
                AlertService.success(alertKey, {param: response.headers('X-app-params')});
            }
            return response;
        }
    }
})();
