package controladores;

import datos.RepositorioTutorias;
import entidades.ClaseTutoria;
import entidades.EjercicioPlanificado;
import entidades.Estudiante;
import java.util.List;

public class ControladorTutorias {
    public static final int MAXIMO_ESTUDIANTES_POR_CLASE = 10;
    public static final String[] MATERIAS_DISPONIBLES = {
        "Álgebra",
        "Cálculo",
        "Fundamentos de Matemática",
        "Fundamentos de Física",
        "Geometría"
    };

    private RepositorioTutorias repositorio;
    private int contadorEstudiantes;
    private int contadorClases;

    public ControladorTutorias() {
        this.repositorio = new RepositorioTutorias();
        this.contadorEstudiantes = 5;
        this.contadorClases = 4;
    }

    public String registrarEstudiante(String nombreCompleto, String universidad, String nivelAcademico) {
        if (nombreCompleto == null || nombreCompleto.trim().isEmpty()) {
            return "Escribe el nombre del estudiante.";
        }
        if (universidad == null || universidad.trim().isEmpty()) {
            return "Selecciona la universidad.";
        }

        String nuevoId = "EST-" + contadorEstudiantes++;
        Estudiante nuevo = new Estudiante(
                nuevoId,
                nombreCompleto.trim(),
                universidad.trim(),
                nivelAcademico);
        repositorio.guardarEstudiante(nuevo);
        return "OK";
    }

    public List<Estudiante> obtenerEstudiantes() {
        return repositorio.obtenerTodosLosEstudiantes();
    }

    public void eliminarEstudiante(String id) {
        repositorio.eliminarEstudiantePorId(id);
    }

    public double calcularHorasPorEstudiante(String idEstudiante) {
        double total = 0.0;
        for (ClaseTutoria clase : repositorio.obtenerTodasLasClases()) {
            for (Estudiante est : clase.getListaEstudiantes()) {
                if (est.getId().equals(idEstudiante)) {
                    total += clase.calcularDuracionHoras();
                    break;
                }
            }
        }
        return Math.round(total * 100.0) / 100.0;
    }

    public String agendarNuevaClase(
            String fecha,
            String horaInicio,
            String horaFin,
            String materia,
            String temaCapitulo,
            String ejerciciosTexto,
            List<Estudiante> estudiantesSeleccionados,
            double tarifaPorHora,
            boolean cobroPorEstudiante) {

        if (fecha == null || fecha.trim().isEmpty()) {
            return "Ingresa la fecha o día de la clase.";
        }
        if (temaCapitulo == null || temaCapitulo.trim().isEmpty()) {
            return "Ingresa el tema o capítulo que van a estudiar.";
        }
        if (estudiantesSeleccionados == null || estudiantesSeleccionados.isEmpty()) {
            return "Selecciona al menos 1 estudiante.";
        }
        if (estudiantesSeleccionados.size() > MAXIMO_ESTUDIANTES_POR_CLASE) {
            return "El cupo máximo es de 10 estudiantes por clase.";
        }
        if (tarifaPorHora <= 0) {
            return "La tarifa por hora debe ser mayor a cero.";
        }

        int minInicio = convertirHoraAMinutos(horaInicio);
        int minFin = convertirHoraAMinutos(horaFin);
        if (minFin <= minInicio) {
            return "La hora de fin debe ser posterior a la hora de inicio.";
        }

        String nuevoId = "CLS-" + contadorClases++;
        ClaseTutoria nuevaClase = new ClaseTutoria(
                nuevoId,
                fecha.trim(),
                horaInicio,
                horaFin,
                materia,
                temaCapitulo.trim(),
                tarifaPorHora,
                cobroPorEstudiante);

        for (Estudiante est : estudiantesSeleccionados) {
            nuevaClase.agregarEstudiante(est);
        }

        if (ejerciciosTexto != null && !ejerciciosTexto.trim().isEmpty()) {
            String[] items = ejerciciosTexto.split(";");
            for (String item : items) {
                if (!item.trim().isEmpty()) {
                    nuevaClase.agregarEjercicio(new EjercicioPlanificado(item.trim()));
                }
            }
        }

        repositorio.guardarClase(nuevaClase);
        return "OK";
    }

    public List<ClaseTutoria> obtenerClases(String filtroMateria) {
        return repositorio.buscarClasesPorMateria(filtroMateria);
    }

    public void alternarClaseDictada(ClaseTutoria clase) {
        if (clase != null) {
            clase.setDictada(!clase.isDictada());
        }
    }

    public void alternarClasePagada(ClaseTutoria clase) {
        if (clase != null) {
            boolean nuevoEstado = !clase.isPagada();
            clase.setPagada(nuevoEstado);
            if (nuevoEstado) {
                clase.setDictada(true);
            }
        }
    }

    public void eliminarClase(String id) {
        repositorio.eliminarClasePorId(id);
    }

    public double calcularTotalHoras() {
        double suma = 0.0;
        for (ClaseTutoria clase : repositorio.obtenerTodasLasClases()) {
            suma += clase.calcularDuracionHoras();
        }
        return Math.round(suma * 100.0) / 100.0;
    }

    public double calcularTotalPorCobrar() {
        double suma = 0.0;
        for (ClaseTutoria clase : repositorio.obtenerTodasLasClases()) {
            if (!clase.isPagada()) {
                suma += clase.calcularTotalCobro();
            }
        }
        return Math.round(suma * 100.0) / 100.0;
    }

    public double calcularTotalCobrado() {
        double suma = 0.0;
        for (ClaseTutoria clase : repositorio.obtenerTodasLasClases()) {
            if (clase.isPagada()) {
                suma += clase.calcularTotalCobro();
            }
        }
        return Math.round(suma * 100.0) / 100.0;
    }

    private int convertirHoraAMinutos(String hora) {
        String[] partes = hora.split(":");
        return Integer.parseInt(partes[0]) * 60 + Integer.parseInt(partes[1]);
    }
}
