(function () {
    'use strict';

    angular
        .module('app')
        .provider('AlertService', AlertService);

    function AlertService() {
        this.toast = false;
        /*jshint validthis: true */
        this.$get = getService;

        this.showAsToast = function (isToast) {
            this.toast = isToast;
        };

        getService.$inject = ['$timeout', '$sce', '$translate'];

        function getService($timeout, $sce, $translate) {
            var toast = this.toast,
                alertId = 0, // unique id for each alert. Starts from 0.
                alerts = [],
                timeout = 5000; // default timeout

            return {
                factory: factory,
                isToast: isToast,
                add: addAlert,
                closeAlert: closeAlert,
                closeAlertByIndex: closeAlertByIndex,
                clear: clear,
                clearPermanent: clearPermanent,
                get: get,
                success: success,
                error: error,
                info: info,
                warning: warning
            };

            function isToast() {
                return toast;
            }

            function getTimeout(time) {
                if (time == null) return timeout;
                return time;
            }

            function clear() {
                alerts.splice(0, alerts.length);
            }

            function clearPermanent() {
                // Ocultamos alertas permanentes y las de error también
                alerts.forEach(function (alert, index) {
                    if (alert.timeout == 0) {
                        alerts.splice(index, 1);
                    }
                    if (alert.type === 'danger') {
                        alerts.splice(index, 1);
                    }
                });
            }

            function get() {
                return alerts;
            }

            /**
             * Añadir un nuevo mensaje de éxito. La documentación es igual para el resto de tipos de alert.
             *
             * @param msg Clave del mensaje.
             * @param params Objeto json con las propiedades que se incluirán en el mensaje. Ej: {id: 16}.
             * En el mensaje tendrá que ir {{id}} donde se quiera hacer la sustitución.
             * @param time Tiempo que se mostrará el mensaje. Por defecto 5 segundos. Si se pone 0, el mensaje será permanente.
             * @param position Posición del mensaje. Sólo funciona cuando está en modo toast.
             */
            function success(msg, params, time, position) {
                return this.add({
                    type: 'success',
                    msg: msg,
                    params: params,
                    timeout: getTimeout(time),
                    toast: toast,
                    position: position
                });
            }

            function error(msg, params, time, position) {
                return this.add({
                    type: 'danger',
                    msg: msg,
                    params: params,
                    timeout: getTimeout(time),
                    toast: toast,
                    position: position
                });
            }

            function warning(msg, params, time, position) {
                return this.add({
                    type: 'warning',
                    msg: msg,
                    params: params,
                    timeout: getTimeout(time),
                    toast: toast,
                    position: position
                });
            }

            function info(msg, params, time, position) {
                return this.add({
                    type: 'info',
                    msg: msg,
                    params: params,
                    timeout: getTimeout(time),
                    toast: toast,
                    position: position
                });
            }

            function factory(alertOptions) {
                if (!alertOptions.params) {
                    alertOptions.params = {};
                }
                var alert = {
                    type: alertOptions.type,
                    msg: $sce.trustAsHtml(alertOptions.msg),
                    id: alertOptions.alertId,
                    timeout: alertOptions.timeout,
                    toast: alertOptions.params.toast ? alertOptions.params.toast : alertOptions.toast,
                    position: alertOptions.params.position ? alertOptions.params.position : 'top right',
                    scoped: alertOptions.scoped,
                    close: function (alerts) {
                        return closeAlert(this.id, alerts);
                    }
                };
                if (!alert.scoped) {
                    alerts.push(alert);
                }
                return alert;
            }

            function addAlert(alertOptions, extAlerts) {
                alertOptions.alertId = alertId++;
                alertOptions.msg = $translate.instant(alertOptions.msg, alertOptions.params);
                var that = this;
                var alert = this.factory(alertOptions);
                if (alertOptions.timeout && alertOptions.timeout > 0) {
                    $timeout(function () {
                        that.closeAlert(alertOptions.alertId, extAlerts);
                    }, alertOptions.timeout);
                }
                return alert;
            }

            function closeAlert(id, extAlerts) {
                var thisAlerts = extAlerts ? extAlerts : alerts;
                return closeAlertByIndex(thisAlerts.map(function (e) {
                    return e.id;
                }).indexOf(id), thisAlerts);
            }

            function closeAlertByIndex(index, thisAlerts) {
                return thisAlerts.splice(index, 1);
            }
        }
    }
})();
