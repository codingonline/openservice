package pop2016.openservice.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.config.RequestConfig;


import com.fasterxml.jackson.databind.ObjectMapper;



import pop2016.openservice.model.CodeFile;
import pop2016.openservice.model.Service;
import pop2016.openservice.model.ServiceInstance;
import pop2016.openservice.model.User;
import pop2016.openservice.service.ServiceManager;
import pop2016.openservice.utils.DirectoryCopy;
import pop2016.openservice.utils.EnvironmentProperty;
import pop2016.openservice.utils.FileUtil;
import pop2016.openservice.utils.JSONException;
import pop2016.openservice.utils.JSONObject;

/**
 * Servlet implementation class OpenServiceServlet
 */
@WebServlet("/openService")
public class OpenServiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
     * @see HttpServlet#HttpServlet()
     */
    public OpenServiceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ServiceManager serviceManager = new ServiceManager();
		User user = (User) req.getSession().getAttribute("user");
		String username = "";
		if(user != null)
			username = user.getUsername();
			String action = req.getParameter("action");
			resp.setContentType("text/html;charset=utf-8");
			PrintWriter out = resp.getWriter();
			try {
				if("services".equals(action)){
					
					List<Service> services = serviceManager.getAllServiceByOwnerId(user.getId());
					ObjectMapper mapper = new ObjectMapper(); 
					out.print(mapper.writeValueAsString(services));
					out.flush();
					out.close();
				}
				else if("filestatus".equals(action)){
					
					List<Service> services = serviceManager.getAllServiceByOwnerId(user.getId());
					List<CodeFile> files = new ArrayList<CodeFile>();
					for(Service sev:services){
						String serviceName = sev.getServiceName();
						files.add(new CodeFile(serviceName,!sev.getServiceInstances().isEmpty(),serviceManager.jsFileExist(serviceName, username)));
					}
					ObjectMapper mapper = new ObjectMapper(); 
					out.print(mapper.writeValueAsString(files));
					out.flush();
					out.close();
				}
				else if("dockerid".equals(action)){
					String serviceName = req.getParameter("servicename");
					Service service = serviceManager.findByServiceName(serviceName);
					String dockerid = "";
					for(Object obj : service.getServiceInstances()){
						ServiceInstance si = (ServiceInstance)obj;
						dockerid =  si.getDockerid();
						break;
					}
					System.out.print("dockerid:"+dockerid);
					out.write("{\"dockerid\":\""+dockerid+"\"}");
					
					out.flush();
					out.close();
				}
				else{
					
					if(user!=null && user.getId()!=null){
						req.setAttribute("services", serviceManager.getAllServiceByOwnerId(user.getId()));
						req.getRequestDispatcher("/pages/console.jsp").forward(req, resp);
					}else{
						resp.sendRedirect("/");
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				out.print(e.getMessage());
				out.flush();
				out.close();
			}
		
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		User user = (User) req.getSession().getAttribute("user");
		if(user != null){
			String action = req.getParameter("action");
			//String username = req.getParameter("username");
			String username = user.getUsername();
			ServiceManager serviceManager = new ServiceManager();
			resp.setContentType("text/html;charset=utf-8");
			PrintWriter pw = resp.getWriter();   
			ObjectMapper mapper = new ObjectMapper();
			
			//CookieUtil cu = new CookieUtil(req, resp);
			//String token = cu.getCookieValue("token");
			//if(ConsoleService.checkUser(username, token)){
				if("create".equals(action)){
					System.out.println("create service start");
					String serviceName = req.getParameter("servicename");
				    String serviceContainerType = req.getParameter("runtime");
					String serviceType = req.getParameter("servicetype");
					String description = req.getParameter("description");
					String ownerName = req.getParameter("developer");
					String ownerQq = req.getParameter("qq");
					String ownerWechat = req.getParameter("wechat");
					String ownerPhone = req.getParameter("phone");
					Timestamp createDate = new Timestamp(new Date().getTime());
					Service service = serviceManager.createService(serviceName,
							serviceType,serviceContainerType, user.getId(), 
							description, "", createDate, createDate, ownerName, 
							ownerPhone, ownerQq, ownerWechat);
					if(service==null)
						pw.append("failed");
					else
					{
						mapper.writeValue(pw, service); 
					
					System.out.println(service.getServiceName());
					System.out.println(service.getDescription());
					System.out.println(service.getServiceType());
					System.out.println("create service end"+service.toString());
					}
		            
				}
				else if("delete".equals(action)){
					String serviceName = req.getParameter("servicename");
					boolean success = serviceManager.deleteService(serviceName, user.getId());
					pw.append("{success:"+String.valueOf(success)+"}");
				}
				else if("upload".equals(action)){
					System.out.println("upload start");
					//String serviceName = req.getParameter("upload-sev-name");
					//System.out.println(serviceName);
					String serviceName = "";
					String serviceContainerType = "";
					boolean success = false;
					//String savePath = "/data";
					String savePath = "";
					
					String msg = "";
					try{
						DiskFileItemFactory factory = new DiskFileItemFactory();
						ServletFileUpload upload = new ServletFileUpload(factory);
						upload.setHeaderEncoding("UTF-8");
						if(!ServletFileUpload.isMultipartContent(req)){
							return;
						}
						List<FileItem> list = upload.parseRequest(req);
						for(FileItem item : list){
							if(item.isFormField()){
								String name = item.getFieldName();
								String value = item.getString("UTF-8");
								if("servicename".equals(name)){
									serviceName = value;
									savePath=EnvironmentProperty.readConf("serviceRepoPath")+username+"/"+serviceName;
									System.out.println(savePath);
									File file = new File(savePath);
									if(!file.exists() && !file.isDirectory()){
										file.setExecutable(true);
										file.setReadable(true);
										file.setWritable(true);
										file.mkdirs();
									}
								}
								else if("serviceContainerType".equals(name)){
									serviceContainerType = value;
								}
							}
						}
								

						for(FileItem item : list){		
							
							if(!item.isFormField()){
								String filename = item.getName();
								if(filename == null || filename.trim().equals("")){
									continue;
								}
								filename = filename.substring(filename.lastIndexOf("\\")+1);
								System.out.println(filename);
								InputStream in =item.getInputStream();
								FileOutputStream out = new FileOutputStream(savePath + "/" + filename);
								System.out.print(savePath + "/" + filename);
								byte buffer[] = new byte[1024];
								int len = 0;
								while((len=in.read(buffer))>0){
									out.write(buffer, 0, len);
								}
								in.close();
								out.close();
								item.delete();
								success = true;
								if(filename.endsWith(".war"))
									FileUtil.unWar(savePath, filename);
								else if(filename.endsWith(".zip"))
									FileUtil.unZip(savePath, filename);
							}
						}
						
					}catch (Exception e){
						e.printStackTrace();
						success = false;
					}
					//String httpStr = "{\"sshport\": \"4018\", \"dockerid\": \"35a91ac6f520\", \"domain\": \"182.92.236.173\", \"code\": \"0\", \"port\": \"2021\"}";
					String httpStr = deploy(serviceName, serviceContainerType,user);
					pw.println(httpStr);  
			        System.out.println(httpStr);
					//req.getRequestDispatcher("/pages/console.jsp").forward(req, resp);
					System.out.print("upload end");
					
				}
				
				else if("uploadUI".equals(action)){
					System.out.println("uploadui start");
					String serverRealPath = System.getProperty("user.dir").replace("bin", "webapps");
					
					//String serviceName = req.getParameter("upload-sev-name");
					//System.out.println(serviceName);
					boolean success = false;
					//String savePath = "/data";
					String savePath = "";
					
					String msg = "";
					try{
						DiskFileItemFactory factory = new DiskFileItemFactory();
						ServletFileUpload upload = new ServletFileUpload(factory);
						upload.setHeaderEncoding("UTF-8");
						if(!ServletFileUpload.isMultipartContent(req)){
							return;
						}
						List<FileItem> list = upload.parseRequest(req);
						for(FileItem item : list){
							if(item.isFormField()){
								String name = item.getFieldName();
								String value = item.getString("UTF-8");
								if("servicename".equals(name)){
									String serviceName = value;
									
									savePath=serverRealPath+"/plugins/"+username+"/"+serviceName;
									System.out.println(savePath);
									File file = new File(savePath);
									if(!file.exists() && !file.isDirectory()){
										file.setExecutable(true);
										file.setReadable(true);
										file.setWritable(true);
										file.mkdirs();
									}
								}
							}
						}
								

						for(FileItem item : list){		
							
							if(!item.isFormField()){
								String filename = item.getName();
								if(filename == null || filename.trim().equals("")){
									continue;
								}
								filename = filename.substring(filename.lastIndexOf("\\")+1);
								System.out.println(filename);
								InputStream in =item.getInputStream();
								FileOutputStream out = new FileOutputStream(savePath + "/" + filename);
								System.out.print(savePath + "/" + filename);
								byte buffer[] = new byte[1024];
								int len = 0;
								while((len=in.read(buffer))>0){
									out.write(buffer, 0, len);
								}
								in.close();
								out.close();
								item.delete();
								success = true;
							}
						}
						
					}catch (Exception e){
						e.printStackTrace();
						success = false;
					}
					pw.write("{\"success\":"+success+"}");
					//req.getRequestDispatcher("/pages/console.jsp").forward(req, resp);
					System.out.print("uploadui end");
					
					
				}
				
				else if("deploy".equals(action)){
					System.out.print("deploy start");
					String serviceName = req.getParameter("servicename");
					String serviceContainerType = req.getParameter("serviceContainerType");
					CloseableHttpClient httpclient = HttpClients.createDefault();
					String httpStr = null;  
			        HttpPost httpPost = new HttpPost(EnvironmentProperty.readConf("deployUrl"));  
			        CloseableHttpResponse response = null;  
			        Map<String, Object> params = new HashMap<String, Object> ();
			        System.out.println(serviceContainerType+"\n"+user.getUsername()+"\n"+ user.getToken()+"\n"+
			        		serviceName
			        		);
			        String type = "";
			        if("javaweb".equals(serviceContainerType))
			        	type = "tomcat";
			        String path = "/"+user.getUsername()+"/"+serviceName+"/";
			        params.put("action", "startservice");
			        params.put("type", type);
			        params.put("path", path);
			        params.put("node", 1);
			        String dockerid = "";
			        String domain = "";
			        String port = "";
			        String sshport = "";
			        String address = "";
			        try {  
			            httpPost.setConfig(RequestConfig.DEFAULT);  
			            List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());  
			            for (Map.Entry<String, Object> entry : params.entrySet()) {  
			                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry  
			                        .getValue().toString());  
			                pairList.add(pair);  
			            }  
			            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));  
			            response = httpclient.execute(httpPost);  
			            System.out.println(response.toString());  
			            HttpEntity entity = response.getEntity();  
			            httpStr = EntityUtils.toString(entity, "UTF-8");
			            
			            try {
							JSONObject jsonObject = new JSONObject(httpStr);
							dockerid = jsonObject.getString("dockerid");
							domain = jsonObject.getString("domain");
							port = jsonObject.getString("port");
							sshport = jsonObject.getString("sshport");
							address = domain+":"+port;
							Service service = serviceManager.findByServiceName(serviceName);
							serviceManager.deleteAllServiceInstance(service);
							serviceManager.addServiceInstance(dockerid,service,domain,Integer.parseInt(port),Integer.parseInt(sshport));
							serviceManager.setAddress(address, serviceName, user.getId());
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        }catch (IOException e) {  
			            e.printStackTrace();  
			        } finally {  
			            if (response != null) {  
			                try {  
			                    EntityUtils.consume(response.getEntity());  
			                } catch (IOException e) {  
			                    e.printStackTrace();  
			                }  
			            }  
			        }  
			        pw.println(httpStr);  
			        System.out.println(httpStr);
			        System.out.print("deploy end");
			        checkStatus(serviceName, user.getId(),dockerid);
			        pw.flush();
					pw.close();
			        
				}
			
				
				if(!"deploy".equals(action)){
					pw.flush();
					pw.close();
				}
				
			}
	}
		
	//}

	

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}
	public String deploy(String serviceName, String serviceContainerType,User user){
		System.out.println("deploy start");
		System.out.println(serviceName+" "+serviceContainerType);
		ServiceManager serviceManager = new ServiceManager();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String httpStr = null;  
        HttpPost httpPost = new HttpPost(EnvironmentProperty.readConf("deployUrl"));  
        CloseableHttpResponse response = null;  
        Map<String, Object> params = new HashMap<String, Object> ();
        System.out.println(serviceContainerType+"\n"+user.getUsername()+"\n"+ user.getToken()+"\n"+
        		serviceName
        		);
        String type = "";
        if("javaweb".equals(serviceContainerType))
        	type = "tomcat";
        String path = "/"+user.getUsername()+"/"+serviceName+"/";
        params.put("action", "startservice");
        params.put("type", type);
        params.put("path", path);
        params.put("node", 0);
        String dockerid = "";
        String domain = "";
        String port = "";
        String sshport = "";
        String address = "";
        try {  
            httpPost.setConfig(RequestConfig.DEFAULT);  
            List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());  
            for (Map.Entry<String, Object> entry : params.entrySet()) {  
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry  
                        .getValue().toString());  
                pairList.add(pair);  
            }  
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));  
            response = httpclient.execute(httpPost);  
            System.out.println(response.toString());  
            HttpEntity entity = response.getEntity();  
            httpStr = EntityUtils.toString(entity, "UTF-8");
            
            try {
				JSONObject jsonObject = new JSONObject(httpStr);
				dockerid = jsonObject.getString("dockerid");
				domain = jsonObject.getString("domain");
				port = jsonObject.getString("port");
				sshport = jsonObject.getString("sshport");
				address = domain+":"+port;
				Service service = serviceManager.findByServiceName(serviceName);
				serviceManager.addServiceInstance(dockerid,service,domain,Integer.parseInt(port),Integer.parseInt(sshport));
				serviceManager.setAddress(address, serviceName, user.getId());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (response != null) {  
                try {  
                    EntityUtils.consume(response.getEntity());  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }   
        System.out.println(httpStr);
        System.out.print("deploy end");
        checkStatus(serviceName, user.getId(),dockerid);
        return httpStr;
	}
	public void checkStatus(final String serviceName, final Integer ownerId,final String dockerid){
		
		Timer timer = new Timer();  
        timer.schedule(new TimerTask() {  
            public void run() {
            	 System.out.println("timer start");
            	ServiceManager serviceManager = new ServiceManager();
            	boolean status =serviceManager.checkStatus(serviceName, ownerId);
            	System.out.println(status);
            	if(!status){
            		CloseableHttpClient httpclient = HttpClients.createDefault();
					String httpStr = null;  
			        HttpPost httpPost = new HttpPost(EnvironmentProperty.readConf("deployUrl"));  
			        CloseableHttpResponse response = null;  
			        Map<String, Object> params = new HashMap<String, Object> ();
			        params.put("action", "delete");
			        params.put("dockerid",dockerid);
			        try {  
			            httpPost.setConfig(RequestConfig.DEFAULT);  
			            List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());  
			            for (Map.Entry<String, Object> entry : params.entrySet()) {  
			                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry  
			                        .getValue().toString());  
			                pairList.add(pair);  
			            }  
			            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));  
			            response = httpclient.execute(httpPost);  
			            System.out.println(response.toString());  
			            HttpEntity entity = response.getEntity();  
			            httpStr = EntityUtils.toString(entity, "UTF-8");
			            System.out.println(httpStr);
			        }catch (IOException e) {  
			            e.printStackTrace();  
			        } finally {  
			            if (response != null) {  
			                try {  
			                    EntityUtils.consume(response.getEntity());  
			                } catch (IOException e) {  
			                    e.printStackTrace();  
			                }  
			            }  
			        }  
            	}
            }  
        }, 1000*60*60*24);
		
	}
	
	

}
