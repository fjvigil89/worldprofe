(function () {
    'use strict';

    angular
        .module('app')
        .directive('hasAnyAuthorityOrState', hasAnyAuthorityOrState);

    function hasAnyAuthorityOrState(Principal, $state) {
        var directive = {
            restrict: 'A',
            link: linkFunc,
            replace: false,
            scope: {
                authorities: "=",
                states: "="
            }
        };
        return directive;

        function linkFunc(scope, element) {

            var setVisible = function () {
                element.removeClass('hidden');
            };
            var setHidden = function () {
                element.addClass('hidden');
            };

            var defineVisibility = function (reset) {
                var result = true;

                if (reset) {
                    setVisible();
                }

                if (scope.authorities) {
                    var authorities = scope.authorities.replace(/\s+/g, '').split(',');
                    result = result && Principal.hasAnyAuthority(authorities);

                }
                if (scope.states) {
                    var states = scope.states.replace(/\s+/g, '').split(',');
                    var results = states.filter(function (state) {
                        return $state.includes(state);
                    });
                    result = result && results.length;
                }

                if (result) {
                    setVisible();
                } else {
                    setHidden();
                }
            };

            if (scope.authorities) {
                scope.$watch(function () {
                    return Principal.isAuthenticated();
                }, function () {
                    defineVisibility(true);
                });
            }

            if (scope.states) {
                scope.$on('$stateChangeSuccess',
                    function () {
                        defineVisibility(true);
                    }
                );
            }

            if (scope.authorities || scope.states) {
                defineVisibility(true);
            }
        }
    }
})();
