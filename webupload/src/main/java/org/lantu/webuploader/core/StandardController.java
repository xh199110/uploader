package org.lantu.webuploader.core;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.lantu.webuploader.Controller;

public class StandardController implements Controller {

	private static String TOP_PATH = "upload";


	public String getFileDir(HttpServletRequest request, String orginalName) {
		File f = new File(request.getSession().getServletContext()
				.getRealPath(TOP_PATH + File.separator + FileUtils.formatYMD(new Date())) + File.separator);
		if (!f.exists()) {
			f.mkdirs();
		}
		return f.getAbsolutePath()+File.separator;
	}

	public String newName(String orginalName) {
		return Md5Utils.hash(orginalName.substring(0, orginalName.lastIndexOf("."))) + "."
				+ orginalName.substring(orginalName.lastIndexOf(".") + 1);
	}

    public String urlFix(HttpServletRequest request, File file) {
        String projectName=request.getContextPath().substring(1, request.getContextPath().length());
        return  file.getAbsolutePath().substring(file.getAbsolutePath().indexOf(projectName)+projectName.length());
    }
}
