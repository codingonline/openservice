package pop2016.openservice.model;
// default package

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * Service entity. @author MyEclipse Persistence Tools
 */
@JsonIgnoreProperties(value={"serviceInstances"})  
public class Service  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private String serviceName;
     private String serviceType;
     private String serviceContainerType;
     private Integer ownerId;
     private String description;
     private String pluginAddress;
     private Timestamp createDate;
     private Timestamp updateDate;
     private String ownerName;
     private String ownerPhone;
     private String ownerQq;
     private String ownerWechat;
     private Boolean issuper;
     private Set serviceInstances = new HashSet(0);
     private Integer status;


    // Constructors

    /** default constructor */
    public Service() {
    }

	/** minimal constructor */
    public Service(String serviceName, String serviceType, String serviceContainerType, Integer ownerId, Timestamp createDate) {
        this.serviceName = serviceName;
        this.serviceType = serviceType;
        this.serviceContainerType = serviceContainerType;
        this.ownerId = ownerId;
        this.createDate = createDate;
    }
    
    /** full constructor */
    public Service(String serviceName, String serviceType, String serviceContainerType, Integer ownerId, String description, String pluginAddress, Timestamp createDate, Timestamp updateDate, String ownerName, String ownerPhone, String ownerQq, String ownerWechat, Boolean issuper, Set serviceInstances,Integer status) {
        this.serviceName = serviceName;
        this.serviceType = serviceType;
        this.serviceContainerType = serviceContainerType;
        this.ownerId = ownerId;
        this.description = description;
        this.pluginAddress = pluginAddress;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.ownerName = ownerName;
        this.ownerPhone = ownerPhone;
        this.ownerQq = ownerQq;
        this.ownerWechat = ownerWechat;
        this.issuper = issuper;
        this.serviceInstances = serviceInstances;
        this.status = status;
    }

   
    // Property accessors

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public String getServiceName() {
        return this.serviceName;
    }
    
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceType() {
        return this.serviceType;
    }
    
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceContainerType() {
        return this.serviceContainerType;
    }
    
    public void setServiceContainerType(String serviceContainerType) {
        this.serviceContainerType = serviceContainerType;
    }

    public Integer getOwnerId() {
        return this.ownerId;
    }
    
    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public String getPluginAddress() {
        return this.pluginAddress;
    }
    
    public void setPluginAddress(String pluginAddress) {
        this.pluginAddress = pluginAddress;
    }

    public Timestamp getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getUpdateDate() {
        return this.updateDate;
    }
    
    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public String getOwnerName() {
        return this.ownerName;
    }
    
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerPhone() {
        return this.ownerPhone;
    }
    
    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public String getOwnerQq() {
        return this.ownerQq;
    }
    
    public void setOwnerQq(String ownerQq) {
        this.ownerQq = ownerQq;
    }

    public String getOwnerWechat() {
        return this.ownerWechat;
    }
    
    public void setOwnerWechat(String ownerWechat) {
        this.ownerWechat = ownerWechat;
    }

    public Boolean getIssuper() {
        return this.issuper;
    }
    
    public void setIssuper(Boolean issuper) {
        this.issuper = issuper;
    }

    public Set getServiceInstances() {
        return this.serviceInstances;
    }
    
    public void setServiceInstances(Set serviceInstances) {
        this.serviceInstances = serviceInstances;
    }
    public Integer getStatus() {
        return this.status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
   








}