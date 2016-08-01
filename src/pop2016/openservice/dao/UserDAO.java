package pop2016.openservice.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pop2016.openservice.model.User;

/**
 * A data access object (DAO) providing persistence and search support for User
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see pop2016.openservice.model.User
 * @author MyEclipse Persistence Tools
 */

public class UserDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(UserDAO.class);
	// property constants
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String IS_SUPER = "isSuper";
	public static final String EMAIL = "email";
	public static final String UNAME_BAIDU = "unameBaidu";
	public static final String UNAME_SINA = "unameSina";
	public static final String UNAME_SAE = "unameSae";
	public static final String UNAME_CODE = "unameCode";
	public static final String PWD_BAIDU = "pwdBaidu";
	public static final String PWD_SAE = "pwdSae";
	public static final String VALIDATE_CODE = "validateCode";
	public static final String REGISTER_STATUS = "registerStatus";
	public static final String TOKEN = "token";
	public static final String IS_REMEMBERED = "isRemembered";

	public void save(User transientInstance) {
		log.debug("saving User instance");
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

	public void delete(User persistentInstance) {
		log.debug("deleting User instance");
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

	public User findById(java.lang.Integer id) {
		log.debug("getting User instance with id: " + id);
		try {
			User instance = (User) getSession().get("User", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(User instance) {
		log.debug("finding User instance by example");
		try {
			List results = getSession().createCriteria("User")
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
		log.debug("finding User instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from User as model where model."
					+ propertyName + "= ?0";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter("0", value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByUsername(Object username) {
		return findByProperty(USERNAME, username);
	}

	public List findByPassword(Object password) {
		return findByProperty(PASSWORD, password);
	}

	public List findByIsSuper(Object isSuper) {
		return findByProperty(IS_SUPER, isSuper);
	}

	public List findByEmail(Object email) {
		return findByProperty(EMAIL, email);
	}

	public List findByUnameBaidu(Object unameBaidu) {
		return findByProperty(UNAME_BAIDU, unameBaidu);
	}

	public List findByUnameSina(Object unameSina) {
		return findByProperty(UNAME_SINA, unameSina);
	}

	public List findByUnameSae(Object unameSae) {
		return findByProperty(UNAME_SAE, unameSae);
	}

	public List findByUnameCode(Object unameCode) {
		return findByProperty(UNAME_CODE, unameCode);
	}

	public List findByPwdBaidu(Object pwdBaidu) {
		return findByProperty(PWD_BAIDU, pwdBaidu);
	}

	public List findByPwdSae(Object pwdSae) {
		return findByProperty(PWD_SAE, pwdSae);
	}

	public List findByValidateCode(Object validateCode) {
		return findByProperty(VALIDATE_CODE, validateCode);
	}

	public List findByRegisterStatus(Object registerStatus) {
		return findByProperty(REGISTER_STATUS, registerStatus);
	}

	public List findByToken(Object token) {
		return findByProperty(TOKEN, token);
	}

	public List findByIsRemembered(Object isRemembered) {
		return findByProperty(IS_REMEMBERED, isRemembered);
	}

	public List findAll() {
		log.debug("finding all User instances");
		try {
			String queryString = "from User";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public User merge(User detachedInstance) {
		log.debug("merging User instance");
		try {
			User result = (User) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(User instance) {
		log.debug("attaching dirty User instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(User instance) {
		log.debug("attaching clean User instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}