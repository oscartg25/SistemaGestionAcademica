/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.BorderLayout;
import vista.*;
import persistencia.*;
import modelo.*;
import java.awt.event.*;
import javax.swing.*;
/**
 *
 * @author GEINDEL
 */
public class MainApp {
    private VistaPrincipal vista;
    private BaseDatosSimulada baseDatos;
    private PanelEstudiantes panelEstudiantes;
    private PanelProfesores panelProfesores;
    private PanelCursos panelCursos;
    private PanelMatriculas panelMatriculas;

    public MainApp() {
        baseDatos = new BaseDatosSimulada();
        vista = new VistaPrincipal();

        panelEstudiantes = new PanelEstudiantes();
        panelProfesores = new PanelProfesores();
        panelCursos = new PanelCursos();
        panelMatriculas = new PanelMatriculas();

        vista.agregarPanel("Estudiantes", panelEstudiantes);
        vista.agregarPanel("Profesores", panelProfesores);
        vista.agregarPanel("Cursos", panelCursos);
        vista.agregarPanel("Matrículas", panelMatriculas);

        configurarListeners();
        cargarDatosIniciales();

        vista.setVisible(true);
    }

    private void configurarListeners() {
    // Listeners para navegación
    vista.addEstudiantesListener(e -> {
        vista.mostrarPanel("Estudiantes");
        actualizarTablaEstudiantes();
    });

    vista.addProfesoresListener(e -> {
        vista.mostrarPanel("Profesores");
        actualizarTablaProfesores();
    });

    vista.addCursosListener(e -> {
        vista.mostrarPanel("Cursos");
        actualizarComboProfesores();
        actualizarTablaCursos();
    });

    vista.addMatriculasListener(e -> {
        vista.mostrarPanel("Matrículas");
        actualizarCombosMatriculas();
    });

    // Listeners para estudiantes
    panelEstudiantes.addAgregarListener(e -> agregarEstudiante());
    panelEstudiantes.addLimpiarListener(e -> panelEstudiantes.limpiarFormulario());

    // ✅ Aquí agregas el listener del botón Guardar Estudiantes
    panelEstudiantes.addGuardarListener(e -> {
        baseDatos.guardarDatos();
        mostrarExito("Datos de estudiantes guardados correctamente");
    });

    // Listeners para profesores
    panelProfesores.addAgregarListener(e -> agregarProfesor());
    panelProfesores.addLimpiarListener(e -> panelProfesores.limpiarFormulario());

    // ✅ Aquí agregas el listener del botón Guardar Profesores
    panelProfesores.addGuardarListener(e -> {
        baseDatos.guardarDatos();
        mostrarExito("Datos de profesores guardados correctamente");
    });

    // Listeners para cursos y matrículas
    panelCursos.addAgregarListener(e -> agregarCurso());
    panelCursos.addLimpiarListener(e -> panelCursos.limpiarFormulario());

    panelMatriculas.addMatricularListener(e -> matricularEstudiante());
    panelMatriculas.addDesmatricularListener(e -> desmatricularEstudiante());
    panelMatriculas.addCursoSeleccionadoListener(e -> actualizarEstudiantesMatriculados());

    vista.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            baseDatos.guardarDatos();
        }
    });
}
    
    private void cargarDatosIniciales() {
        JDialog loadingDialog = new JDialog(vista, "Cargando datos", true);
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        loadingDialog.add(new JLabel("Cargando datos iniciales..."), BorderLayout.NORTH);
        loadingDialog.add(progressBar, BorderLayout.CENTER);
        loadingDialog.setSize(300, 100);
        loadingDialog.setLocationRelativeTo(vista);

        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                actualizarTablaEstudiantes();
                actualizarTablaProfesores();
                actualizarTablaCursos();
                return null;
            }

            @Override
            protected void done() {
                loadingDialog.dispose();
                actualizarCombosMatriculas();
                actualizarComboProfesores();
            }
        }.execute();

        loadingDialog.setVisible(true);
    }

    private void actualizarTablaEstudiantes() {
        panelEstudiantes.limpiarTabla();
        for (Estudiante e : baseDatos.getEstudiantes()) {
            panelEstudiantes.agregarEstudianteATabla(e.getId(), e.getNombre(), e.getEmail(), e.getCarrera());
        }
    }

    private void agregarEstudiante() {
        try {
            String id = panelEstudiantes.getId();
            String nombre = panelEstudiantes.getNombre();
            String email = panelEstudiantes.getEmail();
            String carrera = panelEstudiantes.getCarrera();

            if (validarCamposVacios(id, nombre, email, carrera)) {
                mostrarError("Todos los campos son obligatorios");
                return;
            }

            if (baseDatos.buscarEstudiante(id) != null) {
                mostrarError("Ya existe un estudiante con este ID");
                return;
            }

            Estudiante nuevoEstudiante = new Estudiante(id, nombre, email, carrera);
            baseDatos.agregarEstudiante(nuevoEstudiante);
            panelEstudiantes.agregarEstudianteATabla(id, nombre, email, carrera);
            panelEstudiantes.limpiarFormulario();

            actualizarCombosMatriculas();
            mostrarExito("Estudiante agregado con éxito");
        } catch (Exception e) {
            mostrarError("Error al agregar estudiante: " + e.getMessage());
        }
    }

    private void actualizarTablaProfesores() {
        panelProfesores.limpiarTabla();
        for (Profesor p : baseDatos.getProfesores()) {
            panelProfesores.agregarProfesorATabla(p.getId(), p.getNombre(), p.getEmail(), p.getEspecialidad());
        }
    }

    private void agregarProfesor() {
        try {
            String id = panelProfesores.getId();
            String nombre = panelProfesores.getNombre();
            String email = panelProfesores.getEmail();
            String especialidad = panelProfesores.getEspecialidad();

            if (validarCamposVacios(id, nombre, email, especialidad)) {
                mostrarError("Todos los campos son obligatorios");
                return;
            }

            if (baseDatos.existeProfesor(id)) {
                mostrarError("Ya existe un profesor con este ID");
                return;
            }

            Profesor nuevoProfesor = new Profesor(id, nombre, email, especialidad);
            baseDatos.agregarProfesor(nuevoProfesor);
            panelProfesores.agregarProfesorATabla(id, nombre, email, especialidad);
            panelProfesores.limpiarFormulario();

            actualizarComboProfesores();
            actualizarCombosMatriculas();
            mostrarExito("Profesor agregado con éxito");
        } catch (Exception e) {
            mostrarError("Error al agregar profesor: " + e.getMessage());
        }
    }

    private void actualizarTablaCursos() {
        panelCursos.limpiarTabla();
        for (Curso c : baseDatos.getCursos()) {
            String profesor = c.getProfesor() != null ? c.getProfesor().getNombre() : "No asignado";
            panelCursos.agregarCursoATabla(c.getCodigo(), c.getNombre(), profesor, c.getEstudiantes().size());
        }
    }

    private void actualizarComboProfesores() {
        String[] nombresProfesores = baseDatos.getProfesores().stream()
                .map(Profesor::getNombre)
                .toArray(String[]::new);
        panelCursos.cargarProfesores(nombresProfesores);
    }

    private void agregarCurso() {
        try {
            String nombre = panelCursos.getNombreCurso();
            String codigo = panelCursos.getCodigoCurso();
            String profesorSeleccionado = panelCursos.getProfesorSeleccionado();

            if (nombre.isEmpty() || codigo.isEmpty()) {
                mostrarError("Nombre y código son obligatorios");
                return;
            }

            if (baseDatos.existeCurso(codigo)) {
                mostrarError("Ya existe un curso con este código");
                return;
            }

            if (profesorSeleccionado == null || profesorSeleccionado.equals("-- Seleccione --")) {
                mostrarError("Debe seleccionar un profesor");
                return;
            }

            Profesor profesor = buscarProfesorPorNombre(profesorSeleccionado);
            if (profesor == null) {
                mostrarError("Profesor no encontrado");
                return;
            }

            Curso nuevoCurso = new Curso(nombre, codigo);
            nuevoCurso.setProfesor(profesor);
            baseDatos.agregarCurso(nuevoCurso);

            panelCursos.agregarCursoATabla(codigo, nombre, profesorSeleccionado, 0);
            panelCursos.limpiarFormulario();

            actualizarCombosMatriculas();
            mostrarExito("Curso agregado con éxito");
        } catch (Exception e) {
            mostrarError("Error al agregar curso: " + e.getMessage());
        }
    }

    private void actualizarCombosMatriculas() {
        String[] nombresCursos = baseDatos.getCursos().stream()
                .map(Curso::getNombre)
                .toArray(String[]::new);
        panelMatriculas.cargarCursos(nombresCursos);

        String[] nombresEstudiantes = baseDatos.getEstudiantes().stream()
                .map(Estudiante::getNombre)
                .toArray(String[]::new);
        panelMatriculas.cargarEstudiantes(nombresEstudiantes);
    }

    private void actualizarEstudiantesMatriculados() {
        String nombreCurso = panelMatriculas.getCursoSeleccionado();
        if (nombreCurso == null || nombreCurso.equals("-- Seleccione un curso --")) {
            return;
        }
        Curso curso = buscarCursoPorNombre(nombreCurso);
        if (curso != null) {
            Object[][] datos = curso.getEstudiantes().stream()
                    .map(e -> new Object[]{e.getId(), e.getNombre(), e.getEmail(), e.getCarrera()})
                    .toArray(Object[][]::new);
            panelMatriculas.actualizarTablaMatriculados(datos);
        }
    }

    private void matricularEstudiante() {
        try {
            String nombreCurso = panelMatriculas.getCursoSeleccionado();
            String nombreEstudiante = panelMatriculas.getEstudianteSeleccionado();

            if (validarSeleccionMatricula(nombreCurso, nombreEstudiante)) {
                return;
            }

            Curso curso = buscarCursoPorNombre(nombreCurso);
            Estudiante estudiante = buscarEstudiantePorNombre(nombreEstudiante);

            if (curso != null && estudiante != null) {
                if (curso.getEstudiantes().contains(estudiante)) {
                    mostrarError("El estudiante ya está matriculado en este curso");
                    return;
                }

                curso.agregarEstudiante(estudiante);
                actualizarEstudiantesMatriculados();
                actualizarTablaCursos();
                mostrarExito("Matrícula exitosa");
            }
        } catch (Exception e) {
            mostrarError("Error al matricular: " + e.getMessage());
        }
    }

    private void desmatricularEstudiante() {
        try {
            if (!panelMatriculas.hayEstudianteSeleccionado()) {
                mostrarError("Debe seleccionar un estudiante de la tabla");
                return;
            }

            String idEstudiante = panelMatriculas.getIdEstudianteSeleccionado();
            String nombreCurso = panelMatriculas.getCursoSeleccionado();

            if (nombreCurso == null || nombreCurso.equals("-- Seleccione un curso --")) {
                mostrarError("Debe seleccionar un curso primero");
                return;
            }

            Curso curso = buscarCursoPorNombre(nombreCurso);
            if (curso != null) {
                curso.quitarEstudiante(idEstudiante);
                actualizarEstudiantesMatriculados();
                actualizarTablaCursos();
                mostrarExito("Estudiante desmatriculado con éxito");
            }
        } catch (Exception e) {
            mostrarError("Error al desmatricular: " + e.getMessage());
        }
    }

    private boolean validarCamposVacios(String... campos) {
        for (String campo : campos) {
            if (campo == null || campo.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private boolean validarSeleccionMatricula(String curso, String estudiante) {
        if (curso == null || estudiante == null ||
            curso.equals("-- Seleccione un curso --") ||
            estudiante.equals("-- Seleccione un estudiante --")) {
            mostrarError("Debe seleccionar un curso y un estudiante");
            return true;
        }
        return false;
    }

    private Profesor buscarProfesorPorNombre(String nombre) {
        return baseDatos.getProfesores().stream()
                .filter(p -> p.getNombre().equals(nombre))
                .findFirst()
                .orElse(null);
    }

    private Curso buscarCursoPorNombre(String nombre) {
        return baseDatos.getCursos().stream()
                .filter(c -> c.getNombre().equals(nombre))
                .findFirst()
                .orElse(null);
    }

    private Estudiante buscarEstudiantePorNombre(String nombre) {
        return baseDatos.getEstudiantes().stream()
                .filter(e -> e.getNombre().equals(nombre))
                .findFirst()
                .orElse(null);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainApp());
    }
  
}