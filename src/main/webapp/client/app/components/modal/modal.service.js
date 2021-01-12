(function (angular) {
    'use strict';

    // Gestión de ventanas modales.
    // Permite tener varias abiertas e ir cerrándolas por orden inverso de apertura.

    angular.module('app')
        .service('ModalService', ModalService);

    /* @ngInject */
    function ModalService($state, $uibModal, AlertService) {
        var opened = 0;
        var modals = [];

        return {
            isOpen: function () {
                return opened;
            },
            open: open,
            openComponent: openComponent,
            closeComponent: closeComponent,
            close: close,
            dismiss: dismiss
        };

        function open(params) {
            opened = opened + 1;
            var modal = $uibModal.open(params);
            modals.push(modal);

            // En caso de que se pulse fuera de la modal, hacemos un dismiss
            modal.result.catch(function (data) {
                if (data === 'backdrop click') {
                    dismiss(data);
                }
            });

            AlertService.clear();
            return modal;
        }

        /**
         * Abrir componente en una ventana modal.
         * @param title Título de la ventana modal.
         * @param configEntity Constante de donde se obtendrá el component y el resolve. (Ej: AGENTE_FORM_ADMIN)
         * @param params Parámetros adicionales (Ej: {size: 'lg'})
         */
        function openComponent(title, configEntity, params) {
            if (!params) params = {};

            var config = configEntity(true);
            params.resolve = config.resolve;
            params.resolve.component = function () {
                return config.component;
            };

            angular.merge(params, {
                backdrop: 'static',
                component: 'entityModal',
                resolve: {
                    title: function () {
                        return title;
                    }
                }
            });
            return open(params);
        }

        // Submit del componente. Se cierra la ventana modal o se cambia de estado.
        function closeComponent(result, state, params) {
            if (opened) {
                AlertService.clear();
                close(result);
            } else {
                $state.go(state, params);
            }
        }

        function close(data) {
            subOpened();
            modals[modals.length - 1].close(data);
            modals.pop();
        }

        function dismiss(data) {
            subOpened();
            modals[modals.length - 1].dismiss(data);
            modals.pop();
        }

        function subOpened() {
            opened = opened - 1;
            if (opened < 0) {
                opened = 0;
            }
        }
    }

})(angular);
