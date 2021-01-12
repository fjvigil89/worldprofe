(function (angular) {
    'use strict';

    // String to Int
    angular.module('app').filter('num', function () {
        return function (input) {
            return parseInt(input, 10);
        }
    });

})(angular);
