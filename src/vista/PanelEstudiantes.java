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
public class PanelEstudiantes extends JPanel {
    private JTextField txtId, txtNombre, txtEmail, txtCarrera;
    private JButton btnAgregar, btnLimpiar, btnGuardar;
    private JTable tablaEstudiantes;
    private DefaultTableModel modeloTabla;

    public PanelEstudiantes() {
        setLayout(new BorderLayout());
        
        // Panel de formulario (arriba)
        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelFormulario.add(new JLabel("ID:"));
        txtId = new JTextField();
        panelFormulario.add(txtId);

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panelFormulario.add(txtEmail);

        panelFormulario.add(new JLabel("Carrera:"));
        txtCarrera = new JTextField();
        panelFormulario.add(txtCarrera);

        // Botones: Agregar, Limpiar, Guardar
        btnAgregar = new JButton("Agregar Estudiante");
        btnLimpiar = new JButton("Limpiar");
        btnGuardar = new JButton("Guardar Estudiantes");

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.add(btnAgregar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnGuardar);

        add(panelFormulario, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);

        // Tabla de estudiantes (abajo)
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Email");
        modeloTabla.addColumn("Carrera");

        tablaEstudiantes = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaEstudiantes);

        add(scrollPane, BorderLayout.SOUTH);
    }

    // Métodos para obtener datos del formulario
    public String getId() { 
        return txtId.getText(); 
    }
    public String getNombre() { 
        return txtNombre.getText(); 
    }
    public String getEmail() { 
        return txtEmail.getText(); 
    }
    public String getCarrera() { 
        return txtCarrera.getText(); 
    }

    // Métodos para limpiar formulario y tabla
    public void limpiarFormulario() {
        txtId.setText("");
        txtNombre.setText("");
        txtEmail.setText("");
        txtCarrera.setText("");
    }
    
    public void agregarEstudianteATabla(String id, String nombre, String email, String carrera) {
        modeloTabla.addRow(new Object[]{id, nombre, email, carrera});
    }
    
    public void limpiarTabla() {
        modeloTabla.setRowCount(0);
    }
    
    // Métodos para agregar listeners
    public void addAgregarListener(ActionListener listener) {
        btnAgregar.addActionListener(listener);
    }
    
    public void addLimpiarListener(ActionListener listener) {
        btnLimpiar.addActionListener(listener);
    }
    
    public void addGuardarListener(ActionListener listener) {
        btnGuardar.addActionListener(listener);
    }
}