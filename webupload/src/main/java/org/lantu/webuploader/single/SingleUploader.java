package org.lantu.webuploader.single;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.lantu.webuploader.FileInfo;
import org.lantu.webuploader.UpConstants;
import org.lantu.webuploader.core.AbstractUploader;
import org.lantu.webuploader.core.DefaultFileInfo;
import org.lantu.webuploader.core.FileModuleUploadMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SingleUploader extends AbstractUploader {

	private static Logger logger = LoggerFactory.getLogger(SingleUploader.class);

	/** 文件合并信息存储对象 */
	FileModuleUploadMap fmum = FileModuleUploadMap.getInstance();

	@Override
	protected void uploadFile(List<FileItem> items, List<FileInfo> fileInfos) {
		FileItem item = null;
		if (items.size() >= 1) {// 如果有多个只处理一个
			item = items.get(0);
		}
		try {
			// 判度是否分片
			String chunk = getParams("chunk");// 当前分页序号
			String chunksSize = getParams("chunks");// 分片总页数
			if (chunk != null && chunksSize != null) { // 分片上传
				processUploadChunk(Integer.valueOf(chunk), Integer.valueOf(chunksSize), item, fileInfos);
			} else {
				processUploadedFile(item, fileInfos);// 单个上传
			}
			// 执行参数
		} catch (Exception e) {
			 logger.error("文件上传发生错误", e);
			setStatus(UpConstants.UPLOAD_ERROR);
		}
	}

	/**
	 * 分片上传
	 * 
	 * @param req
	 * @return File
	 * @throws IOException
	 */
	private void processUploadChunk(Integer chunk, Integer chunksSize, FileItem item, List<FileInfo> fileInfos)
			throws IOException {

		// 封装文件信息
		DefaultFileInfo fileInfo = new DefaultFileInfo();
		fileInfo.setOriginalName(item.getName());
		fileInfo.setContentType(item.getContentType());

		HttpServletRequest request = getRequest();
		// 用于区分临时文件的唯一性
		String sessionId = request.getSession().getId();
		// 上传文件存放路径
		File file = null;
		String saveDir = getController().getFileDir(request, item.getName());
		String md5Name = getController().newName(item.getName());
		// 上传文件存放路径
		String uploadFilePath = saveDir + md5Name;
		// 分卷名
		String partFileName = "." + sessionId + chunk + "." + md5Name + ".part";
		File uploadFile = new File(uploadFilePath);

		File partFile = new File(saveDir + partFileName);// 将分片文件写入磁盘
		try {
			item.write(partFile);
			// 判断是否合并
			boolean combineFalg = getCombineFlag(uploadFile.getPath(), chunksSize);
			// 合并
			if (combineFalg) {
				file = combine(sessionId, chunksSize, md5Name, saveDir);
				// 删除分片文件
				for (int i = 0; i < chunksSize; i++) {
					new File(partFileName).getAbsoluteFile().delete();
				}
				// 删除内存中的缓存
				delFileMapInfo(uploadFile.getPath());
			}
			//设置当前上传为分片状态
			setStatus(UpConstants.FILE_CHUNK);
		} catch (IOException e) {
			fileInfo.setStatus(UpConstants.FILE_CHUNK_ERROR);
			delFileMapInfo(uploadFile.getPath());
			logger.error("分片错误", e);
		} catch (Exception e) {
			fileInfo.setStatus(UpConstants.FILE_CHUNK_UPLOAD_ERROR);
			logger.error("分片上传错误", e);
		}
		fileInfo.setFile(file);
		// 文件上传合并成功
		if (file != null) {
			fileInfo.setStatus(UpConstants.SUCCESS);
			fileInfo.setUrl(getController().urlFix(getRequest(), file));
			if (logger.isDebugEnabled()) {
				logger.debug(file.getAbsolutePath());
			}
			fileInfos.add(fileInfo);
		}

	}

	// 是否可以上传
	private boolean getCombineFlag(String path, int chunks) {
		boolean combineFalg = true;
		// 设置缓存，标识该分片上传完成
		FileModuleUploadMap fmum = FileModuleUploadMap.getInstance();
		fmum.setfileModuleUpload(path);
		// 如果上传完成的分片文件不齐全，则标识不进行合并文件
		if (logger.isDebugEnabled()) {
			logger.debug(path + (" 当前数目 -----------" + fmum.getfileModuleUpload(path)));
			logger.debug(path + " 总数 --------------" + chunks);
		}
		if (fmum.getfileModuleUpload(path) != chunks) {
			combineFalg = false;
		}
		return combineFalg;
	}

	// 设置缓存，标识该分片上传完成
	private void delFileMapInfo(String path) {
		FileModuleUploadMap.getInstance().delfileModuleUpload(path);
	}

	/**
	 * 合并文件
	 * 
	 * @param sessionId
	 *            sessionId
	 * @param chunkSize
	 *            文件分片数
	 * @param orignFileName
	 *            源文件名
	 * @return File
	 * @throws IOException
	 */
	public File combine(String sessionId, Integer chunkSize, String orignFileName, String saveDir) throws IOException {
		// 放到 文件信息表中 ,首先获得目录id
		ArrayList<FileInputStream> al = new ArrayList<FileInputStream>();
		for (int i = 0; i < chunkSize; i++) {
			al.add(new FileInputStream(saveDir + "." + sessionId + i + "." + orignFileName + ".part"));
		}
		final Iterator<FileInputStream> it = al.iterator();
		Enumeration<FileInputStream> en = new Enumeration<FileInputStream>() {
			public boolean hasMoreElements() {
				return it.hasNext();// 从内部类中访问局部变量 it；需要被声明为最终类型
			}

			public FileInputStream nextElement() {
				return it.next();
			}
		};
		SequenceInputStream sq = new SequenceInputStream(en);
		FileOutputStream fout = new FileOutputStream(saveDir + orignFileName);
		byte[] by = new byte[1024 * 1024];
		int len = 0;
		while ((len = sq.read(by)) != -1) {
			fout.write(by, 0, len);
			fout.flush();
		}
		File file = new File(saveDir + File.separator + orignFileName);
		// 保存到文件信息中
		close(fout);
		sq.close();
		return file;
	}

	// 这个地方不同
	private void processUploadedFile(FileItem item, List<FileInfo> infos) throws Exception {
		if (!item.isFormField()) {
			// 封装文件信息
			DefaultFileInfo fileInfo = new DefaultFileInfo();
			fileInfo.setOriginalName(item.getName());
			fileInfo.setContentType(item.getContentType());
			try {
				File f = new File(getController().getFileDir(getRequest(), item.getName())
						+ getController().newName(item.getName()));
				item.write(f);
				fileInfo.setFile(f);
				fileInfo.setStatus(UpConstants.SUCCESS);
				if (logger.isDebugEnabled()) {
					logger.debug(f.getAbsolutePath());
				}
			} catch (Exception e) {
			    logger.error("文件上传错误！",e);
				fileInfo.setStatus(UpConstants.UPLOAD_ERROR);
				setStatus(UpConstants.UPLOAD_ERROR);
			}
			infos.add(fileInfo);
		}
	}

	private void close(FileOutputStream fileOutputStream) {
		if (fileOutputStream != null) { // 关闭流
			try {
				fileOutputStream.close();
			} catch (Exception e) {
				logger.error("文件流关闭失败 ", e);
			}
		}
	}

}
