package com.towne.hessian.service;

import java.io.InputStream;

import com.mongodb.gridfs.GridFSDBFile;

public interface StorageService {
	String save(InputStream inputStream, String contentType, String filename);

	GridFSDBFile get(String id);

	GridFSDBFile getByFilename(String filename);
}