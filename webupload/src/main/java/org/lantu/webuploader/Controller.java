package org.lantu.webuploader;

import java.io.File;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * 文件控制器
 * @author xiaohua
 */
public interface Controller {
	
	/**
	 * 得到保存的文件的Dir,为绝对路径<br/>
	 * 比如  /home/work/tomcat803/webapps/dsis/upload/xxxxx/
	 * @param request HttpServletRequest匹配
	 * @return File要保存的文件
	 */
	public String getFileDir(HttpServletRequest request,String orginalName);
	
	/**
	 * 重新命名接口
	 * @param orginalName  test.doc
	 * @return String 7fbc509ae6b529e002b697cd3e5d2995.doc
	 */
	public String newName(String orginalName);
	
	/**
     * 数据库存储定制方法
     * @param request HttpServletRequest
     * @param file File
     * @return String 返回数据库存储的路径
     */
    String urlFix(HttpServletRequest request, File file);
	
}
