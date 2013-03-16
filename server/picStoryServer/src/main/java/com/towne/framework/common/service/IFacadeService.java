package com.towne.framework.common.service;

import java.util.List;

import com.towne.framework.common.model.Trader;
import com.towne.framework.hibernate.bo.Moment;
import com.towne.framework.springmvc.model.MomentV;
import com.towne.framework.springmvc.model.PageV;

public interface IFacadeService {

	public Moment findById(Trader trader, long id);

	public void save(Trader trader, Moment t);

	public void deleteById(Trader trader, long id);

	public void delete(Trader trader, Moment t);

	public List<MomentV> query(Trader trader, String queryString,
			Object... values);

	public List<PageV> findPagesByMomentId(Trader trader, long id);
}
