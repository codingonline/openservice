package pop2016.openservice.model;

import java.sql.Timestamp;

/**
 * ServiceAuthorization entity. @author MyEclipse Persistence Tools
 */

public class ServiceAuthorization implements java.io.Serializable {

	// Fields

	private Integer authorizationid;
	private Integer userid;
	private Integer serviceid;
	private Timestamp authorizeTime;

	// Constructors

	/** default constructor */
	public ServiceAuthorization() {
	}

	/** full constructor */
	public ServiceAuthorization(Integer userid, Integer serviceid,
			Timestamp authorizeTime) {
		this.userid = userid;
		this.serviceid = serviceid;
		this.authorizeTime = authorizeTime;
	}

	// Property accessors

	public Integer getAuthorizationid() {
		return this.authorizationid;
	}

	public void setAuthorizationid(Integer authorizationid) {
		this.authorizationid = authorizationid;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getServiceid() {
		return this.serviceid;
	}

	public void setServiceid(Integer serviceid) {
		this.serviceid = serviceid;
	}

	public Timestamp getAuthorizeTime() {
		return this.authorizeTime;
	}

	public void setAuthorizeTime(Timestamp authorizeTime) {
		this.authorizeTime = authorizeTime;
	}

}