package com.consultorio.dao;

import com.consultorio.config.Conexion;
import com.consultorio.definition.IPacienteDAO;
import com.consultorio.model.Consulta;
import com.consultorio.model.Genero;
import com.consultorio.model.ObraSocial;
import com.consultorio.model.Paciente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PacienteDAOImpl implements IPacienteDAO {

    private static final String SELECT_PACIENTES = "SELECT p.apellido,p.nombre,p.dni,p.edad,p.telefono,p.id_paciente FROM paciente p";
    private static final String SELECT_PACIENTE = "SELECT * FROM paciente WHERE id_paciente = ? ";
    private static final String SELECT_GENEROS = "SELECT * FROM genero";
    private static final String SELECT_OBRASOCIALES = "SELECT * FROM obra_social";
    private static final String UPDATE_PACIENTE = "UPDATE paciente SET apellido = ?, nombre = ?, dni= ?, fecha_nacimiento = ?, edad = ?, telefono =?,domicilio =?, lugar_trabajo =?, antecedentes=?, alergia=?,"
            + "responsable=?, id_genero=?,id_obraSocial=? WHERE id_paciente = ?";
    private static final String INSERT_PACIENTE = "INSERT INTO paciente VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String SELECT_BY_DNI = "SELECT c.fecha,c.descripcion,c.observacion,p.apellido,p.nombre,"
            + " p.id_paciente,p.dni,p.edad FROM paciente p LEFT JOIN consultas c  ON p.id_paciente = c.id_paciente WHERE  p.dni = ?";

    private static final String COUNT_FOR_GENERO = "SELECT count(*) FROM paciente p,genero g GROUP BY p.id_genero";
   

    
    
    @Override
    public int calculateAge(String fechaNacimiento) {

        String query = "SELECT TIMESTAMPDIFF(YEAR,?,CURDATE())";
        int edad = 0;

        try (Connection connection = Conexion.getConexion();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, fechaNacimiento);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                edad = resultSet.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return edad;
    }

    
    
    @Override
    public Paciente findByDni(String dni) {

        Paciente paciente = null;
        Consulta consulta;

        try (Connection connection = Conexion.getConexion();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_DNI)) {

            preparedStatement.setString(1, dni);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                if (resultSet.isFirst()) {
                    paciente = new Paciente();
                    paciente.setApellido(resultSet.getString(4));
                    paciente.setNombre(resultSet.getString(5));
                    paciente.setId(resultSet.getInt(6));
                    paciente.setDni(resultSet.getString(7));
                    paciente.setEdad(resultSet.getInt(8));
                }

                consulta = new Consulta();
                consulta.setFecha(resultSet.getString(1));
                consulta.setMotivo(resultSet.getString(2));
                consulta.setObservacion(resultSet.getString(3));
                paciente.consultarRealizadas(consulta);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paciente;
    }

    
    
    @Override
    public Paciente findById(int id) {

        Paciente paciente = null;
        try (Connection connection = Conexion.getConexion();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PACIENTE)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                paciente = new Paciente();

                paciente.setNombre(resultSet.getString("nombre"));
                paciente.setApellido(resultSet.getString("apellido"));
                paciente.setDni(resultSet.getString("dni"));
                paciente.setFechaNacimiento(resultSet.getString("fecha_nacimiento"));
                paciente.setTelefono(resultSet.getString("telefono"));
                paciente.setDomicilio(resultSet.getString("domicilio"));
                paciente.setDireccionTrabajo(resultSet.getString("lugar_trabajo"));
                paciente.setAntecedentes(resultSet.getString("antecedentes"));
                paciente.setAlergia(resultSet.getString("alergia"));
                paciente.setResponsable(resultSet.getString("responsable"));
                paciente.setIdGenero(resultSet.getInt("id_genero"));
                paciente.setObraSocial(resultSet.getInt("id_obraSocial"));
                paciente.setId(resultSet.getInt("id_paciente"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paciente;
    }


    @Override
    public void updateAges() throws SQLException {

        String select_edades = "SELECT id_paciente,fecha_nacimiento FROM paciente";
        String update_edades = "UPDATE paciente SET edad = (SELECT TIMESTAMPDIFF(YEAR,?,CURDATE())) WHERE id_paciente = ?";

        List<Paciente> listaEdades = new ArrayList();

        Paciente paciente;
        Connection connection = null;
        PreparedStatement psSelect = null;
        PreparedStatement psUpdate = null;
        ResultSet resultSet = null;

        try {
            connection = Conexion.getConexion();
            connection.setAutoCommit(false);

            psSelect = connection.prepareStatement(select_edades);
            resultSet = psSelect.executeQuery();

            while (resultSet.next()) {
                paciente = new Paciente();
                paciente.setId(resultSet.getInt("id_paciente"));
                paciente.setFechaNacimiento(resultSet.getString("fecha_nacimiento"));
                listaEdades.add(paciente);
            }

            // Actualizar edad de cada uno de los pacientes
            for (int i = 0; i < listaEdades.size(); i++) {
                psUpdate = connection.prepareStatement(update_edades);
                psUpdate.setString(1, listaEdades.get(i).getFechaNacimiento());
                psUpdate.setInt(2, listaEdades.get(i).getId());
                psUpdate.executeUpdate();
            }

            connection.commit();

        } catch (SQLException e) {
            connection.rollback();
        } finally {
            resultSet.close();
            psSelect.close();
            psUpdate.close();
            connection.close();
        }

    }


    @Override
    public ArrayList<Paciente> listPacientes() {

        ArrayList<Paciente> listaPacientes = new ArrayList<>();
        Paciente paciente;

        try (Connection connection = Conexion.getConexion();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PACIENTES)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                paciente = new Paciente();
                paciente.setApellido(resultSet.getString(1));
                paciente.setNombre(resultSet.getString(2));
                paciente.setDni(resultSet.getString(3));
                paciente.setEdad(resultSet.getInt(4));
                paciente.setTelefono(resultSet.getString(5));
                paciente.setId(resultSet.getInt(6));
                listaPacientes.add(paciente);
            }

        } catch (SQLException e) {

        }

        return listaPacientes;

    }

    @Override
    public int[] countForGenero() {

        int[] generos = new int[2];
        int contador = 0;

        try (Connection connection = Conexion.getConexion();
                PreparedStatement preparedStatement = connection.prepareStatement(COUNT_FOR_GENERO);
                ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                generos[contador] = resultSet.getInt(1);
                contador++;
            }

        } catch (SQLException sqlE) {

        }

        return generos;
    }

  

    @Override
    public ArrayList<Genero> generos() {

        ArrayList<Genero> listaGeneros = new ArrayList<>();

        try (Connection connection = Conexion.getConexion();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_GENEROS);
                ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                listaGeneros.add(new Genero(resultSet.getInt("id_genero"), resultSet.getString("descripcion")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaGeneros;
    }

    @Override
    public ArrayList<ObraSocial> obrasSociales() {
        ArrayList<ObraSocial> listaObrasSociales = new ArrayList<>();

        try (Connection connection = Conexion.getConexion();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_OBRASOCIALES);
                ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                listaObrasSociales.add(new ObraSocial(resultSet.getInt("id_obraSocial"), resultSet.getString("descripcion")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaObrasSociales;
    }

    @Override
    public void update(Paciente pcnt) {

        try (Connection connection = Conexion.getConexion();
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PACIENTE)) {

            preparedStatement.setString(1, pcnt.getApellido());
            preparedStatement.setString(2, pcnt.getNombre());
            preparedStatement.setString(3, pcnt.getDni());
            preparedStatement.setString(4, pcnt.getFechaNacimiento());
            preparedStatement.setInt(5, pcnt.getEdad());
            preparedStatement.setString(6, pcnt.getTelefono());
            preparedStatement.setString(7, pcnt.getDomicilio());
            preparedStatement.setString(8, pcnt.getDireccionTrabajo());
            preparedStatement.setString(9, pcnt.getAntecedentes());
            preparedStatement.setString(10, pcnt.getAlergia());
            preparedStatement.setString(11, pcnt.getResponsable());
            preparedStatement.setInt(12, pcnt.getIdGenero());
            preparedStatement.setInt(13, pcnt.getObraSocial());
            preparedStatement.setInt(14, pcnt.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(Paciente pcnt) {

        try (Connection connection = Conexion.getConexion();
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PACIENTE)) {

            preparedStatement.setString(1, null);
            preparedStatement.setString(2, pcnt.getNombre());
            preparedStatement.setString(3, pcnt.getApellido());
            preparedStatement.setString(4, pcnt.getDni());
            preparedStatement.setInt(5, pcnt.getEdad());
            preparedStatement.setString(6, pcnt.getFechaNacimiento());
            preparedStatement.setString(7, pcnt.getTelefono());
            preparedStatement.setString(8, pcnt.getDireccionTrabajo());
            preparedStatement.setInt(9, pcnt.getIdGenero());
            preparedStatement.setString(10, pcnt.getDomicilio());
            preparedStatement.setString(11, pcnt.getAntecedentes());
            preparedStatement.setString(12, pcnt.getAlergia());
            preparedStatement.setString(13, pcnt.getResponsable());
            preparedStatement.setInt(14, pcnt.getObraSocial());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
