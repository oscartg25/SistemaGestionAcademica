/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author GEINDEL
 */
public class Profesor extends Persona implements Registrable {
    private String especialidad;
    
    public Profesor(String id, String nombre, String email, String especialidad) {
        super(id, nombre, email);
        this.especialidad = especialidad;
    }

    public String getEspecialidad() {
        return especialidad;
    }
    public String toCSV() {
        return String.join(",", id, nombre, email);
    }
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    @Override
    public void registrar() {
        System.out.println("Profesor registrado: " + this.getNombre());
    }
        @Override
    public String toString() {
        return super.toString() + ", Especialidad: " + especialidad;
    }
}