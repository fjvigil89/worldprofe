(function () {
    'use strict';

    angular
        .module('app')
        .directive('hasNoAuthority', hasNoAuthority);

    function hasNoAuthority(Principal) {
        var directive = {
            restrict: 'A',
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, element, attrs) {
            var authority = attrs.hasNoAuthority.replace(/\s+/g, '');

            var setVisible = function () {
                    element.removeClass('hidden');
                },
                setHidden = function () {
                    element.addClass('hidden');
                },
                defineVisibility = function (reset) {

                    if (reset) {
                        setVisible();
                    }

                    Principal.hasAuthority(authority)
                        .then(function (result) {
                            console.log("Result: " + result);
                            if (result) {
                                setHidden();
                            } else {
                                setVisible();
                            }
                        });
                };

            if (authority.length > 0) {
                defineVisibility(true);

                scope.$watch(function () {
                    return Principal.isAuthenticated();
                }, function () {
                    defineVisibility(true);
                });
            }
        }
    }
})();
