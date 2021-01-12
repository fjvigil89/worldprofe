(function () {
    'use strict';

    angular
        .module('app')

        /* @ngInject */
        .constant('EMPRESA_FORM_ADMIN', function (modal) {
            var resolve = "$resolve.";
            if (modal) {
                resolve = "ctrl.resolve."
            }
            return {
                component: '<empresa-form-admin' +
                ' item="' + resolve + 'item"' +
                ' previous-params="' + resolve + 'previousParams"' +
                '"></empresa-form-admin>',
                resolve: {
                    /* @ngInject */
                    item: function (User) {
                        return new User();
                    }
                }
            }
        });
})();
