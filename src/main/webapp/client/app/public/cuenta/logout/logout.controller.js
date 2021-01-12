(function () {
    'use strict';

    angular.module('app')
        .controller('LogoutController', LogoutController);

    /* @ngInject */
    function LogoutController($state, Auth) {
        Auth.logout();
        // home
        $state.go('registro/info');
    }
})();