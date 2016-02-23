package org.lantu.webuploader.core;

import java.io.File;

import org.lantu.webuploader.FileInfo;

/**
 * fileInfo的默认实现
 * @author xiaohua
 *
 */
public class DefaultFileInfo implements FileInfo {
	
	private File file=null;
	
	private String status=null;
	
	private String orginlName=null;
	
	private String contentType=null;

	private String url;
	

	public File getFile() {
		return this.file;
	}

	public String getStatus() {
		return this.status;
	}

	public String getOrginlName() {
		return this.orginlName;
	}

	public String getContentType() {
		return this.contentType;
	}


	public void setFile(File f) {
		this.file = f;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setOrginlName(String orginlName) {
		this.orginlName = orginlName;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url=url;
	}
	
}
