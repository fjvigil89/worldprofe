package es.enxenio.sife1701.controller.custom.util;

import java.io.Serializable;

/**
 * Created by crodriguez on 22/06/2016.
 */
public class Query implements Serializable {

    private String key;

    private String value;

    public Query() {
    }

    public Query(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
