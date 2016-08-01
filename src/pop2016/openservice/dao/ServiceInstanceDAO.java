package pop2016.openservice.dao;

import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pop2016.openservice.model.ServiceInstance;

/**
 * A data access object (DAO) providing persistence and search support for
 * ServiceInstance entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see pop2016.openservice.model.ServiceInstance
 * @author MyEclipse Persistence Tools
 */

public class ServiceInstanceDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(ServiceInstanceDAO.class);
	// property constants
	public static final String DOMAIN = "domain";
	public static final String PORT = "port";
	public static final String SSHPORT = "sshport";

	public void save(ServiceInstance transientInstance) {
		log.debug("saving ServiceInstance instance");
		Transaction tran=getSession().beginTransaction();
		try {
			getSession().save(transientInstance);
			tran.commit();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
		getSession().flush(); 
	    getSession().close();
	}

	public void delete(ServiceInstance persistentInstance) {
		log.debug("deleting ServiceInstance instance");
		Transaction tran=getSession().beginTransaction();
		try {
			getSession().delete(persistentInstance);
			tran.commit();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
		getSession().flush(); 
	    getSession().close();
	}

	public ServiceInstance findById(java.lang.String id) {
		log.debug("getting ServiceInstance instance with id: " + id);
		try {
			ServiceInstance instance = (ServiceInstance) getSession().get(
					"ServiceInstance", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ServiceInstance instance) {
		log.debug("finding ServiceInstance instance by example");
		try {
			List results = getSession()
					.createCriteria("ServiceInstance")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding ServiceInstance instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from ServiceInstance as model where model."
					+ propertyName + "= ?0";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter("0", value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByDomain(Object domain) {
		return findByProperty(DOMAIN, domain);
	}

	public List findByPort(Object port) {
		return findByProperty(PORT, port);
	}

	public List findBySshport(Object sshport) {
		return findByProperty(SSHPORT, sshport);
	}

	public List findAll() {
		log.debug("finding all ServiceInstance instances");
		try {
			String queryString = "from ServiceInstance";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ServiceInstance merge(ServiceInstance detachedInstance) {
		log.debug("merging ServiceInstance instance");
		try {
			ServiceInstance result = (ServiceInstance) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ServiceInstance instance) {
		log.debug("attaching dirty ServiceInstance instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ServiceInstance instance) {
		log.debug("attaching clean ServiceInstance instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}