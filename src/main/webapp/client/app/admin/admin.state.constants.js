(function () {
    'use strict';

    // Operaciones CRUD para la gestión de elementos en la administración.

    // PARÁMETROS DE CONFIGURACIÓN:

    // base: Estado base (Ej: 'admin/entity')
    // baseUrl: URL base que se va a mostrar en el navegador (Ej: '/admin/entity')
    // translateBase: Raíz de los ficheros de mensaje (Ej: 'admin.entity.')
    // parent: Indica si los nuevos estados van englobados dentro de otro. Útil para el breadcrumb.(Opcional)

    // v2: Formularios como componentes
    // templateList: Contiene un componente para los listados (Ej: '<entity-list></entity-list>')
    // templateForm: Contiene un componente para los formularios (Ej: '<entity-form></entity-form>')
    // Nota: A los componentes habrá que pasarle (con bindings) toda la información que se obtenga en el 'resolve' del estado.

    // v1: Formularios sin componentes (controladores y htmls)
    // controller: Controlador para el listado
    // controllerForm: Controlador para el formulario
    // templateUrlBase: Añade un prefijo a los archivos: .list.html o .form.html.

    // Ejemplo de uso: FILO1701. Organismos (/app/admin/organismo)

    angular.module('app')
        .constant('STATE_HELPER', function (params) {
            var states = {
                parent: {
                    name: params.base,
                    parent: params.parent ? params.parent : 'admin',
                    url: params.baseUrl,
                    redirectTo: params.base + '/list',
                    ncyBreadcrumb: {
                        skip: true
                    }
                    // Extend: data {authorities}
                },
                list: {
                    name: params.base + "/list",
                    parent: params.base,
                    url: '/listar/:page',
                    params: {
                        page: {value: '1', dynamic: true}
                    },
                    views: {},
                    data: {
                        pageTitle: params.translateBase + "list.title"
                    },
                    ncyBreadcrumb: {
                        label: params.translateBase + "list.title"
                    }
                },
                create: {
                    name: params.base + "/create",
                    parent: params.base,
                    url: '/crear',
                    data: {
                        pageTitle: params.translateBase + 'create.title',
                        mode: 'create'
                    },
                    views: {},
                    ncyBreadcrumb: {
                        label: params.translateBase + "create.title",
                        parent: params.base + "/list"
                    },
                    // Extend: resolve
                    resolve: {
                        /* @ngInject */
                        previousParams: function ($state) {
                            return $state.params;
                        }
                    }
                },
                edit: {
                    name: params.base + "/edit",
                    parent: params.base,
                    url: '/:id/editar',
                    data: {
                        pageTitle: params.translateBase + 'edit.title',
                        mode: 'edit'
                    },
                    views: {},
                    ncyBreadcrumb: {
                        label: params.translateBase + "edit.title",
                        parent: params.onlyEdit ? null : params.base + "/list"
                    },
                    // Extend: resolve
                    resolve: {
                        /* @ngInject */
                        previousParams: function ($state) {
                            return $state.params;
                        }
                    }
                }
            };

            if (!params.view) {
                params.view = 'content@';
            }

            // Listar
            states.list.views[params.view] = {
                templateUrl: params.templateUrlBase + '.list.html',
                controller: params.controller,
                template: params.templateList ? params.templateList : undefined,
                controllerAs: 'ctrl'
            };
            // Crear y editar
            states.create.views[params.view] = states.edit.views[params.view] = {
                templateUrl: params.templateUrlBase + '.form.html',
                controller: params.controllerForm,
                template: params.templateForm ? params.templateForm : undefined,
                controllerAs: 'ctrl'
            };

            return states;
        });
})();
