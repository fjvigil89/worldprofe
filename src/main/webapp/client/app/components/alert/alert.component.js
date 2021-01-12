(function () {
    'use strict';

    var alert = {
        template: '<div class="alerts" ng-cloak="" ng-show="!$ctrl.modalService() || ($ctrl.modal && $ctrl.modalService())">' +
        '<div ng-repeat="alert in $ctrl.alerts track by alert.id" ng-class="[alert.position, {\'toast\': alert.toast}]">' +
        '<div uib-alert ng-cloak="" type="{{alert.type}}" ng-class="\'alert-\' + alert.type" close="alert.close($ctrl.alerts)">' +
        '<span ng-bind-html="alert.msg"></span></div>' +
        '</div>' +
        '</div>',
        controller: alertController,
        bindings: {
            modal: '<'
        }
    };

    angular
        .module('app')
        .component('alert', alert);

    /* @ngInject */
    function alertController($scope, AlertService, ModalService, $rootScope, $translate) {
        var vm = this;

        vm.alerts = AlertService.get();
        vm.modalService = ModalService.isOpen;

        function addErrorAlert(message, key, data) {
            key = key && key !== null ? key : message;
            vm.alerts.push(
                AlertService.add(
                    {
                        type: 'danger',
                        msg: key,
                        params: data,
                        timeout: 5000,
                        toast: AlertService.isToast(),
                        scoped: true
                    },
                    vm.alerts
                )
            );
            // Si se produce un error se sube al principio de la página para ver el alert
            angular.element("html, body").animate({scrollTop: 0}, "slow");
        }

        var cleanHttpErrorListener = $rootScope.$on('app.httpError', function (event, httpResponse) {

            if (!!vm.modalService() !== vm.modal) {
                return;
            }
            var i;
            event.stopPropagation();

            switch (httpResponse.status) {
                // connection refused, server not reachable
                case 0:
                    addErrorAlert('Server not reachable', 'error.servernotreachable');
                    break;

                case 400:
                    var errorHeader = httpResponse.headers('X-app-error');
                    var entityKey = httpResponse.headers('X-app-params');

                    if (errorHeader) {
                        var entityName = $translate.instant('global.menu.entities.' + entityKey);
                        addErrorAlert(errorHeader, errorHeader, {entityName: entityName});

                    } else if (httpResponse.data && httpResponse.data.fieldErrors) {
                        for (i = 0; i < httpResponse.data.fieldErrors.length; i++) {
                            var fieldError = httpResponse.data.fieldErrors[i];
                            // convert 'something[14].other[4].id' to 'something[].other[].id' so translations can be written to it
                            var convertedField = fieldError.field.replace(/\[\d*\]/g, '[]');
                            var fieldName = $translate.instant('jhipsterJwtApp.' + fieldError.objectName + '.' + convertedField);
                            addErrorAlert('Field ' + fieldName + ' cannot be empty', 'error.' + fieldError.message, {fieldName: fieldName});
                        }

                    } else if (httpResponse.data && httpResponse.data.message) {
                        if (httpResponse.data.errors) {
                            // Estos errores nunca deberían llegar a mostrarse a no ser que se salten las restricciones del cliente
                            if (httpResponse.data.errors[0].code === 'NotNull') {
                                addErrorAlert("error.notnulls", null, null);
                            } else if (httpResponse.data.errors[0].code === 'Size') {
                                addErrorAlert("error.size", null, null);
                            }
                        } else {
                            addErrorAlert(httpResponse.data.message, httpResponse.data.message, httpResponse.data);
                        }
                    }
                    break;
                case 401:
                    break;

                case 403:
                    addErrorAlert('Access is denied', 'error.accessdenied.title');
                    break;

                case 404:
                    addErrorAlert('Not found', 'error.notfound');
                    break;

                default:
                    if (httpResponse.data && httpResponse.data.message) {
                        if (httpResponse.data.error === 'Internal Server Error') {
                            addErrorAlert('Internal server error', 'error.internalservererror');
                        } else {
                            addErrorAlert(httpResponse.data.message);
                        }
                    }
            }
        });

        $scope.$on('$destroy', function () {
            if (angular.isDefined(cleanHttpErrorListener) && cleanHttpErrorListener !== null) {
                cleanHttpErrorListener();
                vm.alerts = [];
            }
            vm.alerts = [];
        });
    }
})();
