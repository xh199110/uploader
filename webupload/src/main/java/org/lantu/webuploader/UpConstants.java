package org.lantu.webuploader;

/**
 * 上传常量
 * @author xh199110@outlook.com
 */
public interface UpConstants {
	
	
	/**  * 上传成功   SUCCESS */
	public final static String SUCCESS="UPLOAD_SUCCESS";
	/** * IO异常 UPLOAD_IO_ERROR */
	public final static String UPLOAD_IO_ERROR = "UPLOAD_IO_ERROR";
	/** 分片异常 FILE_CHUNK_ERROR */
	public final static String FILE_CHUNK_ERROR = "FILE_CHUNK_ERROR";
	/** 分片异常 FILE_CHUNK_UPLOAD_ERROR */
	public final static String FILE_CHUNK_UPLOAD_ERROR = "FILE_CHUNK_UPLOAD_ERROR";
	/** 文件上传总个数超出异常 LIMIT_UPLOADNUM_ERROR */
	public static final String LIMIT_UPLOADNUM_ERROR = "LIMIT_UPLOADNUM_ERROR";
	/** 文件上传总大小超出异常 LIMIT_SIZE_MAX_ERROR */
	public static final String ALL_SIZE_MAX_ERROR = "LIMIT_SIZE_MAX_ERROR";
	/**单个文件大小超出错误 SINGLE_FILE_SIZE_MAX_ERROR */
	public static final String SINGLE_FILE_SIZE_MAX_ERROR = "SINGLE_FILE_SIZE_MAX_ERROR";
	/** 文件已经上传 FILE_ALREADY_UPLOADED_ERROR **/
	public static String FILE_ALREADY_UPLOADED_ERROR="FILE_ALREADY_UPLOADED_ERROR";
	/** 文件不允许上传 FILE_DISALLOWD_ERROR**/
	public static String FILE_DISALLOWD_ERROR="FILE_DISALLOWD_ERROR";
	/** 空文件异常 FILE_NULL_ERROR**/
	public static String FILE_NULL_ERROR="FILE_NULL_ERROR";
	/** 文件上传目录异常 FILE_DIR_ERROR**/
	public static String FILE_DIR_ERROR="FILE_DIR_ERROR";
	/**文件上传错误*/
	public static final String UPLOAD_ERROR ="UPLOAD_ERROR";
	/**文件处理错误*/
	public static final String FILE_PROCESSE_ERROR ="FILE_PROCESSE_ERROR";
	
	
	
	/** 文件上传总  单位byte 默认50M  **/
	public static long ALL_FILE_SIZE = 52428800;
	/** 单个文件上传 单位byte默认5M SINGLE_FILE_SIZE_MAX **/
	public static long SINGLE_FILE_SIZE_MAX=5242880;
	
	/** 没有可用的处理器 NO_PROCESSOR_ERROR **/
	public static final String NO_PROCESSOR_ERROR = null;
	
	/**不是文件上传表单**/
	public static String NOT_FILE_ERROR="NOT_FILE_ERROR";
	
	/** 文件分片状态 此次上传只是分片上传  FILE_CHUNK**/
	public static String FILE_CHUNK="FILE_CHUNK";
	/** 文件上传编码 UTF-8 **/
	public static String CHARSET_UTF="UTF-8";
	
	/**上传之前 BEFORE_UPLOAD **/
	public static final String BEFORE_UPLOAD="BEFORE_UPLOAD";
	/**文件上传之后<br/>处理之前 <br/>  BEFORE_PROCESS **/
	public static final String BEFORE_PROCESS="BEFORE_PROCESS";
	/**  文件处理之后<br/>文件信息存入数据库之前 <br/> BEFORE_SAVEFILE_TO_DB  **/
	public static final String BEFORE_SAVEFILE_TO_DB="BEFORE_SAVEFILE_TO_DB";
	/**文件信息存入数据库之后 <br/>文件上传到分布式文件系统之前 <br/>BEFORE_FILE_TO_NFS **/
	public static final String BEFORE_FILE_TO_NFS="BEFORE_FILE_TO_NFS";
	/**文件发送到分布式之后<br/>处理文件 信息存入数据库之前 <br/>  BEFORE_SAVE_PROFILE_TO_DB **/
	public static final String BEFORE_SAVE_PROFILE_TO_DB="BEFORE_SAVE_PROFILE_TO_DB";
	/**处理文件信息存入数据库之后<br/>处理文件上传到文件系统之前 <br/> BEFORE_PROFILE_TO_NFS **/
	public static final String BEFORE_PROFILE_TO_NFS="BEFORE_PROFILE_TO_NFS";
	/**处理文件上传到文件系统之后<br/>  BEFORE_FINISHED**/
	public static final String BEFORE_FINISHED="BEFORE_FINISHED";
	/** 默认一次可上传的最大文件数量  MAX_FILE_NUM = 10 **/
	public static final int MAX_FILE_NUM = 10;
	/**单个文件空错误  FILE_EMPTY_ERROR = "FILE_EMPTY_ERROR" */
  public static final String FILE_EMPTY_ERROR = "FILE_EMPTY_ERROR";


	
	
	
	
}
