(function (angular) {
    'use strict';

    /**
     * Requires directives:
     *   * has-any-authority-or-state
     *   * uib-dropdown-toggle
     */
    angular
        .module('app')
        .component('miMenu', {
            bindings: {
                menu: '<',
                navCollapsed: '<',
                modal: '&',
                lateral: '<'
            },
            templateUrl: 'app/components/menu/menu.html',
            /* @ngInject */
            controller: function ($window, $state, StaticModal) {
                var vm = this;

                vm.checkActiveStates = function (activeStates) {
                    var active = false;
                    if (activeStates) {
                        var states = activeStates.replace(/\s+/g, '').split(',');
                        states.forEach(function (state) {
                            if ($state.includes(state)) {
                                active = true;
                            }
                        });
                    }
                    return active;
                };

                vm.modal = function (id) {
                    StaticModal.open(id);
                };

                vm.collapse = function () {
                    if ($window.innerWidth < 768) {
                        vm.navCollapsed = !vm.navCollapsed;
                    }
                }
            }
        });
})(angular);
