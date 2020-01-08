package fr.eni_ecole.qcm.web.servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class Upload
 */
public class Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String repertoireStockage=null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Upload() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException {
    	// TODO Auto-generated method stub
    	super.init(config);
    	this.repertoireStockage=config.getInitParameter("repertoireStockage");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (FileUploadException e) {
			System.out.println(e.getMessage());
		}
	}

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, FileUploadException {
		PrintWriter out = response.getWriter();
		if(ServletFileUpload.isMultipartContent(request))
		{
			// Create a factory for disk-based file items
			DiskFileItemFactory factory = new DiskFileItemFactory();

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);

			// Parse the request
			List items = upload.parseRequest(request);
			
			// Process the uploaded items
			Iterator iter = items.iterator();
			while (iter.hasNext()) {
			    FileItem item = (FileItem) iter.next();

			    if (!item.isFormField()) 
			    {
			        String fichier = processUploadedFile(item);
			        out.write("{success:true,fichier:\""+fichier+"\"}");
			    }
			}
		}
		out.flush();
		out.close();
	}

	private String processUploadedFile(FileItem item) {
		String fileName = item.getName();
		int index=1;
		while(new File(this.repertoireStockage+fileName).exists())
		{
			fileName = "("+index+")"+item.getName();
			index+=1;
		}
		
	    File uploadedFile = new File(this.repertoireStockage + fileName);
	    try {
			item.write(uploadedFile);
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return fileName;
	}

}
