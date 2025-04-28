/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author GEINDEL
 */
public class PanelMatriculas extends JPanel {
    private JComboBox<String> comboCursos;
    private JComboBox<String> comboEstudiantes;
    private JButton btnMatricular;
    private JButton btnDesmatricular;
    private JTable tablaMatriculados;
    private DefaultTableModel modeloTabla;

    public PanelMatriculas() {
        setLayout(new BorderLayout());

        // Panel superior con controles
        JPanel panelControles = new JPanel(new GridLayout(3, 2, 5, 5));
        panelControles.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelControles.add(new JLabel("Curso:"));
        comboCursos = new JComboBox<>();
        comboCursos.addItem("-- Seleccione un curso --");
        panelControles.add(comboCursos);

        panelControles.add(new JLabel("Estudiante:"));
        comboEstudiantes = new JComboBox<>();
        comboEstudiantes.addItem("-- Seleccione un estudiante --");
        panelControles.add(comboEstudiantes);

        btnMatricular = new JButton("Matricular");
        btnDesmatricular = new JButton("Desmatricular");
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.add(btnMatricular);
        panelBotones.add(btnDesmatricular);

        add(panelControles, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);

        // Tabla de estudiantes matriculados
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Email");
        modeloTabla.addColumn("Carrera");

        tablaMatriculados = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaMatriculados);
        add(scrollPane, BorderLayout.SOUTH);
    }

    // Métodos para cargar comboboxes
    public void cargarCursos(String[] cursos) {
        comboCursos.removeAllItems();
        comboCursos.addItem("-- Seleccione un curso --");
        for (String curso : cursos) {
            comboCursos.addItem(curso);
        }
    }

    public void cargarEstudiantes(String[] estudiantes) {
        comboEstudiantes.removeAllItems();
        comboEstudiantes.addItem("-- Seleccione un estudiante --");
        for (String estudiante : estudiantes) {
            comboEstudiantes.addItem(estudiante);
        }
    }

    // Métodos para actualizar la tabla
    public void actualizarTablaMatriculados(Object[][] datos) {
        modeloTabla.setRowCount(0);
        for (Object[] fila : datos) {
            modeloTabla.addRow(fila);
        }
    }

    // Métodos para obtener selecciones
   public String getCursoSeleccionado() {
        return (String) comboCursos.getSelectedItem();
}

    public String getEstudianteSeleccionado() {
        return (String) comboEstudiantes.getSelectedItem();
    }

    // Métodos para acceso a la tabla
    public JTable getTablaMatriculados() {
        return tablaMatriculados;
    }
    
    public int getFilaEstudianteSeleccionado() {
        return tablaMatriculados.getSelectedRow();
    }
    
    public String getIdEstudianteSeleccionado() {
        int fila = getFilaEstudianteSeleccionado();
        return (fila != -1) ? (String) tablaMatriculados.getValueAt(fila, 0) : null;
    }
    
    public boolean hayEstudianteSeleccionado() {
        return getFilaEstudianteSeleccionado() != -1;
    }

    // Métodos para agregar listeners
    public void addMatricularListener(ActionListener listener) {
        btnMatricular.addActionListener(listener);
    }

    public void addDesmatricularListener(ActionListener listener) {
        btnDesmatricular.addActionListener(listener);
    }

    public void addCursoSeleccionadoListener(ActionListener listener) {
        comboCursos.addActionListener(listener);
    }
}