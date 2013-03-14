package com.towne.framework.hibernate.service;

import java.util.List;

import com.towne.framework.hibernate.model.Moment;



public interface MomentService {
		public void add(Object object);
		public void delete(long id);
		public void update(Object object);
		public List<Moment> query(String queryString);
		public Moment findById(long id);
}
