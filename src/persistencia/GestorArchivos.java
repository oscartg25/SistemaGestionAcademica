/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import modelo.*;
/**
 *
 * @author GEINDEL
 */
public class GestorArchivos {
    private static final String CARPETA_DATOS = "data/";
    private static final String ESTUDIANTES_CSV = CARPETA_DATOS + "estudiantes.csv";
    private static final String PROFESORES_CSV = CARPETA_DATOS + "profesores.csv";
    private static final String CURSOS_CSV = CARPETA_DATOS + "cursos.csv";
    private static final String MATRICULAS_CSV = CARPETA_DATOS + "matriculas.csv"; // ✅ ESTA ES LA CORRECCIÓN


    public static void guardarDatos(List<Estudiante> estudiantes, 
                                  List<Profesor> profesores, 
                                  List<Curso> cursos) {
        new File(CARPETA_DATOS).mkdirs();
        guardarEstudiantes(estudiantes);
        guardarProfesores(profesores);
        guardarCursos(cursos);
        guardarMatriculas(cursos);
    }

    public static void cargarDatos(List<Estudiante> estudiantes,
                                 List<Profesor> profesores,
                                 List<Curso> cursos) {
        cargarEstudiantes(estudiantes);
        cargarProfesores(profesores);
        cargarCursos(cursos, profesores);
        cargarMatriculas(cursos, estudiantes);
    }

    // Métodos para estudiantes (ya implementados)
    private static void guardarEstudiantes(List<Estudiante> estudiantes) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ESTUDIANTES_CSV))) {
            writer.println("id,nombre,email,carrera");
            estudiantes.forEach(e -> writer.println(e.toCSV()));
        } catch (IOException e) {
            System.err.println("Error al guardar estudiantes: " + e.getMessage());
        }
    }

    private static void cargarEstudiantes(List<Estudiante> estudiantes) {
        if (!new File(ESTUDIANTES_CSV).exists()) return;
        
        try (BufferedReader br = new BufferedReader(new FileReader(ESTUDIANTES_CSV))) {
            br.readLine(); // Saltar cabecera
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 4) {
                    estudiantes.add(new Estudiante(datos[0], datos[1], datos[2], datos[3]));
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar estudiantes: " + e.getMessage());
        }
    }

    // Métodos para profesores (ya implementados)
    private static void guardarProfesores(List<Profesor> profesores) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(PROFESORES_CSV))) {
            writer.println("id,nombre,email,especialidad");
            profesores.forEach(p -> writer.println(p.toCSV()));
        } catch (IOException e) {
            System.err.println("Error al guardar profesores: " + e.getMessage());
        }
    }

    private static void cargarProfesores(List<Profesor> profesores) {
        if (!new File(PROFESORES_CSV).exists()) return;
        
        try (BufferedReader br = new BufferedReader(new FileReader(PROFESORES_CSV))) {
            br.readLine();
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 4) {
                    profesores.add(new Profesor(datos[0], datos[1], datos[2], datos[3]));
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar profesores: " + e.getMessage());
        }
    }

    // Métodos para cursos (antes faltantes)
    private static void guardarCursos(List<Curso> cursos) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CURSOS_CSV))) {
            writer.println("codigo,nombre,profesor_id");
            cursos.forEach(c -> writer.println(c.toCSV()));
        } catch (IOException e) {
            System.err.println("Error al guardar cursos: " + e.getMessage());
        }
    }

    private static void cargarCursos(List<Curso> cursos, List<Profesor> profesores) {
        if (!new File(CURSOS_CSV).exists()) return;
        
        try (BufferedReader br = new BufferedReader(new FileReader(CURSOS_CSV))) {
            br.readLine();
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 2) {
                    Curso curso = new Curso(datos[1], datos[0]); // nombre, codigo
                    if (datos.length == 3 && !datos[2].equals("null")) {
                        profesores.stream()
                            .filter(p -> p.getId().equals(datos[2]))
                            .findFirst()
                            .ifPresent(curso::setProfesor);
                    }
                    cursos.add(curso);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar cursos: " + e.getMessage());
        }
    }

    // Métodos para matrículas
    private static void guardarMatriculas(List<Curso> cursos) {
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(MATRICULAS_CSV))) {
            writer.println("curso_codigo,estudiante_id");
            cursos.forEach(curso -> {
                curso.getEstudiantes().forEach(estudiante -> {
                    writer.println(curso.getCodigo() + "," + estudiante.getId());
                });
            });
        } catch (IOException e) {
            System.err.println("Error al guardar matrículas: " + e.getMessage());
        }
    }

    private static void cargarMatriculas(List<Curso> cursos, List<Estudiante> estudiantes) {
        if (!new File(MATRICULAS_CSV).exists()) return;
        
        try (BufferedReader br = new BufferedReader(new FileReader(MATRICULAS_CSV))) {
            br.readLine();
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 2) {
                    String codigoCurso = datos[0];
                    String idEstudiante = datos[1];
                    
                    cursos.stream()
                        .filter(c -> c.getCodigo().equals(codigoCurso))
                        .findFirst()
                        .ifPresent(curso -> {
                            estudiantes.stream()
                                .filter(e -> e.getId().equals(idEstudiante))
                                .findFirst()
                                .ifPresent(curso::agregarEstudiante);
                        });
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar matrículas: " + e.getMessage());
        }
    }
}