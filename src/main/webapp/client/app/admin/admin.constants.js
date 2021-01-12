(function () {
    'use strict';

    angular.module('app')
        .constant('ADMIN_PRINCIPAL', "admin/reserva/list")
        // Nº de elementos por página en las tablas
        .constant('NG_TABLE_DEFAULT_PARAMS', {size: 20});
})();
