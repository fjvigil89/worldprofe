(function (angular) {
    'use strict';

    // id: Identificador de la página estática. (Nombre del archivo o identificador de BD).
    // carpeta: Ruta a la página en caso de que haya alguna carpeta intermedia.
    // bd: Boolean. Si se obtiene de BD, se carga el archivo static.html y se muestra el contenido.

    // FIXME: Recargar página estática cuando se cambia de idioma.

    angular
        .module('app')
        .component('static', {
            bindings: {
                id: '@', // required
                carpeta: '@',
                bd: '<'
            },
            /* @ngInject */
            templateUrl: function ($element, $attrs, Language) {
                if ($attrs.bd === "true") {
                    return 'app/components/statics/static.html';
                } else {
                    var carpeta = "";
                    if ($attrs.carpeta) {
                        carpeta = $attrs.carpeta + "/";
                    }
                    return 'statics/' + carpeta + $attrs.id + '/' + $attrs.id + '.' + Language.getCurrent() + '.html';
                }
            },
            /* @ngInject */
            controller: function (Estatica, Language, $attrs) {
                var vm = this;
                Estatica.getByNombreIdioma({
                    'nombre': $attrs.id,
                    'idioma': Language.getCurrent()
                }).$promise.then(function (data) {
                    vm.estaticai18n = data;
                });
            },
            controllerAs: 'ctrl'
        });
})(angular);
