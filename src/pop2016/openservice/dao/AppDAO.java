package pop2016.openservice.dao;
// default package

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pop2016.openservice.dao.BaseHibernateDAO;
import pop2016.openservice.model.App;

/**
 	* A data access object (DAO) providing persistence and search support for App entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see .App
  * @author MyEclipse Persistence Tools 
 */
public class AppDAO extends BaseHibernateDAO  {
	     private static final Logger log = LoggerFactory.getLogger(AppDAO.class);
		//property constants
	public static final String APP_NAME = "appName";
	public static final String APP_TYPE = "appType";
	public static final String OWNER_NAME = "ownerName";
	public static final String WRITE = "write";
	public static final String PAAS_NAME = "paasName";
	public static final String SVN_URL = "svnUrl";
	public static final String GIT_URL = "gitUrl";
	public static final String DOMAIN = "domain";
	public static final String IMPORT_URL = "importUrl";



    
    public void save(App transientInstance) {
        log.debug("saving App instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(App persistentInstance) {
        log.debug("deleting App instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public App findById( java.lang.Integer id) {
        log.debug("getting App instance with id: " + id);
        try {
            App instance = (App) getSession()
                    .get("App", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(App instance) {
        log.debug("finding App instance by example");
        try {
            List results = getSession()
                    .createCriteria("App")
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
      log.debug("finding App instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from App as model where model." 
         						+ propertyName + "= ?0";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter("0", value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByAppName(Object appName
	) {
		return findByProperty(APP_NAME, appName
		);
	}
	
	public List findByAppType(Object appType
	) {
		return findByProperty(APP_TYPE, appType
		);
	}
	
	public List findByOwnerName(Object ownerName
	) {
		return findByProperty(OWNER_NAME, ownerName
		);
	}
	
	public List findByWrite(Object write
	) {
		return findByProperty(WRITE, write
		);
	}
	
	public List findByPaasName(Object paasName
	) {
		return findByProperty(PAAS_NAME, paasName
		);
	}
	
	public List findBySvnUrl(Object svnUrl
	) {
		return findByProperty(SVN_URL, svnUrl
		);
	}
	
	public List findByGitUrl(Object gitUrl
	) {
		return findByProperty(GIT_URL, gitUrl
		);
	}
	
	public List findByDomain(Object domain
	) {
		return findByProperty(DOMAIN, domain
		);
	}
	
	public List findByImportUrl(Object importUrl
	) {
		return findByProperty(IMPORT_URL, importUrl
		);
	}
	

	public List findAll() {
		log.debug("finding all App instances");
		try {
			String queryString = "from App";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public App merge(App detachedInstance) {
        log.debug("merging App instance");
        try {
            App result = (App) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(App instance) {
        log.debug("attaching dirty App instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(App instance) {
        log.debug("attaching clean App instance");
        try {
                      	getSession().buildLockRequest(LockOptions.NONE).lock(instance);
          	            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}