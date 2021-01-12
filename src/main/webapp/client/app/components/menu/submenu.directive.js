(function (angular) {
    'use strict';

    angular
        .module('app')
        .directive('submenu', submenuDirective);

    submenuDirective.$inject = [];

    function submenuDirective() {
        return {
            restrict: 'E',
            replace: true,
            scope: {
                menu: '='
            },
            templateUrl: 'app/components/menu/submenu.html'
        };
    }
})(angular);
