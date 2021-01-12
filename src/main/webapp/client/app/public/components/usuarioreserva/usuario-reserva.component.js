(function () {
    'use strict';

    angular
        .module('app')
        .component('usuarioReserva', {
            templateUrl: 'app/public/components/usuarioreserva/usuario-reserva.html',
            controller: UsuarioReservaController,
            controllerAs: 'ctrl',
            bindings: {
                usuario: '<',
                reserva: '<'
            }
        });

    /* @ngInject */
    function UsuarioReservaController() {
        var vm = this;

        vm.cancelada =
            vm.reserva.estado === 'RECHAZADA'
            || vm.reserva.estado === 'CANCELADA_POR_ALUMNO'
            || vm.reserva.estado === 'CANCELADA_POR_PROFESOR';

        vm.evaluada =
            vm.reserva.estado === 'PENDIENTE'
            || vm.reserva.estado === 'INCIDENCIA'
            || vm.reserva.estado === 'REALIZADA';

    }

})();
