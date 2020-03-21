package vn.myclass.core.common.utils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.*;
import javax.servlet.descriptor.JspConfigDescriptor;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class UploadUtils {
    private final int maxMemorySize=1024*1024*3;//3MB
    private final int maxRequestSize=1024*1024*50;//50MB
    public Object[] writeOrUpdateFile(HttpServletRequest request,Set<String> titleValue,String path) throws FileUploadException,Exception{
        //Check that we have a file upload request
                ServletContext context=request.getServletContext();
                String adress=context.getRealPath("image");
        boolean isMultipart= ServletFileUpload.isMultipartContent(request);
        boolean check=true;
        String fileLocation=null;
        String name=null;
        Map<String,String> mapReturnValue=new HashMap<String, String>();
        if(!isMultipart){
            System.out.println("have not enctype multipart/form-data");
        }
        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();

// Set factory constraints
        factory.setSizeThreshold(maxMemorySize);
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

// Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

// Set overall request size constraint
        upload.setSizeMax(maxRequestSize);

// Parse the request
        List<FileItem> items = upload.parseRequest(request);
        for (FileItem item:items) {
            if(!item.isFormField()){
                String fileName=item.getName();
                File uploadFile=new File(adress+File.separator+path+File.separator+fileName);
                boolean isExit=uploadFile.exists();
                if(isExit){
                    if(uploadFile.delete()){
                        item.write(uploadFile);
                    }else {
                        check=false;
                    }
                }else {
                    item.write(uploadFile);
                }
            }else {
                if(titleValue!=null){
                    String nameField=item.getFieldName();
                    String valueField=item.getString();
                    if(titleValue.contains(nameField)){
                        mapReturnValue.put(nameField,valueField);
                    }
                }
            }
        }
return new Object[]{check,fileLocation,name,mapReturnValue};
    }
}
