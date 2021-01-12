*Errores y código que puede ser interesante a la hora de desarrollar.*

## Cliente

### Estados

* Hay que tener cuidado con los **parámetros definidos en la URL**. Si se define un estado con URL `/prueba/:page` y a continuación otro `/prueba/detalles/:id`, lo que hará Angular será entrar por el primer estado y meter `/detalles/:id` en el parámetro `:page`. La solución más rápida (y por ahora la única que sabemos que funciona) es cambiar el orden de los estados.

### Formularios

* Hay que mantener la siguiente estructura en los formularios para que la validación se vea bien:

```html
<div class="row">
    <!--Atributo-->
    <div class="col-sm-4 ">
        <div class="form-group">
            <label-form key="admin.entidad.form.atributo" for="atributo" required="true"></label-form>
            <input class="form-control" id="atributo" type="text" ng-model="ctrl.item.atributo" required>
        </div>
    </div>
</div>
```

* Para incluir un componente (posiblemente un formulario) en una ventana modal, utilizar el componente EntityModal (que añade una cabecera a la ventana modal), e indicar el componente y sus parámetros del siguiente modo:

```javascript
ModalService.openComponent('admin.entidad.create.title', {
        component: 'entityModal',
        resolve: {
            componente: function () {
                return "<entidad-form-admin item=\'ctrl.resolve.item\'></entidad-form-admin>"
            },
            /* @ngInject */
            item: function (Entidad) {
                return new Entidad();
            }
        }
    }
).result.then(function (result) {
    // Añadimos el nuevo elemento al select
    vm.entidades.push(result);
    // Asociamos el nuevo elemento a la entidad
    vm.item.entidad = result;
});
```

### Pestañas (Tabs)

- Una pestaña por cada estado. Es necesario hacerlo así si las URLs son diferentes y se puede acceder directamente una pestaña que no sea la primera. 
Hay un ejemplo en `mis-libros.html`:

```html
<ul class="nav nav-tabs">
    <li ui-sref-active="active">
        <a ui-sref="estado.subestado1" translate="etiqueta.1"></a>
    </li>
    <li ui-sref-active="active">
        <a ui-sref="estado.subestado2" translate="etiqueta.2"></a>
    </li>
</ul>
```

- En otros casos se utilizan los elementos de Angular Bootstrap `<uib-tabset>` y `<uib-tab>`.

### Watchers

* Para saber desde el controller si un atributo se modifica:

```javascript
$scope.$watch(function () {
  return vm.model;
}, function () {
  init();
}, deep);
```

El campo `deep` nos permite indicar si el *watcher* controlará las propiedades anidadas. Por defecto es `false`.

### CSS

* Es muy **importante** que si se añade un archivo .css dentro de un componente, iniciarlo con un comentario (`/* */`). En caso contrario, al generar el WAR, se concatenará un ";" con el primer elemento definido en el archivo, y no tendrá efecto.
* En el archivo `variables.scss` se pueden sobrescribir cualquier variable de Bootstrap, como, por ejemplo, los colores de los botones.

### Archivos interesantes

Archivos reutilizables en muchos casos:

* Diálogo genérico de confirmación: `confirm.modal.html`. Ejemplo de uso: *club.list.component.js* (en OCTO1601).
* Diálogo genérico para eliminar elementos: `entity.delete.modal.*`. Ejemplo de uso: cualquier página de administración.
* `ng-table-helper.service.js`
* Operaciones predefinidas para las entidades: `resource-helper.service.js`
* Estados predefinidos para administración: `admin.state.constants.js`

## Servidor

### Transacciones

Cuando se lanza una *checked exception* en un servicio, no se hace *rollback*, por lo que si dentro de ese servicio se llama otra operación que realiza cambios sobre la BD, estos cambios se mantendrán. 
Para hacer que un servicio, se lance la excepción que se lance, haga *rollback*, hay que anotarlo de la siguiente manera: 

> `@Transactional(rollbackFor = Exception.class)`

### ZonedDateTime

Para que un filtro pueda recibir una fecha correctamente, hay que que definir el atributo de la siguiente manera: 

>`@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)`

>`private ZonedDateTime inicio;`

### Tiempos

Para cronometras métodos, a parte de JavaMelody, se puede utilizar `StopWatch`:
```java
StopWatch watch = new StopWatch();
watch.start();
// método
watch.stop();
System.out.println("Tiempo de ejecución en Repository: " + watch.getTime());
```

### Hibernate

#### HQL

**Listas como parámetros**: Hay que dividir la consulta y formarla poco a poco, dejando fuera de la consulta la comparación con la lista en caso de que esta sea vacía o NULL.

#### @OrderBy

`@OrderBy` por propiedades anidadas (segunda respuesta): https://stackoverflow.com/questions/10518216/hibernate-orderby-for-nested-properties

### Otras cuestiones de interés

* Acceso no autorizado: `return new ResponseEntity<>(HttpStatus.FORBIDDEN);`. Si se utiliza `HttpStatus.UNAUTHORIZED` desautentica al usuario de la plataforma.
