(function (angular) {
    'use strict';

    angular.module('app')
        .service('StaticModal', StaticModal);

    // Mejora: Se podría refactorizar haciendo que la plantilla fuese el header y footer del modal y el contenido un include de la página en concreto.

    /* @ngInject */
    function StaticModal(ModalService, Language) {
        return {
            open: open
        };

        /**
         * @param id nombre del archivo
         */
        function open(id) {
            ModalService.open({
                    templateUrl: function () {
                        return 'statics/' + id + '/' + id + '.' + Language.getCurrent() + '.html';
                    },
                    controller: 'StaticModalController',
                    controllerAs: 'ctrl',
                    resolve: {
                        title: function () {
                            return 'static.' + id + ".title"
                        }
                    }
                }
            );
        }
    }
})(angular);
