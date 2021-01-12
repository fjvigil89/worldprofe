(function () {
    'use strict';

    // Requiere: label-form
    // Módulos requeridos:
    // "ng-tags-input": "3.0.0"
    // "angular-bootstrap": "1.3.3"

    // id: ID del input.
    // multiple: Boolean. True para usar las etiquetas y false para usar un autcompletado simple.
    // as-list: Boolean. Añadir clase as-list a los elementos para mostrarlos uno debajo de otro.
    // restricted: Boolean. Solo permite añadir elementos que están en el autocompletado.
    // disabled: Boolean. Permite deshabilitar los inputs.
    // minLength: Número de caracteres requeridos para mostrar el autocompletado.
    // key: Clave para el track by
    // display: Propiedad que se va a mostrar (por defecto 'nombre')
    // method: Método del servicio (por defecto 'query')
    // parameters: Parámetros que se pueden añadir a la petición
    // required: Campo obligatorio.
    // resource: Tipo $resource del tesauro. El servicio tiene que implementar la operación query en el controlador.
    //      Ej: IdiomaService. (/idioma/query?query=x)
    // queryParam: Atributo en el que va a ir la query (por defecto 'query')
    // templateUrl: URL a un archivo HTML, o nombre del ID del HTML. En app/templates/tesauro.templates.html se pueden definir las que se utilicen más de una vez.
    // eliminable: Muestra el botón de eliminar en los tesauros simples.

    // Nota: El campo restricted debería ser siempre a TRUE, ya que no vamos a hacer eso de crear los tesauros de manera dinámica

    angular
        .module('app')
        .directive('tesauro', Tesauro);

    function Tesauro() {
        return {
            replace: true,
            restrict: 'E',
            controller: Controller,
            controllerAs: 'ctrl',
            bindToController: true,
            templateUrl: 'app/components/form/tesauro/tesauro.html',
            scope: {
                id: '@',
                label: '@',
                placeholder: '@',
                tooltip: '@',
                resource: '=',
                ngModel: '=',
                multiple: '=',
                asList: '=',
                key: '@',
                display: '@',
                method: '@',
                parameters: '=',
                restricted: '=',
                disabled: '=',
                minLength: '@',
                required: '=',
                queryParam: '@',
                templateUrl: '@',
                eliminable: '='
            }
        };
    }

    function Controller() {
        var vm = this;

        if (!vm.key) {
            vm.key = "nombre";
        }

        if (!vm.display) {
            vm.display = "nombre";
        }

        if (!vm.method) {
            vm.method = "query";
        }

        if (!vm.minLength) {
            vm.minLength = 1;
        }

        if (!vm.queryParam) {
            vm.queryParam = "query";
        }

        vm.clear = function () {
            vm.ngModel = null;
        };

        vm.resetElement = function () {
            vm.ngModel = null;
        };

        vm.setElement = function (item) {
            if (item.id && !vm.multiple) {
                vm.ngModel.id = item.id;
            }
        };

        vm.loadTags = function (query) {
            var params = !vm.parameters ? {} : vm.parameters;
            params[vm.queryParam] = query;
            // angular.merge(params, {query: query});
            return vm.resource[vm.method](params).$promise.then(function (data) {
                if (Array.isArray(data)) {
                    return data;
                } else {
                    return data.content;
                }
            });
        };

    }

})();
