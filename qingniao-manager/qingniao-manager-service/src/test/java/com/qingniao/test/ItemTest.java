package com.qingniao.test;

import org.junit.Test;



/*@ContextConfiguration(locations={"classpath:spring/applicationContext-*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)*/
public class ItemTest {
	
	//@Autowired
	//private ItemService itemService;

	//@Test
	/*public void demo(){
		TbItemExample example=new TbItemExample();
		EasyUIDataGridResult list = itemService.selectByExample(example, 1, 10);
		System.out.println(list.getRows().get(2));;
	}*/
	
	
	@Test
	public void test(){
		String domainName = null;

        String serverName = "https://zhidao.baidu.com/question/647089296843618285.html";
        if (serverName == null || serverName.equals("")) {
            domainName = "";
        } else {
            serverName = serverName.toLowerCase();
            serverName = serverName.substring(7);
            final int end = serverName.indexOf("/");
            serverName = serverName.substring(0, end);
            final String[] domains = serverName.split("\\.");
            int len = domains.length;
            if (len > 3) {
                // www.xxx.com.cn
                domainName = "." + domains[len - 3] + "." + domains[len - 2] + "." + domains[len - 1];
            } else if (len <= 3 && len > 1) {
                // xxx.com or xxx.cn
                domainName = "." + domains[len - 2] + "." + domains[len - 1];
            } else {
                domainName = serverName;
            }
        }

        if (domainName != null && domainName.indexOf(":") > 0) {
            String[] ary = domainName.split("\\:");
            domainName = ary[0];
        }
        System.out.println(domainName);
		
	}
}
