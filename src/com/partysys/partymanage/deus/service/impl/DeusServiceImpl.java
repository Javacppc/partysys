package com.partysys.partymanage.deus.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.partysys.core.service.impl.BaseServiceImpl;
import com.partysys.partymanage.deus.dao.DeusDao;
import com.partysys.partymanage.deus.entity.Deus;
import com.partysys.partymanage.deus.service.DeusService;
@Service("deusService")
public class DeusServiceImpl extends BaseServiceImpl<Deus> implements DeusService{
	private DeusDao deusDao;
	
	@Autowired
	public void setDeusDao(DeusDao deusDao) {
		super.setBaseDao(deusDao);
		this.deusDao = deusDao;
	}
}
