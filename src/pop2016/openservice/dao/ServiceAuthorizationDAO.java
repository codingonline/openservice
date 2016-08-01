package pop2016.openservice.dao;

import java.sql.Timestamp;
import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pop2016.openservice.model.ServiceAuthorization;

/**
 * A data access object (DAO) providing persistence and search support for
 * ServiceAuthorization entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see pop2016.ServiceAuthorization
 * @author MyEclipse Persistence Tools
 */

public class ServiceAuthorizationDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(ServiceAuthorizationDAO.class);
	// property constants
	public static final String USERID = "userid";
	public static final String SERVICEID = "serviceid";

	public void save(ServiceAuthorization transientInstance) {
		log.debug("saving ServiceAuthorization instance");
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

	public void delete(ServiceAuthorization persistentInstance) {
		log.debug("deleting ServiceAuthorization instance");
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

	public ServiceAuthorization findById(java.lang.Integer id) {
		log.debug("getting ServiceAuthorization instance with id: " + id);
		try {
			ServiceAuthorization instance = (ServiceAuthorization) getSession()
					.get("pop2016.ServiceAuthorization", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ServiceAuthorization instance) {
		log.debug("finding ServiceAuthorization instance by example");
		try {
			List results = getSession()
					.createCriteria("pop2016.ServiceAuthorization")
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
		log.debug("finding ServiceAuthorization instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from ServiceAuthorization as model where model."
					+ propertyName + "= ?0";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter("0", value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByUserid(Object userid) {
		return findByProperty(USERID, userid);
	}

	public List findByServiceid(Object serviceid) {
		return findByProperty(SERVICEID, serviceid);
	}

	public List findAll() {
		log.debug("finding all ServiceAuthorization instances");
		try {
			String queryString = "from ServiceAuthorization";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ServiceAuthorization merge(ServiceAuthorization detachedInstance) {
		log.debug("merging ServiceAuthorization instance");
		try {
			ServiceAuthorization result = (ServiceAuthorization) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ServiceAuthorization instance) {
		log.debug("attaching dirty ServiceAuthorization instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ServiceAuthorization instance) {
		log.debug("attaching clean ServiceAuthorization instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}