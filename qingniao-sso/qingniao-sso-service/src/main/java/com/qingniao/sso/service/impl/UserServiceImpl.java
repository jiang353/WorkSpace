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
package com.qingniao.sso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.qingniao.common.QingNiaoResult;
import com.qingniao.common.utils.JsonUtils;
import com.qingniao.jedis.JedisClient;
import com.qingniao.mapper.TbUserMapper;
import com.qingniao.pojo.TbUser;
import com.qingniao.pojo.TbUserExample;
import com.qingniao.pojo.TbUserExample.Criteria;
import com.qingniao.service.UserService;

/**
 * @author 作者: Jiang Song
 * @version 创建时间：2017年12月29日 上午11:35:33 类说明
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper userMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${USER_INFO}")
	private String USER_INFO;
	
	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.qingniao.service.UserService#checkData(java.lang.String,
	 * java.lang.Integer)
	 */
	@Override
	public QingNiaoResult checkData(String parm, Integer type) {
		// TODO Auto-generated method stub
		// 封装查询条件
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		if (type == 1) {
			// 用户名
			criteria.andUsernameEqualTo(parm);
		} else if (type == 2) {
			// 电话
			criteria.andPhoneEqualTo(parm);
		}
		List<TbUser> tbuser = userMapper.selectByExample(example);
		if (tbuser != null || tbuser.size() == 0) {
			// 表示不可用
			return QingNiaoResult.ok(true);
		}
		// 表示可用
		return QingNiaoResult.ok(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.qingniao.service.UserService#register(com.qingniao.pojo.TbUser)
	 * 注册
	 */
	@Override
	public QingNiaoResult register(TbUser user) {
		// TODO Auto-generated method stub

		// 非空验证
		if (StringUtils.isBlank(user.getUsername())) {
			QingNiaoResult.build(400, "用户名不可为空！");
		}
		if (StringUtils.isBlank(user.getPassword())) {
			QingNiaoResult.build(400, "密码不可为空！");
		}

		if ((boolean) checkData(user.getUsername(), 1).getData()) {
			QingNiaoResult.build(400, "用户名不可用！");
		}
		if ((boolean) checkData(user.getPhone(), 2).getData()) {
			QingNiaoResult.build(400, "电话不可用！");
		}

		// -***********************************
		user.setUpdated(new Date());
		user.setCreated(new Date());
		// 密码加密
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		// 保存数据
		userMapper.insert(user);
		return QingNiaoResult.ok();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.qingniao.service.UserService#loging(java.lang.String,
	 * java.lang.String) 登陆 单点登陆 需要把用户信息存到redis数据库 通过生成的唯一token
	 * 
	 * 原理 单点登录将用户登陆信息通过唯一的token保存至redis 同时将token写入到用户的cookie
	 * 然后以cookie里面的token判断用户是否登陆
	 */
	@Override
	public QingNiaoResult loging(String username, String password) {
		// TODO Auto-generated method stub
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> tbuser = userMapper.selectByExample(example);
		if(tbuser==null||tbuser.size()==0){
			return QingNiaoResult.build(400, "用户名不存在");
		}
		TbUser user = tbuser.get(0);
		String MD5_PWD=DigestUtils.md5DigestAsHex(password.getBytes());
		if(!MD5_PWD.equals(user.getPassword())){
			return QingNiaoResult.build(400, "密码错误");
		}
		//*******登陆成功******
		//生成token
		String token=UUID.randomUUID().toString();
		//安全考虑 不将密码存到redis
		user.setPassword(null);
		jedisClient.set(USER_INFO+":"+token, JsonUtils.objectToJson(user));
		//超时时间
		jedisClient.expire(USER_INFO+":"+token, SESSION_EXPIRE);
		//登陆成功 返回token
		return QingNiaoResult.ok(token);
	}

	/* (non-Javadoc)
	 * @see com.qingniao.service.UserService#getUserByToken(java.lang.String)
	 * 
	 * 通过token从redis获取用户
	 */
	@Override
	public QingNiaoResult getUserByToken(String token) {
		// TODO Auto-generated method stub
		String json = jedisClient.get(USER_INFO+":"+token);
		//查到数据
		if(StringUtils.isNotBlank(json)){
			//重新设置超时时间
			jedisClient.expire(USER_INFO+":"+token, SESSION_EXPIRE);
			TbUser tbUser = JsonUtils.jsonToPojo(json, TbUser.class);
			return QingNiaoResult.ok(tbUser);
		}
		//没有查到数据
		return QingNiaoResult.build(400, "请登陆");
	}

}
