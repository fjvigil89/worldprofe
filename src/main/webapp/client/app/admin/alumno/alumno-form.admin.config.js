(function () {
    'use strict';

    angular
        .module('app')

        /* @ngInject */
        .constant('ALUMNO_FORM_ADMIN', function (modal) {
            var resolve = "$resolve.";
            if (modal) {
                resolve = "ctrl.resolve."
            }
            return {
                component: '<alumno-form-admin' +
                ' item="' + resolve + 'item"' +
                ' previous-params="' + resolve + 'previousParams"' +
                ' empresa="' + resolve + 'empresa"' +
                '></alumno-form-admin>',
                resolve: {
                    /* @ngInject */
                    item: function (User) {
                        return new User();
                    }
                }
            }
        });
})();
