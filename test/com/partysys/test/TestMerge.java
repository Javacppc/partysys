package com.partysys.test;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.partysys.sysmanage.party.dao.PartymemberDao;
import com.partysys.sysmanage.party.dao.impl.PartymemberDaoImpl;
import com.partysys.sysmanage.party.entity.Partymember;
import com.partysys.test.entity.Person;
import com.partysys.test.service.TestService;


public class TestMerge {
	private ClassPathXmlApplicationContext ctx = null;
	@Before
	public void loadCtx() {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		/*TestService ts = (TestService) ctx.getBean("testService");
		ts.say();*/
	}
	@Test
	public void testSpring() {
		TestService ts = (TestService) ctx.getBean("testService");
		ts.say();
	}
	@Test
	public void testHibernate() {
		SessionFactory sf = (SessionFactory) ctx.getBean("sessionFactory");
		Session session = sf.openSession();
		Transaction trans = session.beginTransaction();
		session.save(new Person("王金秋"));
		trans.commit();
		session.close();
	}
	@Test
	public void testServiceAndDao() {
		//TestService ts = (TestService) ctx.getBean("testService");
		//ts.save(new Person("趙寶妹"));
		//System.out.println(ts.findPerson("8a3035385abc843f015abc8441210000").toString());
		Partymember m = new Partymember();
		m.setName("zkf");
		m.setPassword("zkf");
		List<String> list = new ArrayList<>();
		list.add("zxd");
		list.add("lxl");
		m.setCultivate(list);
		
		PartymemberDao p = new PartymemberDaoImpl();
		p.save(m);
		p.delete(m);
	}
	
	@Test
	public void testCascade() {
		Partymember m = new Partymember();
		m.setName("zkf");
		m.setPassword("zkf");
		List<String> list = new ArrayList<>();
		list.add("zxd");
		list.add("lxl");
		m.setCultivate(list);
		
		PartymemberDao p = new PartymemberDaoImpl();
		p.save(m);
		p.delete(m);
	}
}
