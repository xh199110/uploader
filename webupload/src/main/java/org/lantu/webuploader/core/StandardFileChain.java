package org.lantu.webuploader.core;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.lantu.webuploader.FileChain;
import org.lantu.webuploader.FileInfo;
import org.lantu.webuploader.FileProcessor;
import org.lantu.webuploader.Uploader;

public class StandardFileChain implements FileChain {
	
	private List<FileInfo> fileInfos=null;
	
	private List<FileProcessor> processors = new ArrayList<FileProcessor>();
	
	// 记录当前执行的Processor
	private int processorIndex = 0;

	private Uploader uploader;
	
	public StandardFileChain(List<FileInfo> fileInfos,Uploader uploader) {
		this.fileInfos=fileInfos;
		this.uploader=uploader;
	}

	public void doProcess() throws Exception {
		if (processors!=null && processors.size() > 0 && processorIndex < processors.size()) {
				processors.get(processorIndex++).process(this,uploader,fileInfos);
		}
	}

	public void addProcessor(FileProcessor processor) {
		processors.add(processor);
	}

}
