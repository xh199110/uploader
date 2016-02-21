package org.lantu.webuploader;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * 上传文件接口
 * @author xh199110@outlook.com
 *
 */
public interface Uploader {

	/**
	 * 设置过滤器
	 * @param filter
	 */
	public void setFilter(ContentTypeFilter filter);
	/**
	 * 设置控制器
	 * @param controller
	 */
	public void setController(Controller controller);

	/**
	 * 添加文件处理器
	 * @param processor
	 */
	public void addFileProcessor(FileProcessor processor);
	
	/**
	 * 初始化上传组件，解析参数，分离文件类型，初始化相关子组件，<br/>
	 * 使用的时候必须先初始化
	 * @param request
	 */
	public void init(HttpServletRequest request);
	
	/**
	 * 上传接口
	 * @param request
	 */
	public List<FileInfo> upload();
	
	/**
	 * 设置参数
	 * @param key
	 * @return String
	 */
	public String getParams(String key);
	
	/**
	 * 设置参数
	 * @param key
	 * @param value
	 */
	public void setParams(String key,String value);
	
	/**
	 * 单个可上传文件大小
	 */
	public void setSingleFileSize(long singleFileSize);
	
	/**
	 * 可上传总文件大小
	 */
	public void setAllFileSize(long allFileSize);
	
	/**
	 * 一次上传最大文件数量
	 */
	public void setMaxFileNum(int maxFileNum);
	
	/**
	 * 得到抛出的异常
	 * @return Throwable
	 */
	public Throwable getThrowable();
	
	/**
	 * 返回此次操作结果
	 * @return String
	 */
	public String getStatus();
	
	/**
	 * 返回请求对象
	 * @return HttpServletRequest
	 */
	public HttpServletRequest getRequest();
}