(function () {
    'use strict';

    angular.module('app')
        .controller('EntityDeleteModalController', Controller);

    /* @ngInject */
    function Controller(item, params, ModalService) {
        var vm = this;

        vm.dismiss = ModalService.dismiss;
        vm.item = item;
        vm.params = params;

        vm.confirmRemove = function () {
            vm.item.$remove({id: vm.item.id}).then(function () {
                ModalService.close(vm.item);
            });
        };
    }
})();
