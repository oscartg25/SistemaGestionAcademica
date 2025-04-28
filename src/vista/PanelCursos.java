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
public class PanelCursos extends JPanel {
    private JTextField txtNombre, txtCodigo;
    private JComboBox<String> comboProfesores;
    private JButton btnAgregar, btnLimpiar;
    private JTable tablaCursos;
    private DefaultTableModel modeloTabla;

    public PanelCursos() {
        setLayout(new BorderLayout());

        // Panel de formulario (arriba)
        JPanel panelFormulario = new JPanel(new GridLayout(3, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelFormulario.add(new JLabel("Nombre del Curso:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Código:"));
        txtCodigo = new JTextField();
        panelFormulario.add(txtCodigo);

        panelFormulario.add(new JLabel("Profesor:"));
        comboProfesores = new JComboBox<>();
        panelFormulario.add(comboProfesores);

        btnAgregar = new JButton("Agregar Curso");
        btnLimpiar = new JButton("Limpiar");

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.add(btnAgregar);
        panelBotones.add(btnLimpiar);

        add(panelFormulario, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);

        // Tabla de cursos (abajo)
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Código");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Profesor");
        modeloTabla.addColumn("Estudiantes");

        tablaCursos = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaCursos);

        add(scrollPane, BorderLayout.SOUTH);
    }

    // Métodos para obtener datos del formulario
    public String getNombreCurso() {
        return txtNombre.getText();
    }

    public String getCodigoCurso() {
        return txtCodigo.getText();
    }

    public String getProfesorSeleccionado() {
        return (String) comboProfesores.getSelectedItem();
    }

    // Métodos para limpiar formulario
    public void limpiarFormulario() {
        txtNombre.setText("");
        txtCodigo.setText("");
        comboProfesores.setSelectedIndex(0);
    }

    // Métodos para manejar la tabla
    public void agregarCursoATabla(String codigo, String nombre, String profesor, int estudiantes) {
        modeloTabla.addRow(new Object[]{codigo, nombre, profesor, estudiantes});
    }

    public void limpiarTabla() {
        modeloTabla.setRowCount(0);
    }

    // Métodos para manejar el combo de profesores
    public void cargarProfesores(String[] profesores) {
        comboProfesores.removeAllItems();
        comboProfesores.addItem("-- Seleccione --");
        for (String profesor : profesores) {
            comboProfesores.addItem(profesor);
        }
    }

    // Métodos para agregar listeners
    public void addAgregarListener(ActionListener listener) {
        btnAgregar.addActionListener(listener);
    }

    public void addLimpiarListener(ActionListener listener) {
        btnLimpiar.addActionListener(listener);
    }
}
