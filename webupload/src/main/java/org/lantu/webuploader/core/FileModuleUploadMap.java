package org.lantu.webuploader.core;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于记录文件分片上传情况，利用唯一路径作为标识，缓存文件分片上传情况,
 * 使用者要考虑文件分片已上传后，什么时候删除缓存（包括上传成功和失败，放弃之类的情况）
 * @author Administrator
 *
 */
public class FileModuleUploadMap {

	private static final FileModuleUploadMap myFileModuleMap = new FileModuleUploadMap();
	private Map<String, Integer> fileModuleMap = new HashMap<String, Integer>();

	private FileModuleUploadMap() {
		
	}

	/**
	 * 唯一实例，用于操作
	 * @return
	 */
	public synchronized static FileModuleUploadMap getInstance() {
		return myFileModuleMap;
	}

	/**
	 * 缓存文件分片上传数据,当上传分片完成时加1
	 * @param path
	 * @param passWord
	 */
	public void setfileModuleUpload(String path) {
		Integer num = fileModuleMap.get(path);
		if(num == null){
			num = 1;
		}else{
			num++;
		}
		fileModuleMap.put(path, num);
	}
	
	/**
	 * 获取文件分片上传信息，并删除缓存
	 * @param path
	 * @return
	 */
	public Integer getfileModuleUpload(String path) {
		Integer info = fileModuleMap.get(path);
		return info;
	}
	
	/**
	 * 删除对应文件的缓存信息
	 * @param path
	 */
	public void delfileModuleUpload(String path) {
		fileModuleMap.remove(path);
	}
}

