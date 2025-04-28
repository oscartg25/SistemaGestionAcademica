/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
/**
 *
 * @author GEINDEL
 */
public class VistaPrincipal extends JFrame {
    private JPanel panelMenu;
    private JPanel panelContenido;
    private JButton btnEstudiantes;
    private JButton btnProfesores;
    private JButton btnCursos;
    private JButton btnMatriculas;
    private CardLayout cardLayout;


    public VistaPrincipal() {
        setTitle("Sistema de Gestión Académica");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configurar layout
        setLayout(new BorderLayout());

        // Panel de menú (izquierda)
        panelMenu = new JPanel();
        panelMenu.setLayout(new GridLayout(5, 1));
        panelMenu.setPreferredSize(new Dimension(150, 600));
        panelMenu.setBackground(new Color(240, 240, 240));

        btnEstudiantes = new JButton("Estudiantes");
        btnProfesores = new JButton("Profesores");
        btnCursos = new JButton("Cursos");
        btnMatriculas = new JButton("Matrículas");

        panelMenu.add(btnEstudiantes);
        panelMenu.add(btnProfesores);
        panelMenu.add(btnCursos);
        panelMenu.add(btnMatriculas);

        add(panelMenu, BorderLayout.WEST);

        // Panel de contenido (derecha)
        panelContenido = new JPanel();
        cardLayout = new CardLayout();
        panelContenido.setLayout(cardLayout);

        add(panelContenido, BorderLayout.CENTER);
    }

    public void mostrarPanel(String nombre) {
        cardLayout.show(panelContenido, nombre);
    }

    // Método genérico para agregar un panel al contenido
    public void agregarPanel(String nombre, JPanel panel) {
        panelContenido.add(panel, nombre);
    }

    // Métodos para agregar listeners
    public void addEstudiantesListener(ActionListener listener) {
        btnEstudiantes.addActionListener(listener);
    }

    public void addProfesoresListener(ActionListener listener) {
        btnProfesores.addActionListener(listener);
    }

    public void addCursosListener(ActionListener listener) {
        btnCursos.addActionListener(listener);
    }

    public void addMatriculasListener(ActionListener listener) {
        btnMatriculas.addActionListener(listener);
    }
}