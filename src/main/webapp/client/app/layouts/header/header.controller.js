(function (angular) {
    'use strict';

    angular
        .module('app')
        .controller('HeaderController', Controller);

    /* @ngInject */
    function Controller($scope, Principal) {
        var vm = this;

        // Información de usuario

        function updateUser() {
            Principal.identity().then(function (data) {
                vm.usuario = data;
            });
        }

        updateUser();

        $scope.$on('changedIdentity', function () {
            updateUser();
        });

    }

})(angular);
