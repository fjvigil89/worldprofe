package es.enxenio.sife1701.controller.custom.filter;

/**
 * Created by crodriguez on 15/01/2019.
 */
public class ProfesorAdminFilter extends UsuarioFilter {

    private Integer mes;

    private Integer ano;

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }
}
