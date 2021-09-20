
package com.consultorio.definition;


import com.consultorio.model.Genero;
import com.consultorio.model.ObraSocial;
import com.consultorio.model.Paciente;
import java.sql.SQLException;
import java.util.ArrayList;


public interface IPacienteDAO {


    public ArrayList<Paciente> listPacientes();
    public Paciente findByDni(String dni);
    public Paciente findById(int id);
    public int[] countForGenero();
    public int calculateAge(String fechaNacimiento);   
    public ArrayList<Genero> generos();
    public ArrayList<ObraSocial> obrasSociales();
    public void update(Paciente paciente);
    public void add(Paciente paciente);
    public void updateAges() throws SQLException;

}
