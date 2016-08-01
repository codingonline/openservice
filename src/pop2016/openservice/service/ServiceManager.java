package pop2016.openservice.service;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


import pop2016.openservice.dao.ServiceAuthorizationDAO;
import pop2016.openservice.dao.ServiceDAO;
import pop2016.openservice.dao.ServiceInstanceDAO;
import pop2016.openservice.model.Service;
import pop2016.openservice.model.ServiceAuthorization;
import pop2016.openservice.model.ServiceInstance;

public class ServiceManager {
	private  ServiceDAO serviceDao;
	private  ServiceAuthorizationDAO serviceAuthDao;
	private  ServiceInstanceDAO serviceInstanceDao;
	public ServiceManager(){
		serviceDao = new ServiceDAO();
		serviceAuthDao = new ServiceAuthorizationDAO();
		serviceInstanceDao = new ServiceInstanceDAO();
	}
	
	public Service createService(String serviceName, String serviceType,
			String serviceContainerType,
			Integer ownerId, String description,String pluginAddress, 
			Timestamp createDate, Timestamp updateDate, 
			String ownerName, String ownerPhone, String ownerQq, 
			String ownerWechat){
		if(findByServiceName(serviceName)!=null)
			return null;
		
		Service service = new Service(serviceName, serviceType, 
				 serviceContainerType,
				ownerId, description,pluginAddress, 
				createDate, updateDate, 
				ownerName, ownerPhone, ownerQq, 
				ownerWechat,false,new HashSet(),0);
		try{
			serviceDao.save(service);
			return service;
		}catch (Exception e) {
			return null;
		}

	}
	
	public ServiceAuthorization addServiceAuthorization(int serviceId, int userId){
		List<ServiceAuthorization> auths = serviceAuthDao.findByUserid(userId);
		for(int i=0;i < auths.size(); i++){
			if(serviceId == auths.get(i).getServiceid())
				return null;
		}
		Timestamp createDate = new Timestamp(new Date().getTime());
		ServiceAuthorization sevauth = new ServiceAuthorization(userId,serviceId,createDate);
		try{
			serviceAuthDao.save(sevauth);
			return sevauth;
		}catch (Exception e) {
			return null;
		}

	}
	
	public ServiceInstance addServiceInstance(String dockerid, Service service, String domain,
			Integer port, Integer sshport){
		ServiceInstance serviceInstance = new ServiceInstance(dockerid,service,domain,port,sshport);
		try{
			serviceInstanceDao.save(serviceInstance);
			return serviceInstance;
		}catch (Exception e) {
			return null;
		}

	}
	
	public boolean deleteAllServiceInstance(Service service){
		Set<ServiceInstance> set = service.getServiceInstances();
		try{
			for(ServiceInstance si : set){
				serviceInstanceDao.delete(si);
			}
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
	public boolean delServiceAuthorization(int serviceId, int userId){
		ServiceAuthorization sevauth =null;
		List<ServiceAuthorization> auths = serviceAuthDao.findByUserid(userId);
		for(int i=0;i < auths.size(); i++){
			if(serviceId == auths.get(i).getServiceid())
				sevauth = auths.get(i);
		}
		if(sevauth ==null)
			return false;
		try{
			serviceAuthDao.delete(sevauth);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
	public boolean deleteService(String serviceName, Integer ownerId){
		Service service = findServiceByNameAndOwner(serviceName, ownerId);
		if (service==null){
			return false;
		}
		try{
			serviceDao.delete(service);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
	public String getAddress(String serviceName){
		Service service = findByServiceName(serviceName);
		if(service ==null)
			return "";
		return service.getPluginAddress();
	}
	
	public boolean setAddress(String address,String serviceName, Integer ownerId){
		Service service = findServiceByNameAndOwner(serviceName, ownerId);
		if (service==null){
			return false;
		}
		try{
			service.setPluginAddress(address);
			serviceDao.save(service);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	public List<Service> getAllService(){
		return serviceDao.findAll();

	}
	
	
	
	public List<Service> getAllServiceByOwnerId(Integer ownerId){
		return serviceDao.findByOwnerId(ownerId);

	}
	
	public Service findByServiceName(String serviceName){
		List<Service> list = serviceDao.findByServiceName(serviceName);
		if(list == null || list.size()==0)
			return null;
		else
			return list.get(0);

	}
	
	public List<Service> getAllServiceByUserId(Integer userId){
		List<Service> res = new ArrayList<Service>();
		
		
		List<ServiceAuthorization> auths = serviceAuthDao.findByUserid(userId);
		for(int i=0; i < auths.size(); i++){
			Service sev = serviceDao.findById(auths.get(i).getServiceid());
			if(sev.getStatus()!=2)
				res.add(sev);
			
		} 
		return res;

	}
	
	public List<Service> getAllUnauthedServiceByUserId(Integer userId){
		
		
		HashSet<Integer> authed= new HashSet<Integer>();
		
		List<ServiceAuthorization> auths = serviceAuthDao.findByUserid(userId);
		for(int i=0; i < auths.size(); i++){
			
			authed.add(auths.get(i).getServiceid());
			
		}
		List<Service> all = getAllService();
		Iterator<Service> iter = all.iterator();
	    while(iter.hasNext()){
	    	Service sev = iter.next();
	    	if(sev!=null && sev.getOwnerId()==111 || authed.contains(sev.getId()) || sev.getStatus()==2 )
	    			iter.remove();
	    	
	    }

		return all;

	}
	
	public Service findServiceByNameAndOwner(String serviceName, Integer ownerId){
		List<Service> list = serviceDao.findByServiceNameAndOwnerId(serviceName, ownerId);
		if(list == null || list.size()==0)
			return null;
		else
			return list.get(0);
	
	}
	public boolean checkStatus(String serviceName, Integer ownerId){
		Service service = findServiceByNameAndOwner(serviceName, ownerId);
		if(service!=null && service.getStatus()==1)
			return true;
		else
			return false;
	}
	public boolean setStatus(Integer serviceId, Integer status){
		Service service = serviceDao.findById(serviceId);
		service.setStatus(status);
		try{
			serviceDao.save(service);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
	public boolean jsFileExist(String serviceName, String ownerName){
		String serverRealPath = System.getProperty("user.dir").replace("bin", "webapps");
		String filePath=serverRealPath+"/plugins/"+ownerName+"/"+serviceName;
		File file = new File(filePath);
		return file.exists();
	}
	
	public static void main(String[] args){
		System.out.println("start");
		System.out.println(new ServiceManager().getAllUnauthedServiceByUserId(104).size());
		System.out.println("end");
	}
	
	
}
