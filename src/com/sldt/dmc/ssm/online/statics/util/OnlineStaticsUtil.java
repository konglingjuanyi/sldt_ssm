package com.sldt.dmc.ssm.online.statics.util;

import java.util.Collection;
import java.util.List;

import org.jasig.cas.ticket.ServiceTicket;
import org.jasig.cas.ticket.Ticket;
import org.jasig.cas.ticket.registry.JBossCacheTicketRegistry;
import org.jasig.cas.ticket.registry.TicketRegistry;

import com.sldt.dmc.ssm.parms.bean.Parameter;
import com.sldt.dmc.ssm.parms.service.OnlineParmsService;
import com.sldt.dmc.ssm.web.init.BeanUtils;

public class OnlineStaticsUtil {

	/**
	 * 获取参数基数value
	 * @return p.getParamValue()
	 */
	public static int getBaseOnlineNum(){
		 OnlineParmsService onlineParmsService=(OnlineParmsService)BeanUtils.getBean("onlineParmsService");
		 List<Parameter> list=onlineParmsService.findBySql();
		 if (list.size()==0||list==null) {
			return 0;
		}else {
			 Parameter p=list.get(0);
			return Integer.parseInt(p.getParamValue());
		}
		 
		
	}
    public static String getOnlinePersons(){
    	TicketRegistry  ticketRegistry = (JBossCacheTicketRegistry)BeanUtils.getBean("ticketRegistry");
    	int unexpiredTgts = 0;
    	  try {
    	   	  final Collection<Ticket> tickets = ticketRegistry.getTickets();
              for (final Ticket ticket : tickets) {
                  if (ticket instanceof ServiceTicket) {
                  } else {
                      if (ticket.isExpired()) {
                      } else {
                          unexpiredTgts++;
                      }
                  }
              }
          } catch (final UnsupportedOperationException e) {
        	  System.out.println("------------统计票据信息出错---------------"+e.getMessage());
          }
          //return Integer.toString(unexpiredTgts);
    	  //20131115投产封板   在线人数 + 参数基数
    	  int  retval=unexpiredTgts;
    	  retval=getBaseOnlineNum()+unexpiredTgts;
          return retval+"";
    }
}
