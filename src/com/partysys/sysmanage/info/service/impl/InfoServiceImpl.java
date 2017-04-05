package com.partysys.sysmanage.info.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.partysys.core.service.impl.BaseServiceImpl;
import com.partysys.sysmanage.info.dao.InfoDao;
import com.partysys.sysmanage.info.entity.Info;
import com.partysys.sysmanage.info.service.InfoService;
@Service("infoService")
public class InfoServiceImpl extends BaseServiceImpl<Info> implements InfoService{
	
	private InfoDao infoDao;
	
	@Autowired
	public void setInfoDao(InfoDao infoDao) {
		//将infoDao注入给BaseDao,这样BaseDao就可以操作数据库了
		super.setBaseDao(infoDao);
		this.infoDao = infoDao;
	}
	
	
	
}
