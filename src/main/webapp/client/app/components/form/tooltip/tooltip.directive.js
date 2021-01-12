/* globals $ */
(function () {
    'use strict';

    // Módulos requeridos:
    //
    // label: Etiqueta que se mostrará en el tooltip
    // position: Posición del tooltip. Por defecto: right

    angular
        .module('app')
        .directive('tooltip', Tooltip);

    function Tooltip() {
        var directive = {
            replace: true,
            restrict: 'E',
            templateUrl: 'app/components/form/tooltip/tooltip.html',
            scope: {
                label: '@',
                position: '@'
            }
        };
        return directive;
    }

})();
