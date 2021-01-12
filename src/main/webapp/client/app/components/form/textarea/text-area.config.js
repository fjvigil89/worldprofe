(function () {
    'use strict';

    // Botones propios del editor de texto textAngular

    angular
        .module('app')
        .config(textAngularConfig);

    /* @ngInject */
    function textAngularConfig($provide) {

        $provide.decorator('taOptions', ['taRegisterTool', '$delegate', function (taRegisterTool, taOptions) {

            // Insertar un texto que se sustituirá por el código
            taRegisterTool('codigo', {
                iconclass: "fa fa-pencil-square-o",
                buttontext: "{{'textangular.codigo' | translate}}",
                action: function () {
                    this.$editor().wrapSelection("insertHTML", "{CODIGO}");
                }
            });

            return taOptions;
        }]);

    }
})();
