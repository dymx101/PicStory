package com.towne.hessian.service;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;


public class StorageServiceGridFSImpl implements StorageService {

	@Autowired
	GridFsOperations gridFsTemplate;

	public String store(InputStream inputStream, String contentType,
			String filename) {
		DBObject metaData = new BasicDBObject();
		metaData.put("extra1", contentType);
		try {
			// inputStream = new
			// FileInputStream("/Users/towne/Desktop/QQ20130428-15.png");
			gridFsTemplate.store(inputStream, filename, "image/png", metaData);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Done");
		return filename;
	}

	public String find(String filename) {
		//QQ20130428-15.png
		List<GridFSDBFile> result = gridFsTemplate.find(new Query()
				.addCriteria(Criteria.where("filename").is(filename)));
		for (GridFSDBFile file : result) {
			System.out.println(file.getFilename());
			System.out.println(file.getContentType());
			try {
				file.writeTo("/Users/towne/Desktop/new-testing.png");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Done");
		return "Done";
	}

}