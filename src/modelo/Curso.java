/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author GEINDEL
 */
public class Curso {
    private String nombre;
    private String codigo;
    private Profesor profesor;
    private List<Estudiante> estudiantes;

    public Curso(String nombre, String codigo) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.estudiantes = new ArrayList<>();
    }

    // Método para generar formato CSV
    public String toCSV() {
        String profesorId = (profesor != null) ? profesor.getId() : "null";
        return String.join(",", codigo, nombre, profesorId);
    }

    // Métodos para manejar estudiantes
    public void agregarEstudiante(Estudiante estudiante) {
        if (!estudiantes.contains(estudiante)) {
            estudiantes.add(estudiante);
        }
    }

    public void quitarEstudiante(Estudiante estudiante) {
        estudiantes.remove(estudiante);
    }

    public void quitarEstudiante(String idEstudiante) {
        estudiantes.removeIf(e -> e.getId().equals(idEstudiante));
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public List<Estudiante> getEstudiantes() {
        return new ArrayList<>(estudiantes); // Devuelve copia para evitar modificaciones externas
    }

    @Override
    public String toString() {
        return "Curso: " + nombre + " (" + codigo + ")" +
               ", Profesor: " + (profesor != null ? profesor.getNombre() : "No asignado") +
               ", Estudiantes: " + estudiantes.size();
    }

    // Método para verificar igualdad basado en código (único)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Curso curso = (Curso) obj;
        return codigo.equals(curso.codigo);
    }

    @Override
    public int hashCode() {
        return codigo.hashCode();
    }
}
