package entidades;

public class Estudiante {
    private String id;
    private String nombreCompleto;
    private String universidad;
    private String nivelAcademico;

    public Estudiante(String id, String nombreCompleto, String universidad, String nivelAcademico) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.universidad = universidad;
        this.nivelAcademico = nivelAcademico;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getUniversidad() {
        return universidad;
    }

    public void setUniversidad(String universidad) {
        this.universidad = universidad;
    }

    public String getNivelAcademico() {
        return nivelAcademico;
    }

    public void setNivelAcademico(String nivelAcademico) {
        this.nivelAcademico = nivelAcademico;
    }

    @Override
    public String toString() {
        return nombreCompleto + " (" + universidad + " - " + nivelAcademico + ")";
    }
}
