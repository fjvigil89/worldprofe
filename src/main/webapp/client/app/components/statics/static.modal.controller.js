(function (angular) {
    'use strict';

    angular
        .module('app')
        .controller('StaticModalController', StaticModalController);

    /* @ngInject */
    function StaticModalController(ModalService, title) {
        var vm = this;

        vm.title = title;

        vm.close = function () {
            ModalService.dismiss();
        };

    }
})
(angular);
