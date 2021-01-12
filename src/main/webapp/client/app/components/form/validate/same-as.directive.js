(function () {
    'use strict';

    angular.module('app')
        .directive('sameAs', directive);

    function directive() {
        return {
            require: '?ngModel',
            restrict: 'A',
            link: function (scope, elem, attrs, ngModel) {
                ngModel.$parsers.unshift(validate);

                scope.$watch(attrs.sameAs, function () {
                    ngModel.$setViewValue(ngModel.$viewValue);
                });

                function validate(value) {
                    var isValid = scope.$eval(attrs.sameAs) === value;
                    ngModel.$setValidity('sameAs', isValid);
                    return isValid ? value : undefined;
                }
            }
        };
    }
})();
