package com.towne.framework.springmvc.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.gridfs.GridFSDBFile;
import com.towne.framework.common.service.StorageService;

@Controller
@RequestMapping(value = "/storage")
public class StorageController {
    @Autowired
    private StorageService storageService;
 
    @RequestMapping(value = "/filename/{filename:.*}", method = RequestMethod.GET)
    public void getByfilename (@PathVariable (value="filename") String filename, HttpServletResponse response) throws IOException {
    	String result = storageService.find(filename);
        if (result!=null) {
//        	for (GridFSDBFile file : result) {
//    			try {
//    				System.out.println(file.getFilename());
//    				System.out.println(file.getContentType());
//    	            byte[] data = IOUtils.toByteArray(file.getInputStream());
//    	            response.setContentType(file.getContentType());
//    	            response.setContentLength((int)file.getLength());
//    	            response.getOutputStream().write(data);
//    	            response.getOutputStream().flush();
//    			} catch (IOException e) {
//    				e.printStackTrace();
//    			}
//    		}
        	
        } else {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }   
 
    @RequestMapping(value = "/store", method = RequestMethod.POST)
    public ResponseEntity<String> store (@RequestParam MultipartFile file, HttpServletRequest request) {
        try {
//            String storedId = storageService.save(file.getInputStream(), file.getContentType(), file.getOriginalFilename());
        	String filenameString = storageService.store(file.getInputStream(),"image/png", file.getOriginalFilename());
            String storedURL = "/storage/filename/" + filenameString;
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setLocation(new URI("http://127.0.0.1"));
            return new ResponseEntity<String>(storedURL, responseHeaders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}