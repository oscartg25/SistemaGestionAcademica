 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import modelo.*;

/**
 *
 * @author GEINDEL
 */
public class BaseDatosSimulada { 
    private List<Estudiante> estudiantes;
    private List<Profesor> profesores;
    private List<Curso> cursos;
    private Map<String, Estudiante> estudiantesMap; // Para búsqueda rápida por ID

    public BaseDatosSimulada() {
        estudiantes = new ArrayList<>();
        profesores = new ArrayList<>();
        cursos = new ArrayList<>();
        estudiantesMap = new HashMap<>();
        
        // Cargar datos existentes al iniciar
       
        
        GestorArchivos.cargarDatos(estudiantes, profesores, cursos);
        actualizarMapaEstudiantes();
    }
     // Métodos para estudiantes
    public void agregarEstudiante(Estudiante estudiante) {
        estudiantes.add(estudiante);
        estudiantesMap.put(estudiante.getId(), estudiante);
    }

    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public Estudiante buscarEstudiante(String id) {
        return estudiantesMap.get(id);
    }

    // Métodos para profesores
    public void agregarProfesor(Profesor profesor) {
        profesores.add(profesor);
    }

    public List<Profesor> getProfesores() {
        return profesores;
    }

    // Métodos para cursos
    public void agregarCurso(Curso curso) {
        cursos.add(curso);
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    // Guardar todos los datos al cerrar la aplicación
    public void guardarDatos() {
        GestorArchivos.guardarDatos(estudiantes, profesores, cursos);
    }

    private void actualizarMapaEstudiantes() {
        estudiantesMap.clear();
        for (Estudiante e : estudiantes) {
            estudiantesMap.put(e.getId(), e);
        }
    }

    public boolean existeProfesor(String id) {
        return profesores.stream().anyMatch(p -> p.getId().equals(id));
    }

    public boolean existeCurso(String codigo) {
        return cursos.stream().anyMatch(c -> c.getCodigo().equals(codigo));
    }
}
    

