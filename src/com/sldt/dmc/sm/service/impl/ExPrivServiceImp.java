package com.sldt.dmc.sm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sldt.dmc.sm.bean.ExPriv;
import com.sldt.dmc.sm.dao.ExPrivDao;
import com.sldt.dmc.sm.service.ExPrivService;
@Service(value = "exPrivService")
public class ExPrivServiceImp implements ExPrivService{

	@Resource(name="exPrivDao")
	private ExPrivDao exPrivDao;
	
	@Override
	public List<ExPriv> queryExPriv() {
		// TODO Auto-generated method stub   ExPriv
		List<Map<String,Object>>list=exPrivDao.queryExPriv();
		List<ExPriv> exPriv=new ArrayList<ExPriv>();
		for(int i=0;i<list.size();i++){
			ExPriv ep=new ExPriv();
			ep.setId(list.get(i).get("priv_id").toString());
			ep.setName(list.get(i).get("priv_name").toString());
			Object pprivId=list.get(i).get("p_priv_id");
			if(ep.getName().equals("元数据变更冲突")){
				System.out.println("pprivId="+pprivId);
			}
			if( pprivId==null){
				ep.setpId(null);		
			}else{
				ep.setpId(pprivId.toString());
			}
			List<Map<String,Object>>pList=exPrivDao.queryisParent(list.get(i).get("priv_id").toString());
			if(pList.size()<=0 ||"".equals(pList)){
				ep.setIsParent(null);
			}else{
				ep.setIsParent("true");
			}
			ep.setChecked(false);
			ep.setHalfCheck(null);
			ep.setOpen(false);
			exPriv.add(ep);
		}
		return exPriv;
	}

}
