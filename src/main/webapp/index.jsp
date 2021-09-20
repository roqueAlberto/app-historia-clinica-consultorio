

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta
            name="viewport"
            content="width=device-width, initial-scale=1, shrink-to-fit=no"
            />
        <meta name="description" content="" />
        <meta name="author" content="" />

        <title>Consulta medica</title>

        <!-- Custom fonts for this template-->
        <link
            href="vendor/fontawesome-free/css/all.min.css"
            rel="stylesheet"
            type="text/css"
            />
        <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet"
            />

        <!-- Custom styles for this template-->
        <link href="css/sb-admin-2.min.css" rel="stylesheet" />

        <link
            href="vendor/datatables/dataTables.bootstrap4.min.css"
            rel="stylesheet"
            />
    </head>

    <body id="page-top" onload="realizar_focus()">
        <!-- Page Wrapper -->
        <div id="wrapper">
            <!-- Sidebar -->
            <ul
                class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion"
                id="accordionSidebar"
                >
                <!-- Sidebar - Brand -->
                <a class="sidebar-brand d-flex align-items-center justify-content-center" href="index.jsp">
                    <div class="sidebar-brand-icon">
                        <i class="fas fa-user-md"></i>
                    </div>
                    <div class="sidebar-brand-text mx-3">Dr. Billordo</div> 
                </a>

                <!-- Divider -->
                <hr class="sidebar-divider my-0" />
                <!-- Divider -->
                <hr class="sidebar-divider" />

                <!-- Nav Item - Pages Collapse Menu -->
                <li class="nav-item active">
                    <a class="nav-link" href="consulta.do?menu=consulta-medica&opcion=default" aria-expanded="true" aria-controls="collapseTwo">
                        <i class="fas fa-stethoscope"></i>
                        <span>Consulta medica</span>
                    </a>
                </li>

                <!-- Nav Item - Utilities Collapse Menu -->
                <li class="nav-item">
                    <a class="nav-link collapsed" href="paciente.do?menu=pacientes" aria-expanded="true"  aria-controls="collapseUtilities" >
                        <i class="fas fa-id-card"></i>
                        <span>Pacientes</span>
                    </a>
                </li>

                <!-- Nav Item - Pages Collapse Menu -->
                <li class="nav-item">
                    <a class="nav-link collapsed" href="paciente.do?menu=agregar&opcion=ficha" aria-expanded="true" aria-controls="collapsePages">
                        <i class="fas fa-user-plus"></i>
                        <span>Nuevo Paciente</span>
                    </a>
                </li>

                <!-- Divider -->
                <hr class="sidebar-divider d-none d-md-block" />

                <!-- Sidebar Toggler (Sidebar) -->
                <div class="text-center d-none d-md-inline">
                    <button class="rounded-circle border-0" id="sidebarToggle"></button>
                </div>
            </ul>
            <!-- End of Sidebar -->

            <!-- Content Wrapper -->
            <div id="content-wrapper" class="d-flex flex-column">
                <!-- Main Content -->
                <div id="content">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-lg-12">
                                <!-- Dropdown Card Example -->
                                <div class="card shadow mb-3">
                                    <!-- Card Header - Dropdown -->
                                    <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                                        <h6 class="m-0 font-weight-bold text-primary">
                                            Describir consulta medica
                                        </h6>
                                        <div class="dropdown no-arrow">
                                            <a class="dropdown-toggle" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true"  aria-expanded="false">
                                                <i class="fas fa-ellipsis-v fa-sm fa-fw text-gray-400"></i>
                                            </a>
                                        </div>
                                    </div>
                                    <!-- Card Body -->
                                    <div class="card-body">
                                        <div class="d-flex mb-2">
                                            <div class="col-sm-12 my-1">
                                                <div class="card mb-4">

                                                    <div class="card-body">
                                                        <div class="form-group">
                                                            <label class="font-weight-bold text-gray-700">Ingrese DNI del paciente</label>
                                                        </div>
                                                        <form action="consulta.do?menu=consulta-medica" name="form" method="POST">
                                                            <div class="form-group d-flex">
                                                                <div class="col-sm-5 d-flex">
                                                                    <input type="text" name="dni_paciente" class="form-control" id="buscar_dni" value="${paciente.getDni()}" required />
                                                                </div>

                                                                <div class="col-sm">
                                                                    <button type="submit" name="opcion" value="buscar" class="agregar btn btn-success">
                                                                        <i class="fas fa-search"></i> Buscar
                                                                    </button>
                                                                </div>
                                                            </div>
                                                        </form>    

                                                        <form action="consulta.do?menu=consulta-medica" method="POST">

                                                            <div class="form-group d-flex">
                                                                <div class="col-sm-6">
                                                                    <label class="font-weight-bold text-gray-700">Paciente</label>
                                                                    <input
                                                                        type="text"
                                                                        name="nombreCompleto"
                                                                        class="form-control"
                                                                        id="paciente_desc"
                                                                        value="${paciente.getNombre()} ${paciente.getApellido()}"
                                                                        readonly
                                                                        />
                                                                </div>

                                                                <div class="col-sm-2">
                                                                    <label class="font-weight-bold text-gray-700">Edad</label>
                                                                    <input
                                                                        type="text"
                                                                        name="edad"
                                                                        class="form-control text-center"
                                                                        id="paciente_desc"
                                                                        value="${paciente.getEdad()}"
                                                                        readonly
                                                                        />                                  
                                                                </div>                                                      
                                                            </div>
                                                            <div class="form-group d-flex">
                                                                <div class="col-sm-6">
                                                                    <label class="font-weight-bold text-gray-700">Motivo de consulta</label>
                                                                    <input
                                                                        type="text"
                                                                        name="motivo"
                                                                        class="form-control"
                                                                        id="motivo"
                                                                        />
                                                                </div>

                                                                <div class="form-group ml-2">
                                                                    <label class="font-weight-bold text-gray-700">Receta</label>
                                                                    <textarea
                                                                        class="form-control"
                                                                        rows="3"
                                                                        placeholder="Escribir ..."
                                                                        name="receta"
                                                                        ></textarea>
                                                                </div>

                                                                <div class="form-group ml-2">
                                                                    <label class="font-weight-bold text-gray-700">Observaciones</label>
                                                                    <textarea
                                                                        class="form-control"
                                                                        rows="3"
                                                                        placeholder="Escribir ..."
                                                                        name="observacion"
                                                                        ></textarea>
                                                                </div>                                        
                                                            </div>
                                                              
                                                                        <input type="hidden" name="idPaciente" value="${paciente.getId()}">
                                                            <div class="form-group">
                                                                <div class="col-sm">
                                                                    <a  href="consulta.do?menu=consulta-medica&opcion=detalle-paciente&id_paciente=${paciente.getId()}" type="button" class="btn-ficha btn btn-success">
                                                                        <i class="fas fa-address-card"></i> Información del paciente</a>                                    
                                                                    <button type="submit" name="opcion" value="registrar" class="btn-guardarconsulta btn btn-primary btn-icon-split">
                                                                        <span class="icon text-white-60"><i class="fas fa-check-circle"></i></span>
                                                                        <span class="text">Guardar consulta</span>
                                                                    </button>
                                                                    <button type="submit" name="accion" class="cancelar btn btn-danger btn-icon-split">
                                                                        <span class="icon text-white-60"><i class="fas fa-window-close" onclick="realizar_focus()"></i></span>
                                                                        <span class="text">Limpiar</span>
                                                                    </button>
                                                                </div>
                                                            </div>
                                                        </form>              
                                                    </div>

                                                </div>
                                            </div>
                                        </div>

                                        <br />

                                        <h4 class="font-weight-bold text-center">
                                            Historia Clinica
                                        </h4>
                                        <br />

                                        <div class="table-responsive">
                                            <table class="table table-bordered" id="table_historial" width="100%" cellspacing="0">
                                                <thead>
                                                    <tr class="font-weight-bold bg-gradient-primary text-gray-100">
                                                        <th>Fecha</th>
                                                        <th>Motivo de consulta</th>
                                                        <th>Observacion</th>
                                                        <th>Receta</th>
                                                    </tr>
                                                </thead>

                                                <tbody>
                                                    <c:forEach var="consulta" items="${paciente.getConsultas()}">
                                                        <tr class="text-gray-900">
                                                            <td>${consulta.getFecha()}</td>
                                                            <td>${consulta.getMotivo()}</td>
                                                            <td>${consulta.getObservacion()}</td>
                                                            <td>${consulta.getReceta()}</td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- /.container-fluid -->
                </div>
                <!-- End of Main Content -->

                <!-- Footer -->
                <footer class="sticky-footer bg-white">
                    <!-- <div class="container my-auto">
                       <div class="copyright text-center my-auto">
                         <span>Copyright &copy; Roque</span>
                       </div>
                     </div> -->
                </footer>
                <!-- End of Footer -->
            </div>
            <!-- End of Content Wrapper -->
        </div>
        <!-- End of Page Wrapper -->

        <!-- Scroll to Top Button-->
        <a class="scroll-to-top rounded" href="#page-top">
            <i class="fas fa-angle-up"></i>
        </a>

        <!-- Bootstrap core JavaScript-->
        <script src="vendor/jquery/jquery.min.js"></script>
        <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

        <!-- Core plugin JavaScript-->
        <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

        <!-- Custom scripts for all pages-->
        <script src="js/sb-admin-2.min.js"></script>

        <!-- Page level plugins -->
        <script src="vendor/datatables/jquery.dataTables.min.js"></script>
        <script src="vendor/datatables/dataTables.bootstrap4.min.js"></script>

        <!-- Page level custom scripts -->
        <script src="js/demo/datatables-demo.js"></script>

        <!--Index editado-->
        <script src="js/index.js"></script>

    </body>
</html>

