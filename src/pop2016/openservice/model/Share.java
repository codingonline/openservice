package pop2016.openservice.model;
// default package

import java.sql.Timestamp;


/**
 * Share entity. @author MyEclipse Persistence Tools
 */

public class Share  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private App appByAppname;
     private App appByUsername;
     private Timestamp time;
     private String token;


    // Constructors

    /** default constructor */
    public Share() {
    }

    
    /** full constructor */
    public Share(App appByAppname, App appByUsername, Timestamp time, String token) {
        this.appByAppname = appByAppname;
        this.appByUsername = appByUsername;
        this.time = time;
        this.token = token;
    }

   
    // Property accessors

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public App getAppByAppname() {
        return this.appByAppname;
    }
    
    public void setAppByAppname(App appByAppname) {
        this.appByAppname = appByAppname;
    }

    public App getAppByUsername() {
        return this.appByUsername;
    }
    
    public void setAppByUsername(App appByUsername) {
        this.appByUsername = appByUsername;
    }

    public Timestamp getTime() {
        return this.time;
    }
    
    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getToken() {
        return this.token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
   








}