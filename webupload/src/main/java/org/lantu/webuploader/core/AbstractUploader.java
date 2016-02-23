package org.lantu.webuploader.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.lantu.webuploader.ContentTypeFilter;
import org.lantu.webuploader.Controller;
import org.lantu.webuploader.FileChain;
import org.lantu.webuploader.FileInfo;
import org.lantu.webuploader.FileProcessor;
import org.lantu.webuploader.UpConstants;
import org.lantu.webuploader.Uploader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件上传抽象类
 * @author xh199110@outlook.com
 */
public abstract class AbstractUploader implements Uploader {

    private static Logger logger = LoggerFactory.getLogger(AbstractUploader.class);

    private HttpServletRequest request = null;

    private Map<String, String> params = new HashMap<String, String>();

    private StandardFileChain chain = null;
    /** 文件存放控制器 */
    private Controller controller = null;

    private ContentTypeFilter filter;
    /** 文件信息 */
    private List<FileInfo> fileInfos;
    /**
     * 文件信息
     */
    private List<FileItem> fileItems = new ArrayList<FileItem>();
    /**
     * 允许单个文件上传的大小
     */
    private long singleFileSize = UpConstants.SINGLE_FILE_SIZE_MAX;
    /**
     * 允许总共文件上传的大小
     */
    private long allFileSize = UpConstants.ALL_FILE_SIZE;
    /**
     * 允许文件上传个数
     */
    private int maxFileNum = UpConstants.MAX_FILE_NUM;

    /**
     * 上传状态
     */
    private String status = UpConstants.SUCCESS;

    public AbstractUploader() {
        this.fileInfos = new LinkedList<FileInfo>();
    }

    public void init(HttpServletRequest request) {
        long allSize = 0;
        int fileNum = 0;
        this.request = request;
        this.fileInfos = new ArrayList<FileInfo>();
        // 如果没有设置，就设置标准的filter
        if (filter == null) {
            filter = new DefaultContentTypeFilter();
        }
        try {
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            // 不是文件上传
            if (!isMultipart) {
                status = UpConstants.NOT_FILE_ERROR;
                return;
            }
            // 初始化执行链
            chain = new StandardFileChain(fileInfos, this);

            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            @SuppressWarnings("unchecked")
            List<FileItem> items = upload.parseRequest(request);
            // 先判断这次文件上传总体是否符合要求
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    allSize += item.getSize();
                    fileNum += 1;
                }
            }

            if (allSize > this.allFileSize) {
                status = UpConstants.ALL_SIZE_MAX_ERROR;
                return;
            } else if (fileNum > this.maxFileNum) {
                status = UpConstants.LIMIT_UPLOADNUM_ERROR;
                return;
            }
            // 处理表单
            Iterator<FileItem> iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = iter.next();
                if (item.isFormField()) {
                    processFormField(item);
                } else if (!item.isFormField()) {
                    fileItems.add(item);
                }
            }
            // 捕捉异常
        } catch (FileUploadException e) {
            status = UpConstants.UPLOAD_IO_ERROR;
            logger.error("文件上传错误！", e );
        } catch (IOException e) {
            status = UpConstants.UPLOAD_IO_ERROR;
            logger.error("文件IO错误！", e );
        } catch (Exception e) {
            status = UpConstants.UPLOAD_ERROR;
            logger.error("文件上传发生错误", e);
        }
    }

    public List<FileInfo> upload() {
        // 如果没有设置，就设置标准的Controller
        if (controller == null) {
            controller = new StandardController();
        }
        if (fileItems != null && fileItems.size() > 0) {
            uploadFile(fileItems, fileInfos);
        }
        try {
            if (chain instanceof FileChain) {
                chain.doProcess();
            }
        } catch (Exception e) {
            logger.error("文件处理发生错误", e);
            status = UpConstants.FILE_PROCESSE_ERROR;
        }
        // 处理执行链
        return fileInfos;
    }

    // 验证文件是否符合上传规范
    protected String validate(FileItem fileItem) {
        // 验证文件大小
        if (fileItem.getSize() <= 0 || fileItem.getName() == null || fileItem.getName().equals("")) {
            // 空文件
            return UpConstants.FILE_EMPTY_ERROR;
        }
        // 文件单个超过大小
        if (fileItem.getSize() > this.singleFileSize) {
            return UpConstants.SINGLE_FILE_SIZE_MAX_ERROR;
        }
        // 总文件大小超出
        if (logger.isDebugEnabled()) {
            logger.debug(fileItem.getName() + " =======> " + fileItem.getContentType());
        }
        // contentType 不接受
        if (!filter.isAccept(fileItem.getName(), fileItem.getContentType())) {
            return UpConstants.FILE_DISALLOWD_ERROR;
        }
        return UpConstants.SUCCESS;
    }

    /**
     * 交给子类去实现
     */
    protected abstract void uploadFile(List<FileItem> Items, List<FileInfo> fileInfos);

    private void processFormField(FileItem item) throws IOException {
        if (item.isFormField()) {
            String name = item.getFieldName();
            String value = new String(item.getString().getBytes("ISO-8859-1"), UpConstants.CHARSET_UTF);
            params.put(name, value);
        }
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void addFileProcessor(FileProcessor processor) {
        chain.addProcessor(processor);
    }

    public String getParams(String key) {
        return params.get(key);
    }

    public void setParams(String key, String value) {
        request.setAttribute(key, value);
    }

    public void setSingleFileSize(long singleFileSize) {
        this.singleFileSize = singleFileSize;
    }

    public void setAllFileSize(long allFileSize) {
        this.allFileSize = allFileSize;
    }

    public void setMaxFileNum(int maxFileNum) {
        this.maxFileNum = maxFileNum;
    }


    public String getStatus() {
        return status;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public StandardFileChain getChain() {
        return chain;
    }

    public void setChain(StandardFileChain chain) {
        this.chain = chain;
    }


    public void setStatus(String status) {
        this.status = status;
    }

    public Controller getController() {
        return controller;
    }

    public List<FileInfo> getFileInfos() {
        return fileInfos;
    }

    public void setFilter(ContentTypeFilter filter) {
        this.filter = filter;
    }

}
