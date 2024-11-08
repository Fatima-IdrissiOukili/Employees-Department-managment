package com.fatima.project.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.fatima.project.entities.Department;
import com.fatima.project.services.DepartmentService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;

/**
 * Servlet implementation class DepartmentController
 */

@SuppressWarnings("serial")
@WebServlet({
	"/listDepartement", 
	"/newDepartementForm", 
	"/editDepartementForm", 
	"/insertDepartement", 
	"/updateDepartement", 
	"/deleteDepartement",
	"/searchDepartement"
})
public class DepartmentController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DepartmentService departmentService;

	@Override
	public void init() throws ServletException {
		super.init();
		this.departmentService = new DepartmentService(); // Or initialize it using some other method
	}

	/**
	 * Default constructor.
	 */
	public DepartmentController() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getServletPath();
        try {
            switch (path) {
                case "/listDepartement":
                    listeDepartement(request, response);
                    break;
                case "/newDepartementForm":
                    showNewForm(request, response);
                    break;
                case "/editDepartementForm":
                    showEditForm(request, response);
                    break;
                case "/insertDepartement":
                    insertDepartement(request, response);
                    break;
                case "/updateDepartement":
                    updateDepartement(request, response);
                    break;
                case "/deleteDepartement":
                    deleteDepartement(request, response);
                    break;
                case "/searchDepartement":
                    searchDepartement(request, response);
                    break;
                default:
                    // Error 404
                    request.getRequestDispatcher("NotFound.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void listeDepartement(HttpServletRequest request, HttpServletResponse response)
	    throws SQLException, IOException, ServletException {
	        
	        int page = 1;
	        int recordsPerPage = 4;
	        if (request.getParameter("page") != null)
	            page = Integer.parseInt(request.getParameter("page"));

	        int offset = (page - 1) * recordsPerPage;

	        List<Department> departments = departmentService.getDepartmentWithPagination(offset, recordsPerPage);
	        
	        int totalDepartments = departmentService.getTotalDep();

	        int totalPages = (int) Math.ceil((double) totalDepartments / recordsPerPage);

	        request.setAttribute("departments", departments);
	        request.setAttribute("totalPages", totalPages);
	        request.setAttribute("currentPage", page);

	        RequestDispatcher dispatcher = request.getRequestDispatcher("departments.jsp");
	        dispatcher.forward(request, response);
	        
    }
	
	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	        RequestDispatcher dispatcher = request.getRequestDispatcher("departmentForm.jsp");
	        dispatcher.forward(request, response);
    }
	
	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
	    throws SQLException, ServletException, IOException {
	        Long id = Long.parseLong(request.getParameter("id"));
	        Department existingDep = departmentService.getById(id);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("departmentForm.jsp");
	        request.setAttribute("departement", existingDep);
	        dispatcher.forward(request, response);
    }
	
	private void insertDepartement(HttpServletRequest request, HttpServletResponse response)
	    throws SQLException, IOException {
	        String name = request.getParameter("name");
	        String location = request.getParameter("location");
	        
	        Department newDepartement = new Department();
	        newDepartement.setName(name);
	        newDepartement.setLocation(location);
	        
	        departmentService.save(newDepartement);
	        response.sendRedirect("listDepartement");
    }
	
	private void updateDepartement(HttpServletRequest request, HttpServletResponse response)
	    throws SQLException, IOException {
	        Long id = Long.parseLong(request.getParameter("id"));
	        String name = request.getParameter("name");
	        String location = request.getParameter("location");
	
	        Department departement = new Department(id, name, location);
	        departmentService.update(departement);
	        response.sendRedirect("listDepartement");
    }
	
	private void deleteDepartement(HttpServletRequest request, HttpServletResponse response)
	    throws SQLException, IOException {
		Long id = Long.parseLong(request.getParameter("id"));
	    
	    Department departmentToDelete = departmentService.getById(id);
	    
	    if (departmentToDelete != null) {
	        departmentService.delete(departmentToDelete);
	    }
	    
	    response.sendRedirect("listDepartement");
    }
	/*private void searchDepartement(HttpServletRequest request, HttpServletResponse response)
	        throws SQLException, IOException, ServletException {
	    String searchQuery = request.getParameter("searchQuery");
	    List<Department> departments = departmentService.search(searchQuery);
	    request.setAttribute("departments", departments);
	    RequestDispatcher dispatcher = request.getRequestDispatcher("departments.jsp");
	    dispatcher.forward(request, response);
	}*/
	private void searchDepartement(HttpServletRequest request, HttpServletResponse response)
	        throws SQLException, IOException, ServletException {
	    String searchQuery = request.getParameter("searchQuery");
	    List<Department> departments = departmentService.search(searchQuery);
	    request.setAttribute("departments", departments);
	    RequestDispatcher dispatcher = request.getRequestDispatcher("departments.jsp");
	    dispatcher.forward(request, response);
	}


}
