package co.com.eleinco.formularioestaticosimple;

/**
 * Created by Usuario on 08/08/2017.
 */

public class containerAsistentes {

    private String nombre, cargo;

    public containerAsistentes(String nombre, String cargo) {
        this.nombre = nombre;
        this.cargo = cargo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
