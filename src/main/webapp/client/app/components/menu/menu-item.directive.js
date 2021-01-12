/**
 * Based on:
 *
 * The MIT License (MIT)
 * Copyright (c) 2015 Eugenio Lentini
 * https://github.com/blackat/ui-navbar
 */
(function (angular) {
    'use strict';

    angular
        .module('app')
        .directive('menuItem', menuItemDirective);

    menuItemDirective.$inject = ['$compile'];

    function menuItemDirective($compile) {
        return {
            restrict: 'E',
            replace: true,
            templateUrl: 'app/components/menu/menu-item.html',
            link: function (scope, element, attrs) {
                if (angular.isArray(scope.leaf.subtree)) {
                    element.append('<submenu menu="leaf.subtree"></submenu>');

                    // find the parent of the element
                    var parent = element.parent();
                    var classFound = false;

                    // check if in the hierarchy of the element exists a dropdown with class navbar-right
                    while (parent.length > 0 && !classFound) {
                        // check if the dropdown has been push to right
                        if (parent.hasClass('navbar-right')) {
                            classFound = true;
                        }
                        parent = parent.parent();
                    }

                    // add a different class according to the position of the dropdown
                    if (classFound) {
                        element.addClass('dropdown-submenu-right');
                    } else {
                        element.addClass('dropdown-submenu');
                    }

                    $compile(element.contents())(scope);
                }
            },
            scope: {
                leaf: '='
            }
        };
    }
})(angular);
