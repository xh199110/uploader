package org.lantu.webuploader.core;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 文件上传工具类
 * @author xiaohua
 */
public class FileUtils {
	
		private static SimpleDateFormat YMD=new SimpleDateFormat("yyyy/MM/dd");
		
		/**
		 * yyyy/MM/dd
		 * @param date
		 * @return string
		 */
		public static String formatYMD(Date date){
			return YMD.format(date);
		}
	
}
