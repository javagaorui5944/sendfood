package com.fenghuo.controller;

import java.io.File;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fenghuo.redisDao.ApkRedisDao;
@Controller
@RequestMapping("/apk")
public class ApkUpdateController {
	@Autowired
	ApkRedisDao apkdao;
	@Value("#{configProperties['apk']}")
	private String apk_version;
	
	
	
	/*@RequestMapping("/update")
	@ResponseBody
	public void update(String key, String path) throws Exception {
		setFile("test", "D:\\test.txt");
		File file = getFile("test");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String record = null;
		while ((record = br.readLine()) != null) {
			System.out.println("record:" + record);
		}
	}*/
	/**
	 * 
	 * @param res
	 * @description 下载apk。当redis里面没有的时候，就从本地下载并保存到redis中
	 * @throws Exception
	 */
	@RequestMapping("/download")
	@ResponseBody
	public void get(HttpServletResponse res) throws Exception {
		 OutputStream os = res.getOutputStream();  
		 File file=null;
		    try {  
		        res.reset();  
		        res.setHeader("Content-Disposition", "attachment; filename=零食蜂.apk");  
		        res.setContentType("application/octet-stream; charset=utf-8");
		        file=apkdao.getFile(apk_version);
		        System.out.println(file);
		        if(file==null){
		        	file=new File("sendfood.apk");
		        	apkdao.setFile(apk_version, "sendfood.apk");
		        	}
		        os.write(FileUtils.readFileToByteArray(file));  
		        os.flush();  
		    } finally {  
		        if (os != null) {  
		            os.close();  
		        }  
		    }  
	}
	/**
	 * 
	 * @param software_version
	 * @return 是否是最新版本的app
	 * @throws Exception
	 */
	@RequestMapping(value="/check" ,method=RequestMethod.GET)
	@ResponseBody
	public boolean check_version(String software_version) throws Exception {
		return apk_version.equals(software_version);
	}
	/**
	 * 
	 * @param software_version
	 * @return 是否是最新版本的app
	 * @throws Exception
	 */
	@RequestMapping(value="/update" ,method=RequestMethod.GET)
	@ResponseBody
	public void update(String software_version) throws Exception {
		apkdao.delete(apk_version);
	}
}
