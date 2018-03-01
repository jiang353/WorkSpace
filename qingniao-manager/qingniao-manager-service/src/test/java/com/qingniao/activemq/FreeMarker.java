//                            _ooOoo_  
//                           o8888888o  
//                           88" . "88  
//                           (| -_- |)  
//                            O/ = /O  
//                        ____/`---'/____  
//                      .   ' //| |// `.  
//                       / //||| : |||// /  
//                     / _||||| -:- |||||- /  
//                       | | /// - /// | |  
//                     | /_| ''/---/'' | |  
//                      / .-/__ `-` ___/-. /  
//                   ___`. .' /--.--/ `. . __  
//                ."" '< `.___/_<|>_/___.' >'"".  
//               | | : `- /`.;`/ _ /`;.`/ - ` : | |  
//                 / / `-. /_ __/ /__ _/ .-` / /  
//         ======`-.____`-.___/_____/___.-`____.-'======  
//                            `=---='  
//  
//         .............................................  
//                  佛祖保佑             永无BUG 

/**
 * 
 */ 
package com.qingniao.activemq;


import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import freemarker.template.Configuration;
import freemarker.template.Template;

/** 
* @author 作者: Jiang Song
* @version 创建时间：2017年12月24日 下午5:18:06 
* 类说明 
* FreeMarker 网页静态化测试代码
*/
public class FreeMarker {
	
	@Test
	public void freemarker() throws Exception{
		// 第一步：创建一个Configuration对象，直接new一个对象。构造方法的参数就是freemarker对于的版本号。
		Configuration configuration = new Configuration(Configuration.getVersion());
		// 第二步：设置模板文件所在的路径。
		configuration.setDirectoryForTemplateLoading(new File("D:/WorkSpace/qingniao-manager/qingniao-manager-service/src/main/webapp/WEB-INF/ftl"));
		// 第三步：设置模板文件使用的字符集。一般就是utf-8.
		configuration.setDefaultEncoding("utf-8");
		// 第四步：加载一个模板，创建一个模板对象。
		Template template=configuration.getTemplate("hello.ftl");
		// 第五步：创建一个模板使用的数据集，可以是pojo也可以是map。一般是Map。
		Map map=new HashMap<>();
		map.put("hello", "this spring freemarker test");
		// 第六步：创建一个Writer对象，一般创建一FileWriter对象，指定生成的文件名。
		Writer out = new FileWriter(new File("D:/hello.html"));
		// 第七步：调用模板对象的process方法输出文件。
		template.process(map, out);
		// 第八步：关闭流。
		out.close();
	}
	
	

}
 