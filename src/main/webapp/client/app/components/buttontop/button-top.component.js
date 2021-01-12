(function () {
    'use strict';

    var buttonTop = {
        template: '<button id="button-top" ng-click="ctrl.topFunction()"><span class="glyphicon glyphicon-arrow-up"></span></button>',
        bindings: {
            mote: '@'
        },
        controller: Controller,
        controllerAs: 'ctrl'
    };

    /* @ngInject */
    function Controller($window) {
        var vm = this;

        // Botón ir arriba
        $window.onscroll = function () {
            scrollFunction();
        };

        function scrollFunction() {
            if (document.getElementById("button-top") != undefined) {
                if (document.body.scrollTop > 50 || document.documentElement.scrollTop > 50) {
                    document.getElementById("button-top").style.display = "block";
                } else {
                    document.getElementById("button-top").style.display = "none";
                }
            }
        }

        vm.topFunction = function () {
            $("html, body").animate({scrollTop: "0px"}, 1000);
        };
    }

    angular
        .module('app')
        .component('buttonTop', buttonTop);

})();
