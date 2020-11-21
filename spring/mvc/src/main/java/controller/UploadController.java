package controller;

import java.util.List;
import java.io.File;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import annotation.Autowired;
import annotation.Component;
import annotation.Controller;
import annotation.RequestMapping;
import service.UploadService;
import servlet.ModelAndView;

@Controller(name = "uploadController")
//@Component(name = "uploadController")
public class UploadController {
@Autowired(name="uploadService")
private UploadService us=new UploadService();
@RequestMapping(value="/upload.do")
public ModelAndView Upload(HttpServletRequest request) throws Exception {
	ModelAndView mv=new ModelAndView();
	mv.addAttribute("message", us.Upload(request));
	mv.setView("/message.jsp");
	return mv;
}
}
