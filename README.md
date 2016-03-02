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
				uploader.setController(new StandardController(){
				    @Override
				    public String getFileDir(HttpServletRequest request, String orginalName) {//设置文件存放路径
				        //比如:上传的文件现在要保存到c盘：c:/home/image
				       File  f=new File("c:/home/image");
				       if(!f.exists()){
				           f.mkdirs();
				       }
				       return f.getAbsolutePath()+File.separator;
				    }
				});
				uploader.init(req);
				//得到上传好的文件
				List<FileInfo> fileInfos=uploader.upload();
				String status=uploader.getStatus();
				if(status.equals(UpConstants.SUCCESS)){//如果全部上传成功 
					System.out.println(fileInfos.get(0).getUrl());//打印出第一个文件虚拟路径
				}else{//上传错误，至少一个文件上传错误
					resp.getWriter().write(status);
					resp.getWriter().write("\r\n");
					//输出每个文件的状态
					for(FileInfo f:fileInfos){
                        resp.getWriter().write(f.getStatus()+"\r\n");
                        if(f.getStatus().equals(UpConstants.SUCCESS)){//如果这个文件上传成功
                            System.out.println(f.getUrl());//打印出虚拟路径
                        };
                    }
					
                    resp.getWriter().write("success"+":"+fileInfos.size());
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
