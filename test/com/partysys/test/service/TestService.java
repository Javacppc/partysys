package com.partysys.test.service;

import com.partysys.core.service.BaseService;
import com.partysys.test.entity.Person;

public interface TestService extends BaseService<Person>{
	public void say();
}
