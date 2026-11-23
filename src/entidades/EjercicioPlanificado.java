package entidades;

public class EjercicioPlanificado {
    private String descripcion;
    private boolean completado;

    public EjercicioPlanificado(String descripcion) {
        this.descripcion = descripcion;
        this.completado = false;
    }

    public EjercicioPlanificado(String descripcion, boolean completado) {
        this.descripcion = descripcion;
        this.completado = completado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }

    @Override
    public String toString() {
        String marca = completado ? "[Hecho] " : "[Pendiente] ";
        return marca + descripcion;
    }
}
