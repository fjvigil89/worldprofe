/* globals $ */
(function () {
    'use strict';

    angular
        .module('app')
        .directive('radio', radio);

    function radio() {
        return {
            restrict: 'E',
            scope: {
                id: '@',
                name: '@',
                model: '=',
                change: '=',
                value: '=',
                disabled: '=',
                label: '@',
                text: '='
            },
            template: '' +
            '<label ng-click="disabled || (model=value)" for="{{id}}">' +
            '<input type="radio" id="{{id}}" name="{{name}}" ng-model="model" ng-value="value"' +
            ' ng-checked="model==value" ng-click="model=value" ng-change="change(model)" ng-disabled="disabled"/>' +
            '<span class="texto" ng-if="label">{{label | translate}}</span>' +
            '<span class="texto" ng-if="text" ng-bind-html="text"></span>' +
            '</label>',

            compile: function (tElt) {
                // tElt.addClass('sm-radio');
            }
        };
    }

})();
