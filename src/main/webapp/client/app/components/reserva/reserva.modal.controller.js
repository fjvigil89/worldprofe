(function () {
    'use strict';

    // Todos los elementos del resolve son necesarios.

    angular.module('app')
        .controller('ReservaModalController', Controller);

    /* @ngInject */
    function Controller(item, parametros, ModalService, Reserva, usuarioModal, configuracion) {
        var vm = this;

        vm.dismiss = ModalService.dismiss;
        vm.item = item;
        vm.parametros = parametros;
        vm.usuarioModal = usuarioModal;
        vm.configuracion = configuracion;
        vm.ahora = new Date();

        vm.comienzoMenosHoras = angular.copy(vm.item.start.toDate());
        vm.comienzoMenosHoras.setHours(vm.comienzoMenosHoras.getHours() - vm.configuracion.tiempoAntesInicioCancelar);

        // Tipos de ventanas
        vm.tipos = {
            SIN_CONTESTAR: 1,
            CANCELAR_PROFESOR: 2,
            CANCELAR_ALUMNO: 3,
            RESPONDER_PROFESOR: 4,
            RESPONDER_ALUMNO: 5,
            RESPONDER_ALUMNO_PENDIENTE: 6
        };
        vm.tipo = null;

        if (vm.parametros.rolbd === 'ROLE_PROFESOR'
            && vm.parametros.estadobd === 'SIN_CONTESTAR' && vm.item.start.toDate() > vm.ahora) {
            vm.tipo = vm.tipos.SIN_CONTESTAR;
        } else if (vm.parametros.rolbd === 'ROLE_PROFESOR'
            && vm.parametros.estadobd === 'CONFIRMADA' && vm.comienzoMenosHoras > vm.ahora) {
            vm.tipo = vm.tipos.CANCELAR_PROFESOR;
        } else if (vm.parametros.rolbd === 'ROLE_ALUMNO'
            && (vm.parametros.estadobd === 'SIN_CONTESTAR' || vm.parametros.estadobd === 'CONFIRMADA') && vm.comienzoMenosHoras > vm.ahora) {
            vm.tipo = vm.tipos.CANCELAR_ALUMNO;
        } else if (vm.parametros.rolbd === 'ROLE_PROFESOR'
            && vm.parametros.estadobd === 'PENDIENTE') {
            vm.tipo = vm.tipos.RESPONDER_PROFESOR;
        } else if (vm.parametros.rolbd === 'ROLE_ALUMNO'
            && (vm.parametros.estadobd === 'REALIZADA' || vm.parametros.estadobd === 'INCIDENCIA')) {
            vm.tipo = vm.tipos.RESPONDER_ALUMNO;
        } else if (vm.parametros.rolbd === 'ROLE_ALUMNO'
            && vm.parametros.estadobd === 'PENDIENTE') {
            vm.tipo = vm.tipos.RESPONDER_ALUMNO_PENDIENTE;
        }

        vm.mostrarFooter = (vm.parametros.estadobd !== 'RECHAZADA' && vm.parametros.estadobd !== 'CANCELADA_POR_ALUMNO'
            && vm.parametros.estadobd !== 'CANCELADA_POR_PROFESOR' && vm.parametros.estadobd !== 'NO_CONTESTADA'
            && vm.tipo !== vm.tipos.RESPONDER_ALUMNO_PENDIENTE);

        vm.confirm = function () {
            if (vm.parametros.rolbd === 'ROLE_ALUMNO' && vm.parametros.estado == null) {
                Reserva.save({
                    alumnoId: vm.parametros.alumnoId,
                    profesorId: vm.usuarioModal.id,
                    claseLibreId: vm.parametros.id
                }).$promise.then(function () {
                    ModalService.close(vm.item);
                });
            } else {
                vm.parametros.fecha = vm.parametros.fechaPrimeraClase;
                Reserva.update(vm.parametros).$promise.then(function () {
                    ModalService.close(vm.item);
                });
            }
        };

    }
})();
