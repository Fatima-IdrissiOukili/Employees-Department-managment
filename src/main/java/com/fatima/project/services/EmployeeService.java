package com.fatima.project.services;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.fatima.project.entities.Department;
import com.fatima.project.entities.Employee;
import com.fatima.project.util.HibernateUtil;

public class EmployeeService implements CrudDao<Employee, Long> {
	
	
	private SessionFactory sessionFactory;
	private DepartmentService departmentService = new DepartmentService();
	

	public EmployeeService() {
		super();
		this.sessionFactory = HibernateUtil.getSessionFactory();
		/*if (this.getAll().size() == 0) {
			Employee employee = Employee.builder().firstName("Soufiane").lastName("Elmarnissi").email("soufiane@gmail.com").build();
			save(employee);
		}*/
		if (this.getAll().isEmpty()) {
			
	        Department department = departmentService.getById(1L);
	        
	        Employee employee = Employee.builder()
	            .firstName("Ziad")
	            .lastName("Elmarnissi")
	            .email("ziad@gmail.com")
	            .department(department)
	            .build();

	        save(employee);
	    }
	}

	@Override
	public void save(Employee entity) {
		
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.save(entity);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		
	}

	@Override
	public Employee getById(Long id) {
		Session session = sessionFactory.openSession();
		Employee employee = null;
		try {
			employee = session.get(Employee.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return employee;
	}

	@Override
	public List<Employee> getAll() {
		Session session = sessionFactory.openSession();
		List<Employee> employees = null;
		try {
			employees = session.createQuery("FROM Employee").list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return employees;
	}

	@Override
	public void update(Employee entity) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.update(entity);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	@Override
	public void delete(Employee entity) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.delete(entity);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	@Override
	public List<Employee> search(String cmh) {
	    Session session = sessionFactory.openSession();
	    List<Employee> employees = null;
	    try {
	        Query<Employee> query = session.createQuery("FROM Employee WHERE firstName LIKE :query OR lastName LIKE :query OR email LIKE :query", Employee.class);
	        query.setParameter("query", "%" + cmh + "%");
	        employees = query.list();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        session.close();
	    }
	    return employees;
	}
	
	public List<Employee> getEmployeeWithPagination(int offset, int numofrecords) {
	    Session session = sessionFactory.openSession();
	    List<Employee> employees = null;
	    try {
	        Query query = session.createQuery("FROM Employee ORDER BY id ASC");
	        query.setFirstResult(offset);
	        query.setMaxResults(numofrecords);
	        employees = query.list();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        session.close();
	    }
	    return employees;
	}
	
	public int getTotalEmp() {
		Session session = HibernateUtil.getSessionFactory().openSession();
        int count = 0;
        try {
            Query<Long> query = session.createQuery("SELECT COUNT(*) FROM Employee", Long.class);
            count = Math.toIntExact(query.uniqueResult());
        } finally {
            session.close();
        }
		return count;
	}


}
