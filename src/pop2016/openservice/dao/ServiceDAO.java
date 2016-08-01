package pop2016.openservice.dao;
// default package


import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pop2016.openservice.model.Service;

/**
 	* A data access object (DAO) providing persistence and search support for Service entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see .Service
  * @author MyEclipse Persistence Tools 
 */

public class ServiceDAO extends BaseHibernateDAO  {
	     private static final Logger log = LoggerFactory.getLogger(ServiceDAO.class);
		//property constants
	public static final String SERVICE_NAME = "serviceName";
	public static final String SERVICE_TYPE = "serviceType";
	public static final String SERVICE_CONTAINER_TYPE = "serviceContainerType";
	public static final String OWNER_ID = "ownerId";
	public static final String DESCRIPTION = "description";
	public static final String PLUGIN_ADDRESS = "pluginAddress";
	public static final String OWNER_NAME = "ownerName";
	public static final String OWNER_PHONE = "ownerPhone";
	public static final String OWNER_QQ = "ownerQq";
	public static final String OWNER_WECHAT = "ownerWechat";
	public static final String ISSUPER = "issuper";



    
    public void save(Service transientInstance) {
        log.debug("saving Service instance");
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
    
	public void delete(Service persistentInstance) {
        log.debug("deleting Service instance");
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
    
    public Service findById( java.lang.Integer id) {
        log.debug("getting Service instance with id: " + id);
        try {
            Service instance = (Service) getSession()
                    .get("pop2016.openservice.model.Service", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(Service instance) {
        log.debug("finding Service instance by example");
        try {
            List results = getSession()
                    .createCriteria("pop2016.openservice.model.Service")
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
      log.debug("finding Service instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from Service as model where model." 
         						+ propertyName + "= ?0";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter("0", value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}
    public List findByProperties(String propertyName1, Object value1,String propertyName2, Object value2) {
        log.debug("finding Service instance with property: " + propertyName1
              + ", value: " + value1 + "and property: " + propertyName2
                  + ", value: " + value2);
        try {
           String queryString = "from Service as model where model." 
           						+ propertyName1 + "= ?0 and " + propertyName2 + "= ?1";
           Query queryObject = getSession().createQuery(queryString);
  		 queryObject.setParameter("0", value1);
  		 queryObject.setParameter("1", value2);
  		 return queryObject.list();
        } catch (RuntimeException re) {
           log.error("find by property name failed", re);
           throw re;
        }
  	}

	public List findByServiceName(Object serviceName
	) {
		return findByProperty(SERVICE_NAME, serviceName
		);
	}
	
	public List findByServiceType(Object serviceType
	) {
		return findByProperty(SERVICE_TYPE, serviceType
		);
	}
	
	public List findByServiceContainerType(Object serviceContainerType
	) {
		return findByProperty(SERVICE_CONTAINER_TYPE, serviceContainerType
		);
	}
	
	public List findByOwnerId(Object ownerId
	) {
		return findByProperty(OWNER_ID, ownerId
		);
	}
	
	public List findByDescription(Object description
	) {
		return findByProperty(DESCRIPTION, description
		);
	}
	
	public List findByPluginAddress(Object pluginAddress
	) {
		return findByProperty(PLUGIN_ADDRESS, pluginAddress
		);
	}
	
	public List findByOwnerName(Object ownerName
	) {
		return findByProperty(OWNER_NAME, ownerName
		);
	}
	
	public List findByOwnerPhone(Object ownerPhone
	) {
		return findByProperty(OWNER_PHONE, ownerPhone
		);
	}
	
	public List findByOwnerQq(Object ownerQq
	) {
		return findByProperty(OWNER_QQ, ownerQq
		);
	}
	
	public List findByOwnerWechat(Object ownerWechat
	) {
		return findByProperty(OWNER_WECHAT, ownerWechat
		);
	}
	
	public List findByIssuper(Object issuper
	) {
		return findByProperty(ISSUPER, issuper
		);
	}
	
	public List findByServiceNameAndOwnerId(Object serviceName, Object ownerId
			) {
				return findByProperties(SERVICE_NAME, serviceName, OWNER_ID, ownerId
				);
			}
	

	public List findAll() {
		log.debug("finding all Service instances");
		try {
			String queryString = "from Service";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public Service merge(Service detachedInstance) {
        log.debug("merging Service instance");
        try {
            Service result = (Service) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Service instance) {
        log.debug("attaching dirty Service instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Service instance) {
        log.debug("attaching clean Service instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}