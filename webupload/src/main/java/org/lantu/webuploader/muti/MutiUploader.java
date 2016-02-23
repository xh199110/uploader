package org.lantu.webuploader.muti;

import java.io.File;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.lantu.webuploader.ContentTypeFilter;
import org.lantu.webuploader.FileInfo;
import org.lantu.webuploader.UpConstants;
import org.lantu.webuploader.core.AbstractUploader;
import org.lantu.webuploader.core.DefaultFileInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 多文件上传实例
 * @author xiaohua
 */
public class MutiUploader extends AbstractUploader{
	
	private static Logger logger=LoggerFactory.getLogger(MutiUploader.class);


	private void processUploadedFile(FileItem item,List<FileInfo> infos) throws Exception {
		if (!item.isFormField()) {
			//封装文件信息
			DefaultFileInfo fileInfo=new DefaultFileInfo();
			fileInfo.setOrginlName(item.getName());
			fileInfo.setContentType(item.getContentType());
			String result = validate(item);
            if (!result.equals(UpConstants.SUCCESS)) {
                setStatus(UpConstants.UPLOAD_ERROR);
                fileInfo.setStatus(result);
                fileInfo.setFile(null);
                fileInfo.setUrl(null);
                infos.add(fileInfo);
                return;
            }
			try {
				File f = new File(getController().getFileDir(getRequest(), item.getName()) + getController().newName(item.getName()));
				item.write(f);
				fileInfo.setFile(f);
				String projectName=getRequest().getContextPath().substring(1, getRequest().getContextPath().length());
				//从项目名那截取
				fileInfo.setUrl(f.getAbsolutePath().substring(f.getAbsolutePath().indexOf(projectName)+projectName.length()));
				fileInfo.setStatus(UpConstants.SUCCESS);
				if(logger.isDebugEnabled()){
					logger.debug(f.getAbsolutePath());
				}
			} catch (Exception e) {
			  logger.error("文件上传错误！！",e);
				fileInfo.setStatus(UpConstants.UPLOAD_ERROR);
				setStatus(UpConstants.UPLOAD_ERROR);
			}
			infos.add(fileInfo);
		}
	}

	

	@Override
	protected void uploadFile(List<FileItem> items,List<FileInfo> infos) {
		for (FileItem f : items) {
			try {
				processUploadedFile(f,infos);
			 //执行参数
			} catch (Exception e) {
			   logger.error("文件上传发生错误", e);
				  setStatus(UpConstants.UPLOAD_ERROR);
			}
		}
	}


}
