(function () {
    'use strict';

    var filter = {
        templateUrl: 'app/components/form/filter/filter.input.template.html',
        bindings: {
            filter: '='
        }
    };

    angular
        .module('app')
        .component('filter', filter);

})();
