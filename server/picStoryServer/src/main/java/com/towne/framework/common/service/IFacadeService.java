package com.towne.framework.common.service;

import java.util.List;

import com.towne.framework.common.model.Trader;
import com.towne.framework.hibernate.model.Moment;
import com.towne.framework.springmvc.model.MomentV;

public interface IFacadeService {
	
	public Moment findById(Trader trader, long id);

	public void update(Trader trader, Moment t);

	public void add(Trader trader, Moment t);

	public void deleteById(Trader trader, long id);

	public void delete(Trader trader, Moment t);

	public List<Moment> query(Trader trader, String queryString);

	public MomentV findPages(Trader trader, long id);
}
