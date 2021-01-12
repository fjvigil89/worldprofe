(function (angular) {
    'use strict';

    angular
        .module('app')
        .config(httpConfig);

    httpConfig.$inject = ['$urlRouterProvider', '$httpProvider', '$locationProvider', 'DEBUG_INFO_ENABLED', 'httpRequestInterceptorCacheBusterProvider'];

    function httpConfig($urlRouterProvider, $httpProvider, $locationProvider, DEBUG_INFO_ENABLED, httpRequestInterceptorCacheBusterProvider) {
        //Cache everything except rest api requests
        httpRequestInterceptorCacheBusterProvider.setMatchlist([/.*api.*/, /.*protected.*/], true);

        $urlRouterProvider.otherwise('/entrar');

        $httpProvider.interceptors.push('errorHandlerInterceptor');
        $httpProvider.interceptors.push('authExpiredInterceptor');
        $httpProvider.interceptors.push('authInterceptor');
        $httpProvider.interceptors.push('notificationInterceptor');

        // Elimina el ! de las URLs
        $locationProvider.hashPrefix('');

        // Elimina el # de las URLs de producción.
        if (!DEBUG_INFO_ENABLED) {
            $locationProvider.html5Mode(true);
        }
    }

})(angular);
