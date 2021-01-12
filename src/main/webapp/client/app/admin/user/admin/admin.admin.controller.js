(function (angular) {
    'use strict';

    angular
        .module('app')
        .controller('AdminAdminController', AdminAdminController);

    /* @ngInject */
    function AdminAdminController($timeout, $stateParams, NG_TABLE_DEFAULT_PARAMS, NgTableParams, NgTableHelper,
                                  ModalService, User) {
        var vm = this;

        $timeout(function () {
            angular.element('.form-group:eq(0)>input').focus();
        });

        vm.item = User;
        vm.pagina = $stateParams.page ? $stateParams.page : 1;
        vm.elementosPorPagina = NG_TABLE_DEFAULT_PARAMS.size;
        vm.filter = {
            key: undefined,
            rol: 'ROLE_ADMIN'
        };
        vm.tableParams = new NgTableParams({
            count: vm.elementosPorPagina,
            page: vm.pagina,
            filter: vm.filter,
            sorting: {login: 'asc'}
        }, NgTableHelper.settings(vm));

        // Eliminar
        vm.showRemoveConfirmation = function (user) {
            ModalService.open({
                templateUrl: 'app/components/form/confirm/confirm.modal.html',
                controller: 'ConfirmModalController',
                controllerAs: 'ctrl',
                resolve: {
                    /* @ngInject */
                    item: function (User) {
                        return User.get({email: user.email}).$promise;
                    },
                    params: function () {
                        return {
                            title: 'admin.list-users.delete.title',
                            body: 'admin.list-users.delete.confirmadmin',
                            property: 'email'
                        };
                    },
                    /* @ngInject */
                    tipo: function (User) {
                        return User;
                    },
                    funcion: function () {
                        return "eliminar";
                    },
                    parametros: function () {
                        return {id: user.id};
                    }
                }
            }).result.then(function () {
                vm.tableParams.reload();
            });
        };

    }

})(angular);
