//                            _ooOoo_  
//                           o8888888o  
//                           88" . "88  
//                           (| -_- |)  
//                            O\ = /O  
//                        ____/`---'\____  
//                      .   ' \\| |// `.  
//                       / \\||| : |||// \  
//                     / _||||| -:- |||||- \  
//                       | | \\\ - /// | |  
//                     | \_| ''\---/'' | |  
//                      \ .-\__ `-` ___/-. /  
//                   ___`. .' /--.--\ `. . __  
//                ."" '< `.___\_<|>_/___.' >'"".  
//               | | : `- \`.;`\ _ /`;.`/ - ` : | |  
//                 \ \ `-. \_ __\ /__ _/ .-` / /  
//         ======`-.____`-.___\_____/___.-`____.-'======  
//                            `=---='  
//  
//         .............................................  
//                  佛祖保佑             永无BUG 

/**
 * 
 */ 
package com.qingniao.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.qingniao.util.FastDFSClient;

/** 
* @author 作者: Jiang Song
* @version 创建时间：2017年12月11日 上午11:06:34 
* 类说明 
*/
@Controller
public class UploadImageController {
	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	public Map uploadImage(MultipartFile uploadFile){
		Map map=new HashMap<>();
		//获取文件拓展名
		String Filename = uploadFile.getOriginalFilename();
		String extName=Filename.substring(Filename.lastIndexOf(".")+1);
		//创建fastFDS客户端
		try {
			FastDFSClient fastDFSClient = new FastDFSClient("classpath:resource/tracker.conf");
			String path = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
			String url=IMAGE_SERVER_URL+path;
			map.put("error", 0);
			map.put("url", url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("error", 1);
			map.put("message", "图片上传失败！");
		}
		return map;
	}

}
 