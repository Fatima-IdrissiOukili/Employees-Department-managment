package com.fatima.project.services;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.fatima.project.entities.Department;
import com.fatima.project.util.HibernateUtil;

public class DepartmentService implements CrudDao<Department, Long> {

	private SessionFactory sessionFactory;

	public DepartmentService() {
		this.sessionFactory = HibernateUtil.getSessionFactory();
		if (this.getAll().size() == 0) {
			Department department = Department.builder().name("Informatique").location("2eme Etage").build();
			save(department);
		}
	}

	@Override
	public void save(Department entity) {
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
	public Department getById(Long id) {
		Session session = sessionFactory.openSession();
		Department department = null;
		try {
			department = session.get(Department.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return department;
	}

	@Override
	public List<Department> getAll() {
		Session session = sessionFactory.openSession();
		List<Department> departments = null;
		try {
			departments = session.createQuery("FROM Department").list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return departments;
	}

	@Override
	public void update(Department entity) {
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
	public void delete(Department entity) {
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
	public List<Department> search(String cmh) {
		Session session = sessionFactory.openSession();
	    List<Department> departments = null;
	    try {
	        Query<Department> query = session.createQuery("FROM Department WHERE name LIKE :cmh", Department.class);
	        query.setParameter("cmh", "%" + cmh + "%");
	        departments = query.list();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        session.close();
	    }
	    return departments;
	}

	public List<Department> getDepartmentWithPagination(int offset, int numofrecords) {
	    Session session = sessionFactory.openSession();
	    List<Department> departments = null;
	    try {
	        Query query = session.createQuery("FROM Department ORDER BY id ASC");
	        query.setFirstResult(offset);
	        query.setMaxResults(numofrecords);
	        departments = query.list();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        session.close();
	    }
	    return departments;
	}
	
	public int getTotalDep() {
		Session session = HibernateUtil.getSessionFactory().openSession();
        int count = 0;
        try {
            Query<Long> query = session.createQuery("SELECT COUNT(*) FROM Department", Long.class);
            count = Math.toIntExact(query.uniqueResult());
        } finally {
            session.close();
        }
		return count;
	}
	
}
