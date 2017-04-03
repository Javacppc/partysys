package com.partysys.sysmanage.party.service.impl;

import org.springframework.stereotype.Service;

import com.partysys.core.dao.impl.BaseDaoImpl;
import com.partysys.sysmanage.party.entity.Partymember;
import com.partysys.sysmanage.party.service.PartymemberService;
@Service("partymemberService")
public class PartymemberServiceImpl extends BaseDaoImpl<Partymember> implements PartymemberService{

}
