package com.towne.framework.common.service;

import java.io.InputStream;

public interface StorageService {

	String store(InputStream inputStream, String contentType, String filename);

	String find(String filename);
}