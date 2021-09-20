package com.consultorio.controller;

import com.consultorio.dao.ConsultaDAOImpl;
import com.consultorio.dao.PacienteDAOImpl;
import com.consultorio.model.Consulta;
import com.consultorio.model.Paciente;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ConsultaController", urlPatterns = {"/consulta.do"})
public class ConsultaController extends HttpServlet {

    private PacienteDAOImpl pacienteDao;
    private ConsultaDAOImpl consultaDao;

    Date fecha = new Date();
    SimpleDateFormat formato = new SimpleDateFormat("dd-MM-YYYY");
    String formatoLatino = formato.format(fecha);

    @Override
    public void init() {
        pacienteDao = new PacienteDAOImpl();
        consultaDao = new ConsultaDAOImpl();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String menu = request.getParameter("menu");
        String opcion = request.getParameter("opcion");

        if (menu.equals("consulta-medica")) {

            switch (opcion) {

                case "buscar":

                    String dni = request.getParameter("dni_paciente");

                    Paciente paciente = pacienteDao.findByDni(dni);
                    request.setAttribute("paciente", paciente);
                    request.getRequestDispatcher("/index.jsp").forward(request, response);

                    break;

                case "detalle-paciente":

                    int id = Integer.parseInt(request.getParameter("id_paciente"));
                    request.setAttribute("id_paciente", id);
                    request.getRequestDispatcher("/paciente.do?menu=detalle&opcion=ver").forward(request, response);
                    break;

                case "registrar":
                    int id_paciente = Integer.parseInt(request.getParameter("idPaciente"));
                    String motivoConsulta = request.getParameter("motivo");
                    String receta = request.getParameter("receta");
                    String observacion = request.getParameter("observacion");

                    Consulta consulta = new Consulta();
                    consulta.setMotivo(motivoConsulta);
                    consulta.setObservacion(observacion);
                    consulta.setReceta(receta);
                    consulta.setIdPaciente(id_paciente);
                    consulta.setFecha(formatoLatino);

                    consultaDao.guardar(consulta);
                    request.getRequestDispatcher("/consulta.do?menu=consulta-medica&opcion=default").forward(request, response);

                    break;

                default:
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
            }

        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
