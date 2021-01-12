(function () {
    'use strict';

    angular
        .module('app')

        /* @ngInject */
        .constant('CAPACIDAD_FORM_ADMIN', function (modal) {
            var resolve = "$resolve.";
            if (modal) {
                resolve = "ctrl.resolve."
            }
            return {
                component: '<capacidad-form-admin ' +
                'item="' + resolve + 'item" ' +
                'previous-params="' + resolve + 'previousParams"></capacidad-form-admin>',
                resolve: {
                    /* @ngInject */
                    item: function (Capacidad) {
                        return new Capacidad();
                    }
                }
            }
        });
})();
