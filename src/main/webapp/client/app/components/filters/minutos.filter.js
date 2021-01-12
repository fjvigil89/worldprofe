(function () {
    'use strict';

    // Filtro para convertir minutos a: Xh Ym

    // Podría ser interesante utilizar Moment para que sea más genérico... https://github.com/jsmreese/moment-duration-format

    angular
        .module('app')
        .filter('minutos', minutos);

    function minutos() {
        return calcular;

        function calcular(time) {
            time = Math.floor(time);
            if (time < 60) {
                return (time) + 'm';
            } else if (time % 60 == 0) {
                return (time - time % 60) / 60 + 'h';
            } else {
                return ((time - time % 60) / 60 + 'h' + ' ' + time % 60 + 'm');
            }
        }

    }
})();
