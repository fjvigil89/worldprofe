(function () {
    'use strict';

    angular
        .module('app')

        /* @ngInject */
        .constant('PROFESOR_FORM_ADMIN', function (modal) {
            var resolve = "$resolve.";
            if (modal) {
                resolve = "ctrl.resolve."
            }
            return {
                component: '<profesor-form-admin' +
                ' item="' + resolve + 'item"' +
                ' previous-params="' + resolve + 'previousParams"' +
                '"></profesor-form-admin>',
                resolve: {
                    /* @ngInject */
                    item: function (User) {
                        return new User();
                    }
                }
            }
        });
})();
