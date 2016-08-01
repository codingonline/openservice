package pop2016.openservice.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import pop2016.openservice.model.Service;
import pop2016.openservice.model.ServiceAuthorization;
import pop2016.openservice.service.ServiceManager;
import pop2016.openservice.utils.DirectoryCopy;
import pop2016.openservice.utils.EnvironmentProperty;
@WebServlet("/openServiceAPI")
public class OpenServiceAPI extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public OpenServiceAPI() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setHeader("Access-Control-Allow-origin","*");
		String action = req.getParameter("action");
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		ServiceManager serviceManager = new ServiceManager();
		if("services".equals(action)){
			int userid = Integer.parseInt(req.getParameter("userid"));
			List<Service> services = serviceManager.getAllServiceByUserId(userid);
			ObjectMapper mapper = new ObjectMapper(); 
			out.print(mapper.writeValueAsString(services));
			out.flush();
			out.close();
		}
		else if("unauthedservices".equals(action)){
			int userid = Integer.parseInt(req.getParameter("userid"));
			List<Service> services = serviceManager.getAllUnauthedServiceByUserId(userid);
			ObjectMapper mapper = new ObjectMapper(); 
			
			out.print(mapper.writeValueAsString(services));
			out.flush();
			out.close();
		}
		else if("address".equals(action)){
			String serviceName = req.getParameter("servicename");
			String address = serviceManager.getAddress(serviceName);
			System.out.println("address:"+address);
			out.print("{\"address\":\"http://"+address+"/\"}");
			out.flush();
			out.close();
		}
		
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setHeader("Access-Control-Allow-origin","*");
		PrintWriter out = resp.getWriter();
		ServiceManager serviceManager = new ServiceManager();
		String action = req.getParameter("action");
		if("initWorkSpace".equals(action)){
			System.out.print("init workspace start");
			String appType = req.getParameter("apptype");
			String appOwnerName = req.getParameter("appowner");
			String appName = req.getParameter("appname");
			String serviceName = req.getParameter("servicename");
			String serviceOwnerName = req.getParameter("serviceowner");
			String appPath = EnvironmentProperty.readConf("appRepoBase")+
					appType+"/"+appOwnerName+"/"+appName;
			String serviceWorkSpacePath = EnvironmentProperty.readConf("serviceRepoPath")+serviceOwnerName+"/"+serviceName+"/workspace";
			DirectoryCopy dc = new DirectoryCopy(appPath, serviceWorkSpacePath);
			dc.copy();
			System.out.print("init workspace end");
		}
		else if("addservice".equals(action)){
			System.out.println("add start");
			int userid = Integer.parseInt(req.getParameter("userid"));
			int serviceid = Integer.parseInt(req.getParameter("serviceid"));
			ServiceAuthorization sevauth = serviceManager.addServiceAuthorization(serviceid, userid);
			ObjectMapper mapper = new ObjectMapper(); 
			out.print(mapper.writeValueAsString(sevauth));
			out.flush();
			out.close();
			System.out.println("add end");
		}
		else if("delservice".equals(action)){
			System.out.println("del start");
			int userid = Integer.parseInt(req.getParameter("userid"));
			int serviceid = Integer.parseInt(req.getParameter("serviceid"));
			boolean success = serviceManager.delServiceAuthorization(serviceid, userid);
			out.append("{success:"+String.valueOf(success)+"}");
			out.flush();
			out.close();
			System.out.println("del end");
		}
		else if("setstatus".equals(action)){
			System.out.println("setstatus start");
			int value= Integer.parseInt(req.getParameter("value"));
			int serviceId = Integer.parseInt(req.getParameter("serviceid"));
			boolean success = serviceManager.setStatus(serviceId, value);
			out.append("{success:"+String.valueOf(success)+"}");
			out.flush();
			out.close();
			System.out.println("setstatus end");
		}
		
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
