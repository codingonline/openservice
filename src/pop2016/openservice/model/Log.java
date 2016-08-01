package pop2016.openservice.model;
// default package



/**
 * Log entity. @author MyEclipse Persistence Tools
 */

public class Log  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private String pname;
     private String action;
     private String domain;
     private Integer port;
     private String dockerid;
     private String ptype;
     private String owner;
     private String user;
     private String appname;
     private String deployTime;
     private String actionTime;


    // Constructors

    /** default constructor */
    public Log() {
    }

    
    /** full constructor */
    public Log(String pname, String action, String domain, Integer port, String dockerid, String ptype, String owner, String user, String appname, String deployTime, String actionTime) {
        this.pname = pname;
        this.action = action;
        this.domain = domain;
        this.port = port;
        this.dockerid = dockerid;
        this.ptype = ptype;
        this.owner = owner;
        this.user = user;
        this.appname = appname;
        this.deployTime = deployTime;
        this.actionTime = actionTime;
    }

   
    // Property accessors

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public String getPname() {
        return this.pname;
    }
    
    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getAction() {
        return this.action;
    }
    
    public void setAction(String action) {
        this.action = action;
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

    public String getDockerid() {
        return this.dockerid;
    }
    
    public void setDockerid(String dockerid) {
        this.dockerid = dockerid;
    }

    public String getPtype() {
        return this.ptype;
    }
    
    public void setPtype(String ptype) {
        this.ptype = ptype;
    }

    public String getOwner() {
        return this.owner;
    }
    
    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getUser() {
        return this.user;
    }
    
    public void setUser(String user) {
        this.user = user;
    }

    public String getAppname() {
        return this.appname;
    }
    
    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getDeployTime() {
        return this.deployTime;
    }
    
    public void setDeployTime(String deployTime) {
        this.deployTime = deployTime;
    }

    public String getActionTime() {
        return this.actionTime;
    }
    
    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }
   








}