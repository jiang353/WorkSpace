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
package com.qingniao.test;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

/** 
* @author 作者: Jiang Song
* @version 创建时间：2017年12月14日 上午10:43:21 
* 类说明 
* jedis测试
*/


public class JedisTest {
	
	public void jedis(){
		//1获取jedis实例
		Jedis jedis=new Jedis("192.168.128.128", 6379);
		//2操作jedis数据库
		jedis.set("name", "姜松");
		jedis.close();
	}
	
	//jedis连接池
	public void jedisPool(){
		JedisPool jedisPool=new JedisPool("192.168.128.128", 6379);
		Jedis resource = jedisPool.getResource();
		resource.set("name", "Jiang Song");
		resource.close();
		//实际操作中连接池不用关闭
		jedisPool.close();
	}
	
	
	//iedis集群测试
	public void jedisDemo(){
		Set<HostAndPort> nodes=new HashSet<>();
		nodes.add(new HostAndPort("192.168.128.128", 7001));
		nodes.add(new HostAndPort("192.168.128.128", 7002));
		nodes.add(new HostAndPort("192.168.128.128", 7003));
		nodes.add(new HostAndPort("192.168.128.128", 7004));
		nodes.add(new HostAndPort("192.168.128.128", 7005));
		nodes.add(new HostAndPort("192.168.128.128", 7006));
		JedisCluster cluster=new JedisCluster(nodes);
		cluster.set("index", "jiang song");
		System.out.println(cluster.get("index"));
	}

}
 