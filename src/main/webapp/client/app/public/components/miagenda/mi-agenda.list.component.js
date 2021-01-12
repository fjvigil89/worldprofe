(function () {
    'use strict';

    /**
     * Calendario: http://angular-ui.github.io/ui-calendar/
     */

    angular
        .module('app')
        .component('miAgenda', {
            templateUrl: 'app/public/components/miagenda/mi-agenda.list.html',
            controller: MiAgendaController,
            controllerAs: 'ctrl',
            bindings: {
                usuarioAutenticado: '<',
                admin: '<', // Boolean
                initMonth: '<', // Integer: Del 1 al 12
                initYear: '<' // Integer
            }
        });

    /* @ngInject */
    function MiAgendaController($scope, $compile, $timeout, $translate, $state, uiCalendarConfig,
                                Reserva, ClaseLibre, ModalService, Principal, Auth, AlertService) {

        // *********************************************** CONSTANTES ***********************************************
        $scope.COLOR_CLASE_LIBRE = '#4ACF4A';
        $scope.COLOR_CLASE_OCUPADA = 'lightgray';
        $scope.COLOR_RESERVA_SIN_CONTESTAR = 'gold';
        $scope.COLOR_RESERVA_CONFIRMADA = '#87CEFA';
        $scope.COLOR_RESERVA_NO_CONTESTADA_RECHAZADA_CANCELADA_POR_ALUMNO_O_PROFESOR = '#FFB266';
        $scope.COLOR_RESERVA_INCIDENCIA = '#FF6666';
        $scope.COLOR_RESERVA_PENDIENTE = '#8866FF';
        $scope.COLOR_RESERVA_REALIZADA = '#FF66FF';
        var TREINTA_MINUTOS_EN_MS = 1800000;

        // ********************************************* Inicialización *********************************************
        var vm = this;

        function cargarDatos(start, end) {
            $scope.firstTiempoMinimo = true;
            $scope.firstTiempoMaximo = true;
            obtenerClasesLibres(start, end);
        }

        if ($state.params.id != null) {
            vm.profesor = {};
            vm.profesor.id = $state.params.id;
        }

        if (vm.usuarioAutenticado == null) {
            Principal.identity().then(function (data) {
                vm.usuarioAutenticado = data;
                $scope.usuarioLogueado = vm.usuarioAutenticado != null;
            });
        } else {
            $scope.usuarioLogueado = true;
        }

        if ($state.params.id != null) {
            vm.usuarioObtenerClasesLibres = vm.profesor;
            $scope.alumnoViendose = false;
        } else {
            vm.usuarioObtenerClasesLibres = vm.usuarioAutenticado;
            $scope.alumnoViendose = true;
        }

        var defaultView = 'agendaWeek';
        if (vm.admin) {
            defaultView = 'month';
        }

        // En caso de que se indiquen un mes y un año, inicializamos el calendario
        var today = new Date();
        var defaultDate = new Date();
        if (vm.initMonth) {
            vm.initMonth = vm.initMonth - 1;
            defaultDate.setMonth(vm.initMonth);
        }
        if (vm.initYear) {
            defaultDate.setFullYear(vm.initYear);
        } else {
            if (vm.initMonth > today.getMonth()) {
                defaultDate.setFullYear(today.getFullYear() - 1);
            }
        }

        $scope.rol = vm.usuarioObtenerClasesLibres.rol;
        $scope.canceladasRechazadas = $state.params.canceladasRechazadas;

        $scope.firstTiempoMinimo = true;
        $scope.firstTiempoMaximo = true;

        // ***************************************** Funcionalidad *****************************************

        function actualizarEvento(evento) {
            if (evento != null) {
                angular.element('.calendar').fullCalendar('updateEvent', evento);
            }
        }

        function actualizarRangosTiempo() {
            // FIXME: Para que funcionase lo de adaptar el calendario a las clases (no mostrar las 24 horas, si no solo las necesarias)
            //  habría que encontrar la manera de que no se llamase a renderView al cambiar minTime y maxTime
            // Actualizar minTime y maxTime (hora de inicio y de fin del calendario)
            // if (!$scope.rol || $scope.rol === 'ROLE_ALUMNO') {
            //     // vm.cambiandoTiempo = true;
            //     $scope.uiConfig.calendar.minTime = $scope.tiempoMinimo.getHours() + ':' + $scope.tiempoMinimo.getMinutes();
            //     $scope.uiConfig.calendar.maxTime = $scope.tiempoMaximo.getHours() + ':' + $scope.tiempoMaximo.getMinutes();
            // }
        }

        function actualizarAgenda(eventos) {
            uiCalendarConfig.calendars.myCalendar.fullCalendar('removeEvents');
            uiCalendarConfig.calendars.myCalendar.fullCalendar('addEventSource', eventos);
        }

        var minutesOfDay = function (d) {
            return d.getMinutes() + d.getHours() * 60;
        };

        function devolverColorEvento(evento) {
            if (evento != null) {
                if (evento.ocupada) {
                    evento.backgroundColor = $scope.COLOR_CLASE_OCUPADA;
                    evento.borderColor = $scope.COLOR_CLASE_OCUPADA;
                } else {
                    evento.backgroundColor = $scope.COLOR_CLASE_LIBRE;
                    evento.borderColor = $scope.COLOR_CLASE_LIBRE;
                }
            }
        }

        function clasesLibresToBloque(response, start, end) {
            var resultados = response.content;
            if (resultados.length) {
                var inicio = null;
                var fin = null;
                for (var i = 0; i < resultados.length; i++) {
                    inicio = new Date(resultados[i].fecha * 1000);
                    // Coger el tiempo mínimo para mostrar la agenda acotada
                    if ($scope.firstTiempoMinimo || $scope.tiempoMinimo != null && (minutesOfDay($scope.tiempoMinimo) > minutesOfDay(inicio))) {
                        $scope.tiempoMinimo = new Date(inicio.valueOf());
                        $scope.firstTiempoMinimo = false;
                    }
                    fin = new Date(resultados[i].fecha * 1000 + TREINTA_MINUTOS_EN_MS);
                    // Coger el tiempo máximo para mostrar la agenda acotada
                    if ($scope.firstTiempoMaximo || $scope.tiempoMaximo != null && (minutesOfDay($scope.tiempoMaximo) < minutesOfDay(fin))) {
                        $scope.tiempoMaximo = new Date(fin.valueOf());
                        $scope.firstTiempoMaximo = false;
                    }
                    var className = '';
                    if (inicio < today) {
                        className = 'eventosPasados';
                    }
                    if (resultados[i].ocupada) {
                        $scope.events.push({
                            start: inicio,
                            end: fin,
                            backgroundColor: $scope.COLOR_CLASE_OCUPADA,
                            borderColor: $scope.COLOR_CLASE_OCUPADA,
                            ocupada: resultados[i].ocupada,
                            id: resultados[i].id,
                            className: className
                        });
                    } else {
                        $scope.events.push({
                            start: inicio,
                            end: fin,
                            backgroundColor: $scope.COLOR_CLASE_LIBRE,
                            borderColor: $scope.COLOR_CLASE_LIBRE,
                            ocupada: resultados[i].ocupada,
                            id: resultados[i].id,
                            className: className
                        });
                    }
                }

                if (vm.usuarioAutenticado == null) {
                    actualizarRangosTiempo();
                    actualizarAgenda($scope.events);
                }
            }
        }

        vm.countEstados = {};

        function reservasToBloque(response, start, end, ocupada) {
            vm.countEstados = {};
            var resultados = response.content;
            var inicio = null;
            var fin = null;
            var backgroundColor = null;
            var borderColor = null;
            var reservasAux = [];
            for (var i = 0; i < resultados.length; i++) {
                inicio = new Date(resultados[i].fecha * 1000);
                // Coger el tiempo mínimo para mostrar la agenda acotada
                if ($scope.firstTiempoMinimo || $scope.tiempoMinimo != null && (minutesOfDay($scope.tiempoMinimo) > minutesOfDay(inicio))) {
                    $scope.tiempoMinimo = new Date(inicio.valueOf());
                    $scope.firstTiempoMinimo = false;
                }
                fin = new Date(resultados[i].fecha * 1000 + TREINTA_MINUTOS_EN_MS);
                // Coger el tiempo máximo para mostrar la agenda acotada
                if ($scope.firstTiempoMaximo || $scope.tiempoMaximo != null && (minutesOfDay($scope.tiempoMaximo) < minutesOfDay(fin))) {
                    $scope.tiempoMaximo = new Date(fin.valueOf());
                    $scope.firstTiempoMaximo = false;
                }

                // Número de reservas en cada estado
                if (vm.countEstados[resultados[i].estado]) {
                    vm.countEstados[resultados[i].estado] = (vm.countEstados[resultados[i].estado] + 1);
                } else {
                    vm.countEstados[resultados[i].estado] = 1;
                }

                switch (resultados[i].estado) {
                    case 'SIN_CONTESTAR':
                        backgroundColor = $scope.COLOR_RESERVA_SIN_CONTESTAR;
                        borderColor = $scope.COLOR_RESERVA_SIN_CONTESTAR;
                        break;
                    case 'CONFIRMADA':
                        backgroundColor = $scope.COLOR_RESERVA_CONFIRMADA;
                        borderColor = $scope.COLOR_RESERVA_CONFIRMADA;
                        break;
                    case 'NO_CONTESTADA':
                    case 'RECHAZADA':
                    case 'CANCELADA_POR_ALUMNO':
                    case 'CANCELADA_POR_PROFESOR':
                        backgroundColor = $scope.COLOR_RESERVA_NO_CONTESTADA_RECHAZADA_CANCELADA_POR_ALUMNO_O_PROFESOR;
                        borderColor = $scope.COLOR_RESERVA_NO_CONTESTADA_RECHAZADA_CANCELADA_POR_ALUMNO_O_PROFESOR;
                        break;
                    case 'INCIDENCIA':
                        backgroundColor = $scope.COLOR_RESERVA_INCIDENCIA;
                        borderColor = $scope.COLOR_RESERVA_INCIDENCIA;
                        break;
                    case 'PENDIENTE':
                        backgroundColor = $scope.COLOR_RESERVA_PENDIENTE;
                        borderColor = $scope.COLOR_RESERVA_PENDIENTE;
                        break;
                    case 'REALIZADA':
                        backgroundColor = $scope.COLOR_RESERVA_REALIZADA;
                        borderColor = $scope.COLOR_RESERVA_REALIZADA;
                        break;
                }

                //FIXME: Optimizar esto
                for (var j = 0; j < $scope.events.length; j++) {
                    if (resultados[i].claseLibre != null && $scope.events[j].id === resultados[i].claseLibre.id) {
                        $scope.events.splice(j, 1);
                    }
                }

                var titulo;
                var persona;
                var className = '';
                if (vm.usuarioAutenticado.rol === 'ROLE_PROFESOR') {
                    persona = resultados[i].alumno;
                } else {
                    persona = resultados[i].profesor;
                }
                // Mostramos el nombre del alumno/profesor con el que va a ser la clase
                titulo = persona.nombre;
                titulo += persona.apellidos ? ' ' + persona.apellidos : '';

                // De las reservas pasadas, dejamos las pendientes resaltadas
                if (inicio < today && (resultados[i].estado !== 'PENDIENTE' || vm.usuarioAutenticado.rol === 'ROLE_ALUMNO')) {
                    className = 'eventosPasados';
                }

                reservasAux.push({
                    start: inicio,
                    end: fin,
                    backgroundColor: backgroundColor,
                    borderColor: borderColor,
                    ocupada: ocupada,
                    id: resultados[i].id,
                    estado: resultados[i].estado,
                    motivoProfesor: resultados[i].motivoProfesor,
                    motivoAlumno: resultados[i].motivoAlumno,
                    className: className,
                    title: titulo
                });
            }

            $scope.events.push.apply($scope.events, reservasAux);

            actualizarRangosTiempo();
            actualizarAgenda($scope.events);
        }

        // Función que muestra las clases libres y las reservas para rellenar la agenda (fullcalendar)
        function obtenerClasesLibres(start, end) {
            $scope.events = [];
            ClaseLibre.findAll({
                fechaInicio: start.toDate(),
                fechaFin: end.toDate(),
                ocupada: null,
                id: vm.usuarioObtenerClasesLibres.id
            }).$promise.then(
                function (response) {
                    if ($state.params.canceladasRechazadas == null || $state.params.canceladasRechazadas === 'false') {
                        clasesLibresToBloque(response, start, end);
                    }
                    if (vm.usuarioAutenticado != null) {
                        Reserva.findAll({
                            id: vm.usuarioAutenticado.id,
                            rol: vm.usuarioAutenticado.rol,
                            fechaInicio: start.toDate(),
                            fechaFin: end.toDate(),
                            canceladasRechazadas: $state.params.canceladasRechazadas
                        }).$promise.then(
                            function (response) {
                                reservasToBloque(response, start, end, true);
                                vm.loading = false;
                            },
                            function (error) {
                                vm.error = error.data.mensaje;
                                vm.loading = false;
                            }
                        );
                    } else {
                        vm.loading = false;
                    }
                },
                function (error) {
                    vm.error = error.data.mensaje;
                    vm.loading = false;
                });
        }

        $scope.deseleccionarEvento = function () {
            $scope.isCollapsed = false;
            devolverColorEvento($scope.eventoSeleccionado);
            actualizarEvento($scope.eventoSeleccionado);
        };

        $scope.duplicarSemana = function () {
            ClaseLibre.duplicarsemana({
                fechaInicio: $scope.start,
                fechaFin: $scope.end,
                id: vm.usuarioObtenerClasesLibres.id
            }).$promise.then(function (data) {
                AlertService.success("admin.reserva.duplicar.semana");
            })
        };

        $scope.dayClick = function (date, allDay, jsEvent, view) {
            if (jsEvent.type === 'agendaWeek' && $scope.canceladasRechazadas == 'false') {
                if (date.toDate() > today) {
                    if (vm.usuarioAutenticado.rol === 'ROLE_PROFESOR') {
                        $scope.events.push({
                            start: date.toDate(),
                            end: date.clone().add('30', 'm').toDate(),
                            backgroundColor: $scope.COLOR_CLASE_LIBRE,
                            borderColor: $scope.COLOR_CLASE_LIBRE
                        });
                        ClaseLibre.save({
                            fecha: date,
                            ocupada: false,
                            profesor: {id: vm.usuarioAutenticado.id, type: vm.usuarioAutenticado.type}
                        }).$promise.then(function () {
                            cargarDatos(jsEvent.start, jsEvent.end);
                        }).catch(function () {
                            // Si se detecta un error, se recarga la página
                            cargarDatos(jsEvent.start, jsEvent.end);
                        })
                    }
                }
            }
        };

        $scope.alertOnEventClick = function (clase, jsEvent, view) {
            if (vm.admin) return;

            if (vm.usuarioAutenticado == null) {
                Auth.authorize(false, "ROLE_AUTHENTICATED");
            }

            if (vm.usuarioAutenticado != null) {
                // Un profesor puede eliminar una hora libre si no está reservada
                if (vm.usuarioAutenticado.rol === 'ROLE_PROFESOR' && !clase.ocupada && clase.start.toDate() > today) {
                    ModalService.open({
                        templateUrl: 'app/components/form/confirm/confirm.modal.html',
                        controller: 'ConfirmModalController',
                        controllerAs: 'ctrl',
                        resolve: {
                            /* @ngInject */
                            tipo: function (ClaseLibre) {
                                return ClaseLibre;
                            },
                            funcion: function () {
                                return "subtract";
                            },
                            parametros: function () {
                                return {id: clase.id}
                            },
                            params: function () {
                                return {
                                    title: 'admin.claselibre.bloque.delete.title',
                                    body: 'admin.claselibre.bloque.delete.confirm',
                                    property: ''
                                };
                            },
                            item: function () {
                            },
                            configuracion: function () {
                            }
                        }
                    }).result.then(function () {
                        for (var i = 0; i < $scope.events.length; i++) {
                            if ($scope.events[i].id == clase.id) {
                                $scope.events.splice(i, 1);
                                actualizarAgenda($scope.events);
                            }
                        }
                    });
                } else
                // Un alumno puede reservar una clase libre
                // Un profesor puede confirmar o rechazar una reserva sin contestar
                // Un profesor o alumno pueden cancelar una reserva confirmada a X horas de la realización de la misma
                // Un profesor puede indicar si la clase se ha llevado a cabo o no (incidencia)
                // Un alumno puede escribir un comentario en las clases realizadas
                {
                    if (clase.start.toDate() < today && (clase.estado === 'SIN_CONTESTAR' || !clase.ocupada)) {
                        return;
                    }
                    if (clase.estado == null && clase.ocupada == true) {
                        return;
                    }
                    $scope.eventoSeleccionado = angular.element('.calendar').fullCalendar('clientEvents', clase._id)[0];
                    $scope.eventoSeleccionado.vistaInicio = view.start;
                    $scope.eventoSeleccionado.vistaFin = view.end;
                    $scope.anteriorEventoIdSeleccionado = clase._id;
                    ModalService.open({
                        templateUrl: 'app/components/reserva/reserva.modal.html',
                        controller: 'ReservaModalController',
                        controllerAs: 'ctrl',
                        resolve: {
                            parametros: function () {
                                return {
                                    id: clase.id,
                                    rolbd: vm.usuarioAutenticado.rol,
                                    estadobd: clase.estado,
                                    fechaPrimeraClase: clase.start.toDate(),
                                    fechaUltimaClase: clase.end.toDate(),
                                    alumnoId: vm.usuarioAutenticado.id,
                                    creditosDisponibles: vm.usuarioAutenticado.creditosDisponibles,
                                    motivoAlumno: clase.motivoAlumno,
                                    motivoProfesor: clase.motivoProfesor
                                }
                            },
                            item: function () {
                                return clase;
                            },
                            /* @ngInject */
                            usuarioModal: function (User) {
                                if (clase.estado == null) {
                                    return User.getProfesorPorClaseLibre({id: clase.id}).$promise.then(function (data) {
                                        return data;
                                    });
                                } else {
                                    return User.getUsuarioPorReserva({
                                        id: clase.id,
                                        rol: vm.usuarioAutenticado.rol
                                    }).$promise.then(function (data) {
                                        return data;
                                    });
                                }
                            },
                            /* @ngInject */
                            configuracion: function (Configuracion) {
                                return Configuracion.get({id: '0'}).$promise.then(function (data) {
                                    return data;
                                });
                            }
                        }
                    }).result.then(function () {
                        Principal.identity(true).then(function (data) {
                            vm.usuarioAutenticado.creditosDisponibles = data.creditosDisponibles;
                            vm.usuarioAutenticado.creditosConsumidos = data.creditosConsumidos;
                        });
                        cargarDatos(view.start, view.end);
                    });
                }
            }
        };

        $scope.eventRender = function (event, element, view) {
            element.attr({'uib-tooltip': event.tooltip, 'uib-tooltip-append-to-body': true});
            $compile(element)($scope);
        };

        $scope.viewRender = function (view, element) {
            if (!vm.loading) {
                vm.loading = true;
                $scope.start = view.start;
                $scope.end = view.end;
                cargarDatos(view.start, view.end);
                if (view.intervalUnit === 'week') {
                    angular.element(".fc-duplicarSemana-button").show();
                } else {
                    angular.element(".fc-duplicarSemana-button").hide();
                }
            }
        };

        $scope.eventAfterAllRender = function () {
        };

        // ***************************************** ui-calendar *****************************************
        // https://fullcalendar.io/docs/display/
        $scope.uiConfig = {
            calendar: {
                defaultDate: defaultDate,
                height: 'auto',
                editable: false,
                allDaySlot: false,
                defaultView: defaultView,
                timezone: 'local',
                minTime: '00:00',
                maxTime: '24:00',
                locale: 'es',
                firstDay: 1,
                eventOverlap: false,
                eventStartEditable: false,
                header: {left: 'agendaWeek,month', center: 'title', right: 'recargar duplicarSemana today prev,next'},
                timeFormat: 'H:mm',
                slotLabelFormat: 'H:mm',
                dayClick: $scope.dayClick,
                eventClick: $scope.alertOnEventClick,
                eventDurationEditable: false,
                eventRender: $scope.eventRender,
                viewRender: $scope.viewRender,
                eventAfterAllRender: $scope.eventAfterAllRender
            }
        };

        // Botón de recarga
        $scope.uiConfig.calendar.customButtons = {
            recargar: {
                icon: 'recargar fa fa-refresh',
                click: function () {
                    cargarDatos($scope.start, $scope.end);
                }
            }
        };

        // Botón para el duplicado de semanas
        if (vm.usuarioAutenticado != null && vm.usuarioAutenticado.rol === 'ROLE_PROFESOR' && !vm.admin) {
            $scope.uiConfig.calendar.editable = true;
            angular.extend($scope.uiConfig.calendar.customButtons, {
                duplicarSemana: {
                    text: $translate.instant('admin.reserva.duplicar.title'),
                    click: function () {
                        $scope.duplicarSemana();
                    }
                }
            });
        }

    }

})();
