(function () {
    'use strict';

    angular.module('app')
        .factory('User', User);

    /* @ngInject */
    function User($resource, ParseLinks) {
        var urlAdmin = 'api/admin/user';
        var urlPublic = 'api/public/user';
        return $resource(urlAdmin, {email: '@account.email'}, {
            'query': {
                method: 'GET',
                isArray: false,
                transformResponse: function (data, headers) {
                    return {
                        links: ParseLinks.parse(headers('link')),
                        list: angular.fromJson(data)
                    };
                }
            },
            'get': {
                method: 'GET',
                params: {
                    email: '@email'
                }
            },
            'getById': {
                method: 'GET',
                url: urlAdmin + '/id',
                params: {
                    id: '@id'
                }
            },
            'getEmpresaDTO': {
                method: 'GET',
                url: urlAdmin + '/getEmpresaDTO/id',
                params: {
                    id: '@id'
                }
            },
            'getProfesorById': {
                method: 'GET',
                url: urlPublic + '/profesor/id',
                params: {
                    id: '@id'
                }
            },
            'findAll': {
                method: 'GET',
                url: urlAdmin + '/findAll'
            },
            'findProfesoresAdmin': {
                method: 'POST',
                url: urlAdmin + '/findProfesoresAdmin'
            },
            'findAllProfesor': {
                method: 'POST',
                url: urlPublic + '/findAllProfesor'
            },
            'findAlumnosByCentro': {
                method: 'GET',
                url: urlPublic + '/findAlumnosByCentro'
            },
            'getUsuarioPorReserva': {
                method: 'GET',
                url: urlPublic + '/getUsuarioPorReserva'
            },
            'getProfesorPorClaseLibre': {
                method: 'GET',
                url: urlPublic + '/getProfesorPorClaseLibre'
            },
            'email': {
                method: 'GET',
                url: urlAdmin + '/email',
                params: {
                    email: '@email'
                }
            },
            'activar': {
                method: 'POST',
                url: urlAdmin + '/activar',
                params: {
                    email: '@email',
                    activar: '@activar'
                }
            },
            'restaurar': {
                method: 'POST',
                url: urlAdmin + '/restaurar',
                params: {
                    id: '@id'
                }
            },
            'eliminar': {
                method: 'GET',
                url: urlAdmin + '/eliminar',
                params: {
                    id: '@id'
                }
            },
            'baja': {
                method: 'GET',
                url: urlAdmin + '/baja',
                params: {
                    email: '@email'
                }
            },
            'zonasHorarias': {
                method: 'GET',
                url: urlPublic + '/zonasHorarias',
                isArray: true
            }
        });
    }
})();
