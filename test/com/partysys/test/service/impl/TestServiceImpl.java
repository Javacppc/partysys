package com.partysys.test.service.impl;

import org.springframework.stereotype.Service;

import com.partysys.core.service.impl.BaseServiceImpl;
import com.partysys.test.entity.Person;
import com.partysys.test.service.TestService;
@Service("testService")
public class TestServiceImpl extends BaseServiceImpl<Person> implements TestService{

	@Override
	public void say() {
		System.out.println("整合成功！。。");
	}
	
}
