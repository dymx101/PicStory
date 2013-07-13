package com.towne.data.mongodb.examples.hello;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import com.towne.data.mongodb.examples.hello.domain.Account;
import com.towne.data.mongodb.examples.hello.domain.Person;

@Repository
public class HelloMongo {

	@Autowired
	MongoOperations mongoOperations;

	public void run() {

		if (mongoOperations.collectionExists(Person.class)) {
			mongoOperations.dropCollection(Person.class);
		}

		mongoOperations.createCollection(Person.class);

		Person p = new Person("John", 39);
		Account a = new Account("1234-59873-893-1", Account.Type.SAVINGS, 123.45D);
		p.getAccounts().add(a);

		mongoOperations.insert(p);
//		mongoOperations.remove(new Query(Criteria.where("id").is("51dba59c03643dfa591ecb35")), Person.class);
//		new Query(Criteria.where("id").is("4ffe3486b41f8ed41269a729")),User.class
		List<Person> results = mongoOperations.findAll(Person.class);
		System.out.println("Results: " + results);
	}

}
