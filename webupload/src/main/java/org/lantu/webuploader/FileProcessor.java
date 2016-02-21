package org.lantu.webuploader;

import java.util.List;


/**
 * 文件处理器接口
 * @author xh199110@outlook.com
 */
public interface FileProcessor {

	/**
	 * 处理逻辑
	 * @param context Uploader
	 * @param fileInfos List<FileInfo>
	 */
	public void process(FileChain chain,Uploader uploader,List<FileInfo> fileInfos) throws Exception;
	
}
