package pop2016.openservice.dao;
// default package

import java.util.List;

import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pop2016.openservice.dao.BaseHibernateDAO;
import pop2016.openservice.model.Log;

/**
 	* A data access object (DAO) providing persistence and search support for Log entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see .Log
  * @author MyEclipse Persistence Tools 
 */
public class LogDAO extends BaseHibernateDAO  {
	     private static final Logger log = LoggerFactory.getLogger(LogDAO.class);
		//property constants
	public static final String PNAME = "pname";
	public static final String ACTION = "action";
	public static final String DOMAIN = "domain";
	public static final String PORT = "port";
	public static final String DOCKERID = "dockerid";
	public static final String PTYPE = "ptype";
	public static final String OWNER = "owner";
	public static final String USER = "user";
	public static final String APPNAME = "appname";
	public static final String DEPLOY_TIME = "deployTime";
	public static final String ACTION_TIME = "actionTime";



    
    public void save(Log transientInstance) {
        log.debug("saving Log instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(Log persistentInstance) {
        log.debug("deleting Log instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public Log findById( java.lang.Integer id) {
        log.debug("getting Log instance with id: " + id);
        try {
            Log instance = (Log) getSession()
                    .get("Log", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(Log instance) {
        log.debug("finding Log instance by example");
        try {
            List results = getSession()
                    .createCriteria("Log")
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
      log.debug("finding Log instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Log as model where model." 
         						+ propertyName + "= ?0";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter("0", value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByPname(Object pname
	) {
		return findByProperty(PNAME, pname
		);
	}
	
	public List findByAction(Object action
	) {
		return findByProperty(ACTION, action
		);
	}
	
	public List findByDomain(Object domain
	) {
		return findByProperty(DOMAIN, domain
		);
	}
	
	public List findByPort(Object port
	) {
		return findByProperty(PORT, port
		);
	}
	
	public List findByDockerid(Object dockerid
	) {
		return findByProperty(DOCKERID, dockerid
		);
	}
	
	public List findByPtype(Object ptype
	) {
		return findByProperty(PTYPE, ptype
		);
	}
	
	public List findByOwner(Object owner
	) {
		return findByProperty(OWNER, owner
		);
	}
	
	public List findByUser(Object user
	) {
		return findByProperty(USER, user
		);
	}
	
	public List findByAppname(Object appname
	) {
		return findByProperty(APPNAME, appname
		);
	}
	
	public List findByDeployTime(Object deployTime
	) {
		return findByProperty(DEPLOY_TIME, deployTime
		);
	}
	
	public List findByActionTime(Object actionTime
	) {
		return findByProperty(ACTION_TIME, actionTime
		);
	}
	

	public List findAll() {
		log.debug("finding all Log instances");
		try {
			String queryString = "from Log";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public Log merge(Log detachedInstance) {
        log.debug("merging Log instance");
        try {
            Log result = (Log) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Log instance) {
        log.debug("attaching dirty Log instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Log instance) {
        log.debug("attaching clean Log instance");
        try {
                      	getSession().buildLockRequest(LockOptions.NONE).lock(instance);
          	            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}