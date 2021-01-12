(function (angular) {
    'use strict';

    angular.module('app', [
        'ui.bootstrap',
        'ui.router',
        'angular-loading-bar',
        'angularjs-dropdown-multiselect',
        'infinite-scroll',
        'jcs-autoValidate',
        'ncy-angular-breadcrumb',
        'ngAnimate',
        'ngCacheBuster',
        'ngCookies',
        'ngFileUpload',
        'ngResource',
        'ngStorage',
        'ngTable',
        'ngTagsInput',
        'pascalprecht.translate',
        'textAngular',
        'ui.bootstrap',
        'ui.bootstrap.datetimepicker',
        'ui.calendar',
        "uiCropper",
        '720kb.socialshare'
    ]).run(appRun);

    /* @ngInject */
    function appRun($rootScope, $translate, $localStorage, stateHandler, bootstrap3ElementModifier, validator, defaultErrorMessageResolver) {

        stateHandler.initialize();

        $rootScope.changeLanguage = function (key) {
            if ($translate.use() !== key) {
                $translate.use(key).then(function () {
                    $translate.refresh();
                });
                $localStorage.lang = key;
            }
        };

        // Mostrar iconos de validación
        bootstrap3ElementModifier.enableValidationStateIcons(true);
        // No resaltar elementos correctos
        validator.setValidElementStyling(false);
        // Validación sólo al hacer submit
        validator.defaultFormValidationOptions.validateOnFormSubmit = true;

        // Ubicación del fichero de mensajes de error
        defaultErrorMessageResolver.setI18nFileRootPath('i18n');
        defaultErrorMessageResolver.setCulture('es');
    }
})
(angular);
