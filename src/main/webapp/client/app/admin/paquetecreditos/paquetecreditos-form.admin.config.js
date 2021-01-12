(function () {
    'use strict';

    angular
        .module('app')

        /* @ngInject */
        .constant('PAQUETECREDITOS_FORM_ADMIN', function (modal) {
            var resolve = "$resolve.";
            if (modal) {
                resolve = "ctrl.resolve."
            }
            return {
                component: '<paquetecreditos-form-admin' +
                ' item="' + resolve + 'item"' +
                ' previous-params="' + resolve + 'previousParams"' +
                '"></paquetecreditos-form-admin>',
                resolve: {
                    /* @ngInject */
                    item: function (Paquetecreditos) {
                        return new Paquetecreditos();
                    }
                }
            }
        });
})();
