
package com.consultorio.dao;


import com.consultorio.definition.IConsultaDAO;
import com.consultorio.config.Conexion;
import com.consultorio.model.Consulta;
import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.SQLException;

public class ConsultaDAOImpl implements IConsultaDAO{
     
    private final static String INSERT_CONSULTA = "INSERT INTO consultas(descripcion,fecha,observacion,receta,id_paciente)"
                                                  + " values (?,?,?,?,?)";
           
    
    @Override
    public void guardar(Consulta consulta){
       
      try (Connection connection = Conexion.getConexion();
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CONSULTA)) {
             
            preparedStatement.setString(1, consulta.getMotivo());
            preparedStatement.setString(2, consulta.getFecha());
            preparedStatement.setString(3,consulta.getObservacion());
            preparedStatement.setString(4, consulta.getReceta());
            preparedStatement.setInt(5, consulta.getIdPaciente());           
            preparedStatement.executeUpdate();

        } catch (SQLException e) {

        }         
    }      
}
