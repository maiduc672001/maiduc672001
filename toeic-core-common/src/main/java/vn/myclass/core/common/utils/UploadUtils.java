package vn.myclass.core.common.utils;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import vn.myclass.core.common.constant.CoreConstant;

import javax.servlet.*;
import javax.servlet.descriptor.JspConfigDescriptor;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class UploadUtils {
    private final int maxMemorySize = 1024 * 1024 * 3;//3MB
    private final int maxRequestSize = 1024 * 1024 * 50;//50MB
    private final Logger log = Logger.getLogger(this.getClass());

    public Object[] writeOrUpdateFile(HttpServletRequest request, Set<String> titleValue, String path) {
        String adress ="/"+ CoreConstant.FOLDER_UPLOAD;
        checkAnhCreateFolder(adress,path);
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        boolean check = true;
        String fileLocation = null;
        String name = null;
        Map<String, String> mapReturnValue = new HashMap<String, String>();
        if (!isMultipart) {
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
        try {
            List<FileItem> items = upload.parseRequest(request);
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    name = item.getName();
                    if(StringUtils.isNotBlank(name)){
                        File uploadFile = new File(adress + File.separator + path + File.separator + name);
                        fileLocation = adress + File.separator + path + File.separator + name;
                        boolean isExit = uploadFile.exists();
                        try {
                            if(isExit){
                                uploadFile.delete();
                                item.write(uploadFile);
                            }else {
                                item.write(uploadFile);
                            }
                        }catch (Exception e){
                            check=false;
                        }
                    }

                } else {
                    if (titleValue != null) {
                        String nameField = item.getFieldName();
                        String valueField = item.getString("UTF-8");
                        if (titleValue.contains(nameField)) {
                            mapReturnValue.put(nameField, valueField);
                        }
                    }
                }
            }
        } catch (FileUploadException e) {
            check = false;
            log.error(e.getMessage(), e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new Object[]{check, fileLocation, path+File.separator+name, mapReturnValue};
    }

    private void checkAnhCreateFolder(String adress, String path) {
        File folderRoot=new File(adress);
        if(!folderRoot.exists()){
            folderRoot.mkdirs();
        }
        File folderChild=new File(adress+File.separator+path);
        if(!folderChild.exists()){
            folderChild.mkdirs();
        }
    }
}
