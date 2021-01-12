(function (angular) {
    'use strict';

    angular.module('app').factory('Language', factory);

    /* @ngInject */
    function factory($translate) {
        return {
            getCurrent: getCurrent
        };

        function getCurrent() {
            if ($translate.use()) return $translate.use();
            return $translate.preferredLanguage();
        }
    }
})(angular);
