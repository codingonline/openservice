package pop2016.openservice.model;
// default package

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


/**
 * App entity. @author MyEclipse Persistence Tools
 */

public class App  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private User user;
     private String appName;
     private String appType;
     private String ownerName;
     private Boolean write;
     private String paasName;
     private String svnUrl;
     private String gitUrl;
     private String domain;
     private Timestamp date;
     private String importUrl;
     private Set sharesForAppname = new HashSet(0);
     private Set sharesForUsername = new HashSet(0);


    // Constructors

    /** default constructor */
    public App() {
    }

	/** minimal constructor */
    public App(User user, String appName, String appType, String ownerName, Timestamp date) {
        this.user = user;
        this.appName = appName;
        this.appType = appType;
        this.ownerName = ownerName;
        this.date = date;
    }
    
    /** full constructor */
    public App(User user, String appName, String appType, String ownerName, Boolean write, String paasName, String svnUrl, String gitUrl, String domain, Timestamp date, String importUrl, Set sharesForAppname, Set sharesForUsername) {
        this.user = user;
        this.appName = appName;
        this.appType = appType;
        this.ownerName = ownerName;
        this.write = write;
        this.paasName = paasName;
        this.svnUrl = svnUrl;
        this.gitUrl = gitUrl;
        this.domain = domain;
        this.date = date;
        this.importUrl = importUrl;
        this.sharesForAppname = sharesForAppname;
        this.sharesForUsername = sharesForUsername;
    }

   
    // Property accessors

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }

    public String getAppName() {
        return this.appName;
    }
    
    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppType() {
        return this.appType;
    }
    
    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getOwnerName() {
        return this.ownerName;
    }
    
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Boolean getWrite() {
        return this.write;
    }
    
    public void setWrite(Boolean write) {
        this.write = write;
    }

    public String getPaasName() {
        return this.paasName;
    }
    
    public void setPaasName(String paasName) {
        this.paasName = paasName;
    }

    public String getSvnUrl() {
        return this.svnUrl;
    }
    
    public void setSvnUrl(String svnUrl) {
        this.svnUrl = svnUrl;
    }

    public String getGitUrl() {
        return this.gitUrl;
    }
    
    public void setGitUrl(String gitUrl) {
        this.gitUrl = gitUrl;
    }

    public String getDomain() {
        return this.domain;
    }
    
    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Timestamp getDate() {
        return this.date;
    }
    
    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getImportUrl() {
        return this.importUrl;
    }
    
    public void setImportUrl(String importUrl) {
        this.importUrl = importUrl;
    }

    public Set getSharesForAppname() {
        return this.sharesForAppname;
    }
    
    public void setSharesForAppname(Set sharesForAppname) {
        this.sharesForAppname = sharesForAppname;
    }

    public Set getSharesForUsername() {
        return this.sharesForUsername;
    }
    
    public void setSharesForUsername(Set sharesForUsername) {
        this.sharesForUsername = sharesForUsername;
    }
   








}