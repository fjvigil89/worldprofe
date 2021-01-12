(function () {
    'use strict';

    /**
     * @ngdoc object
     * @name app.object:ieFix
     *
     * @description
     * Definición de las operaciones que no soporta Internet Explorer y que el resto de navegadores sí:
     *
     * - `String.prototype.startsWith`
     * - `String.prototype.endsWith`
     * - `String.prototype.inclues`
     * - `Array.prototype.startsWith`
     *
     */
    angular
        .module('app')
        .config(ieFix);

    /* @ngInject */
    function ieFix() {

        // *** Strings ***

        // https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/startsWith
        if (!String.prototype.startsWith) {
            String.prototype.startsWith = function (search, pos) {
                return this.substr(!pos || pos < 0 ? 0 : +pos, search.length) === search;
            };
        }

        // https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/endsWith
        if (!String.prototype.endsWith) {
            String.prototype.endsWith = function (search, this_len) {
                if (this_len === undefined || this_len > this.length) {
                    this_len = this.length;
                }
                return this.substring(this_len - search.length, this_len) === search;
            };
        }

        // https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/includes
        if (!String.prototype.includes) {
            String.prototype.includes = function (search, start) {
                'use strict';
                if (typeof start !== 'number') {
                    start = 0;
                }

                if (start + search.length > this.length) {
                    return false;
                } else {
                    return this.indexOf(search, start) !== -1;
                }
            };
        }

        // *** Arrays ***

        // https://developer.mozilla.org/es/docs/Web/JavaScript/Referencia/Objetos_globales/Array/includes
        if (!Array.prototype.includes) {
            Array.prototype.includes = function (searchElement /*, fromIndex*/) {
                'use strict';
                var O = Object(this);
                var len = parseInt(O.length) || 0;
                if (len === 0) {
                    return false;
                }
                var n = parseInt(arguments[1]) || 0;
                var k;
                if (n >= 0) {
                    k = n;
                } else {
                    k = len + n;
                    if (k < 0) {
                        k = 0;
                    }
                }
                var currentElement;
                while (k < len) {
                    currentElement = O[k];
                    if (searchElement === currentElement ||
                        (searchElement !== searchElement && currentElement !== currentElement)) {
                        return true;
                    }
                    k++;
                }
                return false;
            };
        }

    }
})();
