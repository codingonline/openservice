package pop2016.openservice.model;

public class CodeFile {
	private String serviceName;
	private boolean serverStatus;
	private boolean jsStatus;
	public CodeFile(String servicename, boolean serverstatus, boolean jsstatus){
		serviceName = servicename;
		serverStatus = serverstatus;
		jsStatus = jsstatus;
	}
	public String getServiceName(){
		return serviceName;
	}
	public boolean getServerStatus(){
		return serverStatus;
	}
	public boolean getJsStatus(){
		return jsStatus;
	}
	

}
