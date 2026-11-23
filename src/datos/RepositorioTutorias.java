package datos;

import entidades.ClaseTutoria;
import entidades.EjercicioPlanificado;
import entidades.Estudiante;
import java.util.ArrayList;
import java.util.List;

public class RepositorioTutorias {
    private List<Estudiante> listaEstudiantes;
    private List<ClaseTutoria> listaClases;

    public RepositorioTutorias() {
        this.listaEstudiantes = new ArrayList<>();
        this.listaClases = new ArrayList<>();
        cargarDatosDeEjemplo();
    }

    private void cargarDatosDeEjemplo() {
        Estudiante est1 = new Estudiante("EST-1", "Mateo Paredes", "EPN", "Nivelación");
        Estudiante est2 = new Estudiante("EST-2", "Sofía Andrade", "ESPE", "1er Semestre");
        Estudiante est3 = new Estudiante("EST-3", "Carlos Mena", "EPN", "1er Semestre");
        Estudiante est4 = new Estudiante("EST-4", "Valeria Ruiz", "ESPE", "Nivelación");

        listaEstudiantes.add(est1);
        listaEstudiantes.add(est2);
        listaEstudiantes.add(est3);
        listaEstudiantes.add(est4);

        ClaseTutoria clase1 = new ClaseTutoria(
                "CLS-1",
                "Lunes 23 Nov",
                "15:00",
                "17:00",
                "Cálculo",
                "Capítulo 2: Límites y Continuidad",
                4.50,
                true);
        clase1.agregarEstudiante(est1);
        clase1.agregarEstudiante(est3);
        clase1.agregarEjercicio(new EjercicioPlanificado("Hoja de trabajo EPN: Ejercicios 1 al 10", true));
        clase1.agregarEjercicio(new EjercicioPlanificado("Límites indeterminados 0/0", false));
        clase1.setDictada(true);

        ClaseTutoria clase2 = new ClaseTutoria(
                "CLS-2",
                "Martes 24 Nov",
                "18:00",
                "19:30",
                "Fundamentos de Física",
                "Cinemática 1D y Movimiento Rectilíneo",
                6.00,
                false);
        clase2.agregarEstudiante(est2);
        clase2.agregarEjercicio(new EjercicioPlanificado("Gráficas velocidad-tiempo y caída libre", false));

        listaClases.add(clase1);
        listaClases.add(clase2);
    }

    public void guardarEstudiante(Estudiante estudiante) {
        listaEstudiantes.add(estudiante);
    }

    public List<Estudiante> obtenerTodosLosEstudiantes() {
        return listaEstudiantes;
    }

    public void eliminarEstudiantePorId(String id) {
        for (int i = 0; i < listaEstudiantes.size(); i++) {
            if (listaEstudiantes.get(i).getId().equals(id)) {
                listaEstudiantes.remove(i);
                break;
            }
        }
    }

    public void guardarClase(ClaseTutoria clase) {
        listaClases.add(0, clase);
    }

    public List<ClaseTutoria> obtenerTodasLasClases() {
        return listaClases;
    }

    public List<ClaseTutoria> buscarClasesPorMateria(String materia) {
        if (materia == null || materia.equals("Todas")) {
            return listaClases;
        }
        List<ClaseTutoria> filtradas = new ArrayList<>();
        for (ClaseTutoria clase : listaClases) {
            if (clase.getMateria().equalsIgnoreCase(materia)) {
                filtradas.add(clase);
            }
        }
        return filtradas;
    }

    public void eliminarClasePorId(String id) {
        for (int i = 0; i < listaClases.size(); i++) {
            if (listaClases.get(i).getId().equals(id)) {
                listaClases.remove(i);
                break;
            }
        }
    }
}
