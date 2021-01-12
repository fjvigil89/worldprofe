(function () {
    'use strict';

    angular
        .module('app')
        .config(appConfig);

    /* @ngInject */
    function appConfig($translateProvider, DEBUG_INFO_ENABLED, cfpLoadingBarProvider, $compileProvider, $breadcrumbProvider, $qProvider) {
        console.log("Configurando aplicación");

        $translateProvider.useSanitizeValueStrategy('escapeParameters');

        // Configuración del idioma
        $translateProvider.useStaticFilesLoader({
            files: [{
                prefix: 'i18n/',
                suffix: '/admin.json'
            }, {
                prefix: 'i18n/',
                suffix: '/error.json'
            }, {
                prefix: 'i18n/',
                suffix: '/public.json'
            }, {
                prefix: 'i18n/',
                suffix: '/miespacio.json'
            }, {
                prefix: 'i18n/',
                suffix: '/componentes.json'
            }, {
                prefix: 'i18n/',
                suffix: '/enum.json'
            }]
        });
        // TODO: Almacenar en localstorage el último idioma seleccionado (app.module->changeLanguage) y leerlo de ahí
        $translateProvider.preferredLanguage('es');

        // Configuración de la barra de carga
        cfpLoadingBarProvider.latencyThreshold = 150;
        cfpLoadingBarProvider.includeSpinner = false;

        // Configuración Breadcrumb
        $breadcrumbProvider.setOptions({
            templateUrl: 'app/components/breadcrumb/breadcrumb.html'
        });

        // disable debug data on prod profile to improve performance. Por ahora siempre en modo debug
        console.log("Angular debug:" + DEBUG_INFO_ENABLED);
        $compileProvider.debugInfoEnabled(true);

        $compileProvider.preAssignBindingsEnabled(true);
        $qProvider.errorOnUnhandledRejections(false);
    }
})();
