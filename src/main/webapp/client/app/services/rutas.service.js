(function () {
    'use strict';

    angular.module('app')
        .factory('RutasService', RutasService);

    /* @ngInject */
    function RutasService($location, DEBUG_INFO_ENABLED) {
        var cacheUrl = "";

        return {
            getBaseUrl: getBaseUrl,
            getAgenteCvUrl: getAgenteCvUrl,
            getAgenteImagenUrl: getAgenteImagenUrl
        };

        function getBaseUrl() {
            // En desarrollo utilizamos la de pre porque si no hay problemas al compartir con Facebook
            if (!cacheUrl) {
                if (DEBUG_INFO_ENABLED) {
                    cacheUrl = "http://localhost:8080";
                } else {
                    cacheUrl = $location.protocol() + "://" + $location.host();
                    if ($location.port() !== 80) {
                        cacheUrl = cacheUrl + ":" + $location.port();
                    }
                }
            }
            return cacheUrl;
        }

        function getAgenteCvUrl(id) {
            return '/upload/agentes/' + id + '/cv/';
        }

        function getAgenteImagenUrl(id) {
            return '/upload/agentes/' + id + '/imagen/';
        }

    }

})();
