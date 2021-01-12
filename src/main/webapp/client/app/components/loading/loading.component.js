(function () {
    'use strict';

    // Campos:
    // isLoading: Campo para saber si está oculto o no.
    // class: Clase a mayores de la de por defecto.
    // img: Ruta al icono de carga. Por defecto loading.svg

    var loading = {
        template: '<img ng-show="$ctrl.isLoading" ' +
        'ng-src="{{$ctrl.img || \'assets/images/loading.svg\'}}" ' +
        'class="loading {{$ctrl.class}}"/>',
        bindings: {
            isLoading: '<',
            class: '@',
            img: '@'
        }
    };

    angular
        .module('app')
        .component('loading', loading);

})();
