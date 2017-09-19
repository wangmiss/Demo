
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * IP获取工具类
 * 
 */
public abstract class IpUtil {
	
	/** 日志 */
	private static Logger log = Logger.getLogger(IpUtil.class);
	
	private static InetAddress localHost;
	
	static{
		try {
			localHost = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			log.error(e);
		}
	}
	
	/**
	 * 获取IP地址
	 * 
	 * @param request
	 * @return IP
	 */
	public static String getIpAddr(HttpServletRequest request) {
		if (request == null) {
			return "unknown";
		}
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * @return 主机名称
	 */
	public static final String getHostName() {
		if(localHost!=null){
			return localHost.getHostName();
		}
		return "UnknownHostName";
	}
	
	/**
	 * @return 主机地址
	 */
	public static final String getHostAddress(){
		if(localHost!=null){
			return localHost.getHostAddress();
		}
		return "UnknownHostAddr";
	}
	
}
