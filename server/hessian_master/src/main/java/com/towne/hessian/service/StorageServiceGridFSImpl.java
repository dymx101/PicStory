package com.towne.hessian.service;

import java.io.InputStream;

import org.bson.types.ObjectId;
import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

public class StorageServiceGridFSImpl implements StorageService {
	private final GridFS gridFs;

	public StorageServiceGridFSImpl(DB gridfsDb) {
		gridFs = new GridFS(gridfsDb);
	}

	@Override
	public String save(InputStream inputStream, String contentType,
			String filename) {
		GridFSInputFile input = gridFs.createFile(inputStream, filename, true);
		input.setContentType(contentType);
		input.save();
		return input.getId().toString();
	}

	@Override
	public GridFSDBFile get(String id) {
		return gridFs.findOne(new ObjectId(id));
	}

	@Override
	public GridFSDBFile getByFilename(String filename) {
		return gridFs.findOne(filename);
	}
}