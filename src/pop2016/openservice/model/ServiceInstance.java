package pop2016.openservice.model;

/**
 * ServiceInstance entity. @author MyEclipse Persistence Tools
 */

public class ServiceInstance implements java.io.Serializable {

	// Fields

	private String dockerid;
	private Service service;
	private String domain;
	private Integer port;
	private Integer sshport;

	// Constructors

	/** default constructor */
	public ServiceInstance() {
	}

	/** full constructor */
	public ServiceInstance(String dockerid, Service service, String domain,
			Integer port, Integer sshport) {
		this.dockerid = dockerid;
		this.service = service;
		this.domain = domain;
		this.port = port;
		this.sshport = sshport;
	}

	// Property accessors

	public String getDockerid() {
		return this.dockerid;
	}

	public void setDockerid(String dockerid) {
		this.dockerid = dockerid;
	}

	public Service getService() {
		return this.service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public String getDomain() {
		return this.domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public Integer getPort() {
		return this.port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Integer getSshport() {
		return this.sshport;
	}

	public void setSshport(Integer sshport) {
		this.sshport = sshport;
	}

}