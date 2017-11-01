package co.com.eleinco.formularioestaticosimple;

/**
 * Created by Usuario on 10/08/2017.
 */

public class containerCompromisos {

    String compromiso, responsable, estado;

    public containerCompromisos(String compromiso, String responsable, String estado) {
        this.compromiso = compromiso;
        this.responsable = responsable;
        this.estado = estado;
    }

    public String getCompromiso() {
        return compromiso;
    }

    public void setCompromiso(String compromiso) {
        this.compromiso = compromiso;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


}
