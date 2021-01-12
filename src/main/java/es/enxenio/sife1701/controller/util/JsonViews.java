package es.enxenio.sife1701.controller.util;

/**
 * Vistas de JSON para filter lo que se envía desde el controller.
 * Si un atributo no se etiqueta con @JsonView(JsonViews.x.class), por defecto se enviará.
 * Created by crodriguez on 22/09/2016.
 */
public class JsonViews {

    // Pensado para tablas. Sólo atributos básicos. (Por ejemplo, tablas de administración).
    public static class List {
    }

    // Para tablas que tienen algo más de contenido. (Por ejemplo, lista de libros en la parte pública)
    public static class DetailedList extends List {
    }

    // Todos los campos que no queremos que vayan en List hay que anotarlos con Details.
    public static class Details extends DetailedList {
    }

    // Todos los campos que no queremos que vayan en List hay que anotarlos con Details.
    public static class ExtraDetails extends Details {
    }

}
