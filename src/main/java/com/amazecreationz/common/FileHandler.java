package com.amazecreationz.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileHandler {
	private String DATA_DIR;
	
	public FileHandler(String location){
		DATA_DIR = location;
	}
	
	protected boolean createFile(String fileName){
    	return true;
    }
    
    protected boolean uploadFile(HttpServletRequest request){
    	if(ServletFileUpload.isMultipartContent(request)){
			try {
				List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
				for(FileItem item : multiparts){
					if(!item.isFormField()){
						String name = new File(item.getName()).getName();
						item.write( new File(DATA_DIR + File.separator + name));
					}
				}
				return true;
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
    	return false;
    }
    
    protected HttpServletResponse downloadFile(String fileName, HttpServletResponse response){
    	try {
			File file = new File(DATA_DIR +"/" +fileName);
			response.setContentType("application/octet-stream");
			response.setContentLength((int) file.length());
			response.setHeader( "Content-Disposition",String.format("attachment; filename=\"%s\"", file.getName()));
			OutputStream out = response.getOutputStream();
			FileInputStream in = new FileInputStream(file);
			byte[] buffer = new byte[4096];
			int length;
			while ((length = in.read(buffer)) > 0){
			    out.write(buffer, 0, length);
			}
			in.close();
			out.flush();
		} catch(Exception e){
			e.printStackTrace();
		}
    	return response;
    }
    
    protected boolean moveFile(String fileName, String location){
    	return true;
    }
    
    protected boolean renameFile(String fileName, String newFileName){
    	return true;
    }
    
    protected boolean removeFile(String fileName){
    	return true;
    }
}
