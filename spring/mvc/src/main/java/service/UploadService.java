package service;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import annotation.Autowired;
import annotation.Component;
import annotation.RequestMapping;
@Component(name = "uploadService")
public class UploadService {
	private String realPath="D:\\workspace\\spring\\mvc\\src\\main\\resources\\";
	    /*传统表单上传*/
	    public String Upload(HttpServletRequest request)  {
	        System.out.println("uploadFiles1.....");
	        //获取部署目录,tomcat部署
	       // String realPath = request.getSession().getServletContext().getRealPath("/uploads/");
	      //  System.out.println(realPath);
	        File file = new File(realPath);
	        if (file.exists()==false){//判断文件目录是否存在
	            file.mkdirs();//创建上传目录
	        }
	 
	        //解析request对象，获取上传文件项
	        FileItemFactory diskFileItemFactory = new DiskFileItemFactory();
	        ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
	        List<FileItem> fileItems=null;
	        String filename=null;
			try {
				fileItems = servletFileUpload.parseRequest(request);
			 //获取到上传的文件项
	        for (FileItem fileItem : fileItems) {
	            //判断是否为上传的文件项
	            if (fileItem.isFormField()){
	                //表单项
	            }else {
	            	filename=getFilename(fileItem);//获取上传文件的名字
	            	
	                String uuid = UUID.randomUUID().toString().replace("-", "");//uuid生成随机字符
	                filename=uuid +"_" +filename;
	                //上传文件
	                fileItem.write(new File(realPath,filename));
	                //删除内存缓存文件  <10kb
	                fileItem.delete();
	            }
	 
	 
	        }
	        	return filename+"上传成功";
			}
	        catch (Exception  e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return filename+"上传失败";
			}
	    }

		private String getFilename(FileItem fileItem) {
			int p=fileItem.getName().lastIndexOf("\\");
			String s="";
			if(p!=-1) {
				s=fileItem.getName().substring(p+1);
			}else {
				s=fileItem.getName();
			}
			return s;
		}
}
