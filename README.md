# uploader
a framework of upload.
一个文件上框架
使用方法为：
public class UploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				Uploader uploader=UploaderFactory.getMutiUploader();
				uploader.init(req);
				//得到上传好的文件
				List<FileInfo> fileInfos=uploader.upload();
				
				String status=uploader.getStatus();
				if(status.equals(UpConstants.SUCCESS)){
					System.out.println(fileInfos.get(0).getUrl());
					resp.getWriter().write("success"+":"+fileInfos.size());
				}else{
					System.out.println(status);
					resp.getWriter().write(status);
					Throwable t=uploader.getThrowable();
					if(t!=null)
						t.printStackTrace();
				}
				
	}
}

处理器接口：
FileProcessor 处理器接口可以对文件上传应用服务器作后续处理，采用职责设计模式

DBProcessor.java

public class DBProcessor implements FileProcessor {

	public void process(FileChain chain,Uploader uploader, List<FileInfo> fileInfos) throws Exception {
		System.out.println("db processor start");
		System.out.println(fileInfos.get(0).getOrginlName());
		System.out.println(uploader.getParams("fileName"));
		chain.doProcess();
		System.out.println("db processor end");
	}

}


DBProcessor2.java

public class DBProcessor2 implements FileProcessor {

	public void process(FileChain chain,Uploader uploader, List<FileInfo> fileInfos) throws Exception {
		System.out.println("db processor start");
		System.out.println(fileInfos.get(0).getOrginlName());
		chain.doProcess();
		System.out.println("db processor end");
	}

}
