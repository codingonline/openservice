package pop2016.openservice.dao;
// default package

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pop2016.openservice.dao.BaseHibernateDAO;
import pop2016.openservice.model.Share;

/**
 	* A data access object (DAO) providing persistence and search support for Share entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see .Share
  * @author MyEclipse Persistence Tools 
 */
public class ShareDAO extends BaseHibernateDAO  {
	     private static final Logger log = LoggerFactory.getLogger(ShareDAO.class);
		//property constants
	public static final String TOKEN = "token";



    
    public void save(Share transientInstance) {
        log.debug("saving Share instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(Share persistentInstance) {
        log.debug("deleting Share instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public Share findById( java.lang.Integer id) {
        log.debug("getting Share instance with id: " + id);
        try {
            Share instance = (Share) getSession()
                    .get("Share", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(Share instance) {
        log.debug("finding Share instance by example");
        try {
            List results = getSession()
                    .createCriteria("Share")
                    .add(Example.create(instance))
            .list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding Share instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Share as model where model." 
         						+ propertyName + "= ?0";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter("0", value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByToken(Object token
	) {
		return findByProperty(TOKEN, token
		);
	}
	

	public List findAll() {
		log.debug("finding all Share instances");
		try {
			String queryString = "from Share";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public Share merge(Share detachedInstance) {
        log.debug("merging Share instance");
        try {
            Share result = (Share) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Share instance) {
        log.debug("attaching dirty Share instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Share instance) {
        log.debug("attaching clean Share instance");
        try {
                      	getSession().buildLockRequest(LockOptions.NONE).lock(instance);
          	            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}