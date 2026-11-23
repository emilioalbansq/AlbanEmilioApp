package entidades;

import java.util.ArrayList;
import java.util.List;

public class ClaseTutoria {
    private String id;
    private String fecha;
    private String horaInicio;
    private String horaFin;
    private String materia;
    private String temaCapitulo;
    private List<EjercicioPlanificado> listaEjercicios;
    private List<Estudiante> listaEstudiantes;
    private double tarifaPorHora;
    private boolean cobroPorEstudiante;
    private boolean dictada;
    private boolean pagada;

    public ClaseTutoria(
            String id,
            String fecha,
            String horaInicio,
            String horaFin,
            String materia,
            String temaCapitulo,
            double tarifaPorHora,
            boolean cobroPorEstudiante) {
        this.id = id;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.materia = materia;
        this.temaCapitulo = temaCapitulo;
        this.listaEjercicios = new ArrayList<>();
        this.listaEstudiantes = new ArrayList<>();
        this.tarifaPorHora = tarifaPorHora;
        this.cobroPorEstudiante = cobroPorEstudiante;
        this.dictada = false;
        this.pagada = false;
    }

    public boolean agregarEstudiante(Estudiante estudiante) {
        if (listaEstudiantes.size() >= 10) {
            return false;
        }
        for (Estudiante actual : listaEstudiantes) {
            if (actual.getId().equals(estudiante.getId())) {
                return false;
            }
        }
        listaEstudiantes.add(estudiante);
        return true;
    }

    public void agregarEjercicio(EjercicioPlanificado ejercicio) {
        listaEjercicios.add(ejercicio);
    }

    public double calcularDuracionHoras() {
        try {
            String[] partesInicio = horaInicio.split(":");
            String[] partesFin = horaFin.split(":");

            int minutosInicio = Integer.parseInt(partesInicio[0]) * 60 + Integer.parseInt(partesInicio[1]);
            int minutosFin = Integer.parseInt(partesFin[0]) * 60 + Integer.parseInt(partesFin[1]);

            int diferenciaMinutos = minutosFin - minutosInicio;
            if (diferenciaMinutos <= 0) {
                return 1.0;
            }
            double horas = diferenciaMinutos / 60.0;
            return Math.round(horas * 100.0) / 100.0;
        } catch (Exception e) {
            return 1.0;
        }
    }

    public double calcularTotalCobro() {
        double horas = calcularDuracionHoras();
        int multiplicador = 1;
        if (cobroPorEstudiante && !listaEstudiantes.isEmpty()) {
            multiplicador = listaEstudiantes.size();
        }
        double total = horas * tarifaPorHora * multiplicador;
        return Math.round(total * 100.0) / 100.0;
    }

    public String obtenerNombresEstudiantes() {
        if (listaEstudiantes.isEmpty()) {
            return "Sin alumnos";
        }
        StringBuilder texto = new StringBuilder();
        for (int i = 0; i < listaEstudiantes.size(); i++) {
            texto.append(listaEstudiantes.get(i).getNombreCompleto());
            if (i < listaEstudiantes.size() - 1) {
                texto.append(", ");
            }
        }
        return texto.toString();
    }

    public String obtenerEstadoTexto() {
        if (pagada) {
            return "Pagada";
        }
        if (dictada) {
            return "Dictada";
        }
        return "Programada";
    }

    public String getId() {
        return id;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public String getMateria() {
        return materia;
    }

    public String getTemaCapitulo() {
        return temaCapitulo;
    }

    public List<EjercicioPlanificado> getListaEjercicios() {
        return listaEjercicios;
    }

    public List<Estudiante> getListaEstudiantes() {
        return listaEstudiantes;
    }

    public double getTarifaPorHora() {
        return tarifaPorHora;
    }

    public boolean isCobroPorEstudiante() {
        return cobroPorEstudiante;
    }

    public boolean isDictada() {
        return dictada;
    }

    public void setDictada(boolean dictada) {
        this.dictada = dictada;
    }

    public boolean isPagada() {
        return pagada;
    }

    public void setPagada(boolean pagada) {
        this.pagada = pagada;
    }
}
