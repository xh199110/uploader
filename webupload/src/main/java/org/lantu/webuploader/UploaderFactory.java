package org.lantu.webuploader;

import org.lantu.webuploader.muti.MutiUploader;
import org.lantu.webuploader.single.SingleUploader;

/**
 *  上传类的工厂方法
 * @author xiaohua
 */

public abstract class UploaderFactory {

	/**
	 * 多文件上传实例
	 * @return Uploader
	 */
	public static Uploader getMutiUploader(){
		return 	new MutiUploader();
	}
	
	
	/**
	 * 单文件断点续传实例
	 * @return Uploader
	 */
	public static Uploader getSingleUploader(){
		return new SingleUploader();
	}
	
}
