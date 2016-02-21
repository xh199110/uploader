package org.lantu.webuploader;

/**
 * 文件封装接口
 * @author xh199110@outlook.com
 */
import java.io.File;
/**
	 *  文件信息
	 * @author xiaohua
	 *
	 */
	public interface FileInfo {
			/**
			 * 得到已经上传的文件
			 * @return File
			 */
			public File getFile();
			
			/**
			 * 得到此文件的虚拟路径 <br/>
			 * /upload/2016/02/02/28ba0b326d771915707ea0f12fa5cbd2.png
			 * @return
			 */
			public String getUrl();
			
			/**
			 * 得到异常信息
			 * @return
			 */
			public Throwable getThrowable();
			
			/**
			 * 此文件状态信息
			 * @return String
			 */
			public String getStatus();
			
			/**
			 * 得到源文件的信息
			 * @return String
			 */
			public String getOrginlName();
			
			/**
			 * 
			 * @return
			 */
			public String getContentType();
			
			
	}
