package com.fatima.project.controllers;

import java.io.IOException;
import java.sql.SQLException;

import java.util.List;

import com.fatima.project.entities.Department;
import com.fatima.project.entities.Employee;
import com.fatima.project.services.DepartmentService;
import com.fatima.project.services.EmployeeService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@SuppressWarnings("serial")
@WebServlet({
	"/listEmployee", 
	"/newEmployeeForm", 
	"/editEmployeeForm", 
	"/insertEmployee", 
	"/updateEmployee",
	"/deleteEmployee",
	"/searchEmployee"
})
public class EmployeeController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private EmployeeService employeeService;
	private DepartmentService departmentService;

	public EmployeeController() {
	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		this.employeeService = new EmployeeService();
		this.departmentService = new DepartmentService();
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
                case "/listEmployee":
                    listeEmployee(request, response);
                    break;
                case "/newEmployeeForm":
                    showNewForm(request, response);
                    break;
                case "/editEmployeeForm":
                    showEditForm(request, response);
                    break;
                case "/insertEmployee":
                    insertEmployee(request, response);
                    break;
                case "/updateEmployee":
                    updateEmployee(request, response);
                    break;
                case "/deleteEmployee":
                    deleteEmployee(request, response);
                    break;
                case "/searchEmployee":
                    searchEmployee(request, response);
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

	private void listeEmployee(HttpServletRequest request, HttpServletResponse response)
		    throws SQLException, IOException, ServletException {
		        /*List < Employee > employees = employeeService.getAll();
		        request.setAttribute("employees", employees);
		        RequestDispatcher dispatcher = request.getRequestDispatcher("employees.jsp");
		        dispatcher.forward(request, response);*/
				int page = 1;
		        int recordsPerPage = 4;
		        if (request.getParameter("page") != null)
		            page = Integer.parseInt(request.getParameter("page"));
		
		        int offset = (page - 1) * recordsPerPage;
		
		        List<Employee> employees = employeeService.getEmployeeWithPagination(offset, recordsPerPage);
		        
		        int totalEmployees = employeeService.getTotalEmp();
		
		        int totalPages = (int) Math.ceil((double) totalEmployees / recordsPerPage);
		
		        request.setAttribute("employees", employees);
		        request.setAttribute("totalPages", totalPages);
		        request.setAttribute("currentPage", page);
		
		        RequestDispatcher dispatcher = request.getRequestDispatcher("employees.jsp");
		        dispatcher.forward(request, response);
	    }
		
		private void showNewForm(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {
			
				List<Department> departments = departmentService.getAll(); 
			    request.setAttribute("departments", departments);
			
		        RequestDispatcher dispatcher = request.getRequestDispatcher("employeeForm.jsp");
		        dispatcher.forward(request, response);
	    }
		
		private void showEditForm(HttpServletRequest request, HttpServletResponse response)
		    throws SQLException, ServletException, IOException {
		        Long id = Long.parseLong(request.getParameter("id"));
		        
		        List<Department> departments = departmentService.getAll(); 
			    request.setAttribute("departments", departments);
			    
		        Employee existingEmp = employeeService.getById(id);
		        RequestDispatcher dispatcher = request.getRequestDispatcher("employeeForm.jsp");
		        request.setAttribute("employee", existingEmp);
		        dispatcher.forward(request, response);
		        
	    }
		
		private void insertEmployee(HttpServletRequest request, HttpServletResponse response)
		    throws SQLException, IOException {
		        String firstName = request.getParameter("firstName");
		        String lastName = request.getParameter("lastName");
		        String email = request.getParameter("email");
		        
		        /*Employee newEmployee = new Employee();
		        newEmployee.setFirstName(firstName);
		        newEmployee.setLastName(lastName);
		        newEmployee.setEmail(email);		        */
		        Long department_id = Long.parseLong(request.getParameter("department_id"));
		        
		        
		        employeeService.save(new Employee(null, firstName, lastName , email,departmentService.getById(department_id) ));
		        //employeeService.save(newEmployee);
		        response.sendRedirect("listEmployee");
	    }
		
		private void updateEmployee(HttpServletRequest request, HttpServletResponse response)
		    throws SQLException, IOException {
		        Long id = Long.parseLong(request.getParameter("id"));
		        String firstName = request.getParameter("firstName");
		        String lastName = request.getParameter("lastName");
		        String email = request.getParameter("email");
		        Long department_id = Long.parseLong(request.getParameter("department_id"));
		
		        Employee employee = new Employee(id, firstName, lastName , email,departmentService.getById(department_id));
		        employeeService.update(employee);
		        response.sendRedirect("listEmployee");
		        
	    }
		
		private void deleteEmployee(HttpServletRequest request, HttpServletResponse response)
		    throws SQLException, IOException {
			Long id = Long.parseLong(request.getParameter("id"));
		    
		    Employee employeeToDelete = employeeService.getById(id);
		    
		    if (employeeToDelete != null) {
		        employeeService.delete(employeeToDelete);
		    }
		    
		    response.sendRedirect("listEmployee");
	    }
		
		private void searchEmployee(HttpServletRequest request, HttpServletResponse response)
		        throws SQLException, IOException, ServletException {
		    String searchQuery = request.getParameter("searchQuery");
		    List<Employee> employees = employeeService.search(searchQuery);
		    request.setAttribute("employees", employees);
		    RequestDispatcher dispatcher = request.getRequestDispatcher("employees.jsp");
		    dispatcher.forward(request, response);
		}
	
}
