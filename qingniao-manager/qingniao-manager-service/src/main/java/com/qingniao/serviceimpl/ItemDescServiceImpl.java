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
package com.qingniao.serviceimpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qingniao.common.QingNiaoResult;
import com.qingniao.mapper.TbItemDescMapper;
import com.qingniao.pojo.TbItemDesc;
import com.qingniao.service.ItemDescService;

/** 
* @author 作者: Jiang Song
* @version 创建时间：2017年12月11日 下午4:29:33 
* 类说明 
*/
@Service
@Transactional
public class ItemDescServiceImpl implements ItemDescService {
	
	@Autowired
	private TbItemDescMapper itemDescMapper;

	/* (non-Javadoc)
	 * @see com.qingniao.service.ItemDescService#getItemDesc(long)
	 */
	@Override
	public QingNiaoResult getItemDesc(long itemID) {
		// TODO Auto-generated method stub
		QingNiaoResult result=new QingNiaoResult();
		//执行查询
		TbItemDesc ByExample = itemDescMapper.selectByPrimaryKey(itemID);
		result.setData(ByExample);
		result.setStatus(200);
		return result;
	}

}
 