/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author GEINDEL
 */
public class Estudiante extends Persona implements Registrable {
    private String carrera;
    
    public Estudiante(String id, String nombre, String email, String carrera) {
        super(id, nombre, email);
        this.carrera = carrera;
    }
    public String getCarrera() {
        return carrera;
    }
    public String toCSV() {
        return String.join(",", id, nombre, email, carrera);
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    @Override
    public void registrar() {
        System.out.println("Estudiante registrado: " + this.getNombre());
    }

    @Override
    public String toString() {
        return super.toString() + ", Carrera: " + carrera;
    }
}
