(function () {
    'use strict';

    /**
     * @ngdoc controller
     * @name app.controller:ConfirmModalController
     * @description
     * Controller que, asociado al archivo confirm.modal.html, permite abrir una ventana modal de confirmación que lanzará una acción.
     *
     * Es obligatorio indicar en el Resolve cada uno de los campos listados a continuación.
     *
     * @param {Object} item  Elemento del que se muestra información
     * @param {Object} params JSON de la forma {title: '', body: '', property: ''}
     * @param {Object} tipo Resource que tiene la función que se llamará
     * @param {String} funcion Nombre de la función
     * @param {Object} parametros Parameteros que se enviará la a función de confirmación
     * @param {String}
     */
    angular.module('app')
        .controller('ConfirmModalController', Controller);

    /* @ngInject */
    function Controller(item, params, tipo, funcion, parametros, ModalService) {
        var vm = this;

        vm.dismiss = ModalService.dismiss;
        vm.item = item;
        vm.params = params;

        vm.confirm = function () {
            tipo[funcion](parametros).$promise.then(function () {
                ModalService.close(vm.item);
            });
        };
    }
})();
