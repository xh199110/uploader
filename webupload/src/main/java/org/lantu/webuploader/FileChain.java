package org.lantu.webuploader;

/**
 * @author xiaohua
 */
public interface FileChain {
	/**
	 * 处理链
	 * @throws Exception 
	 */
	public void doProcess() throws Exception;
	
	/**
	 * 添加处理器
	 * @param processor
	 */
	public void addProcessor(FileProcessor processor);
	
}
