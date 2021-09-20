package com.consultorio.controller;

import com.consultorio.dao.PacienteDAOImpl;
import com.consultorio.definition.IPacienteDAO;
import com.consultorio.model.Paciente;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "PacienteController", urlPatterns = {"/paciente.do"})
public class PacienteController extends HttpServlet {

    private IPacienteDAO pacienteDao;

    @Override
    public void init() {
        pacienteDao = new PacienteDAOImpl();

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String menu = request.getParameter("menu");
        String opcion = request.getParameter("opcion");

        if (menu.equals("pacientes")) {
            request.setAttribute("cantidad_por_genero", pacienteDao.countForGenero());
            request.setAttribute("pacientes", pacienteDao.listPacientes());
            request.getRequestDispatcher("/Pacientes.jsp").forward(request, response);
        } else if (menu.equals("detalle")) {

            switch (opcion) {
                case "actualizar":
                    Paciente p = new Paciente();

                    p.setId(Integer.parseInt(request.getParameter("id_paciente")));
                    p.setNombre(request.getParameter("nombre"));
                    p.setApellido(request.getParameter("apellido"));
                    p.setDni(request.getParameter("dni"));
                    p.setFechaNacimiento(request.getParameter("fecha_n"));
                    p.setIdGenero(Integer.parseInt(request.getParameter("sexo")));
                    p.setObraSocial(Integer.parseInt(request.getParameter("obra_social")));
                    p.setDomicilio(request.getParameter("domicilio"));
                    p.setTelefono(request.getParameter("telefono"));
                    p.setDireccionTrabajo(request.getParameter("lugar_trabajo"));
                    p.setAntecedentes(request.getParameter("antecedentes"));
                    p.setAlergia(request.getParameter("alergia"));
                    p.setResponsable(request.getParameter("responsable"));

                    int edad = pacienteDao.calculateAge(request.getParameter("fecha_n"));
                    p.setEdad(edad);
                    pacienteDao.update(p);
                    request.getRequestDispatcher("paciente.do?menu=pacientes").forward(request, response);
                    break;

                case "ver":
                    int id = Integer.parseInt(request.getParameter("id_paciente"));

                    Paciente paciente = pacienteDao.findById(id);
                    request.setAttribute("paciente", paciente);
                    request.setAttribute("generos", pacienteDao.generos());
                    request.setAttribute("obrasSociales", pacienteDao.obrasSociales());
                    request.getRequestDispatcher("/Detalles.jsp").forward(request, response);
                    break;

            }
        } else if (menu.equals("agregar")) {

            switch (opcion) {

                case "ficha":
                    request.setAttribute("generos", pacienteDao.generos());
                    request.setAttribute("obrasSociales", pacienteDao.obrasSociales());
                    request.getRequestDispatcher("/AgregarPaciente.jsp").forward(request, response);
                    break;

                case "guardar-cambios":
                    Paciente p = new Paciente();
             
                    p.setNombre(request.getParameter("nombre"));
                    p.setApellido(request.getParameter("apellido"));
                    p.setDni(request.getParameter("dni"));
                    p.setFechaNacimiento(request.getParameter("fecha_nacimiento"));
                    p.setIdGenero(Integer.parseInt(request.getParameter("genero")));
                    p.setObraSocial(Integer.parseInt(request.getParameter("obra_social")));
                    p.setDomicilio(request.getParameter("domicilio"));
                    p.setTelefono(request.getParameter("telefono"));
                    p.setDireccionTrabajo(request.getParameter("lugar_trabajo"));
                    p.setAntecedentes(request.getParameter("antecedentes"));
                    p.setAlergia(request.getParameter("alergia"));
                    p.setResponsable(request.getParameter("responsable"));
                    int edad = pacienteDao.calculateAge(request.getParameter("fecha_nacimiento"));
                    p.setEdad(edad);
                    
                    pacienteDao.add(p);

                    request.getRequestDispatcher("/paciente.do?menu=agregar&opcion=ficha").forward(request, response);

                    break;
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
