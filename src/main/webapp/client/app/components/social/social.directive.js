(function () {
    'use strict';

    angular
        .module('app')
        .directive('social', social);

    /* @ngInject */
    function social($filter, SocialService) {
        var directive = {
            restrict: 'E',
            scope: {
                provider: '@',
                mini: '@'
            },
            templateUrl: 'app/components/social/social.html',
            link: linkFunc
        };

        return directive;

        function linkFunc(scope) {
            scope.label = $filter('capitalize')(scope.provider);
            scope.providerSetting = SocialService.getProviderSetting(scope.provider);
            scope.providerURL = SocialService.getProviderURL(scope.provider);
            scope.csrf = SocialService.getCSRF();
        }

    }
})();
