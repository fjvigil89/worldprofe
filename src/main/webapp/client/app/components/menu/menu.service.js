(function (angular) {
    'use strict';

    angular
        .module('app')
        .factory('Menu', Menu);

    Menu.$inject = ['$q', '$http'];

    function Menu($q, $http) {
        return {
            getMenu: getMenu
        };

        function getMenu(position) {
            var deferred = $q.defer();
            $http.get('menu/' + position + '.json').then(function (response) {
                deferred.resolve(response.data);
            });

            return deferred.promise;
        }
    }
})(angular);
