package com.towne.framework.common.service;



import com.towne.framework.common.model.Trader;
import com.towne.framework.hibernate.model.Moment;
import com.towne.framework.springmvc.model.MomentV;

public interface IFacadeService extends IService<Moment>  {
	public MomentV findPages(Trader trader, long id);
}
