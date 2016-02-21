
package org.lantu.webuploader;
/**
 * 这里是对文件ContentType的拦截或者说过滤
 * @author xh199110@outlook.com
 */
public interface ContentTypeFilter {

	/**
	 * 是否上传此文件<br/>
	 * 默认可以上传audio，zip，rar，image，video，doc <br/>
	 * 并且必须后缀名和contentType必须都匹配
	 * @param orginalName
	 * @param contentType
	 * @return boolean 
	 */
	public boolean isAccept(String orginalName,String contentType);
	
}
