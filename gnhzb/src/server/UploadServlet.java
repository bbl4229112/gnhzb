package server ;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadServlet extends HttpServlet{



		/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public void doGet( HttpServletRequest req, HttpServletResponse res ){
		doPost(req, res);
	}

		public void doPost( HttpServletRequest req, HttpServletResponse res ){

			File disk = null;
			FileItem item = null;
			FileItemFactory factory = new DiskFileItemFactory();
			Iterator iter = null;
			List items = null;
			ServletFileUpload upload = new ServletFileUpload( factory );
			ServletOutputStream out = null;
			try{
				items = upload.parseRequest( req );
				iter = items.iterator();
				res.setContentType( " text/xml");
				out = res.getOutputStream();
				out.println( " <response> " );
				while( iter.hasNext() ){
					item = ( FileItem )iter.next();
					if( item.isFormField() ){
						out.println( "<field name=\"" + item.getFieldName() + "\"value=\"" + item.getString() + "\" />"  );
					}
					else{
						String wsdlLocationParam = req.getParameter("wsdlLocation");
						//System.out.println("$$$$$$$$$$$$$$$$$$$$$$___________req.getParameter(wsdlLocation) : "+wsdlLocationParam);
						wsdlLocationParam = wsdlLocationParam.replace('\\', File.separatorChar);
						//System.out.println("$$$$$$$$$$$$$$$$$___________req.getParameter(wsdlLocation) : "+wsdlLocationParam);
						String path = getServletContext().getRealPath("/")+wsdlLocationParam;
						path = path.replace('\\', File.separatorChar);
						//System.out.println("$$$$$$$$$$$$$$$$$path : "+path);
						disk = new File( path, item.getName() );
						item.write( disk );
						out.println( "<file name=\"" + item.getName() + "\" size=\""+ item.getSize()+" path=\"" + getInitParameter("uploadPath") + "\" />"  );

					}

				}

				out.println( " </response>" );

				out.close();

			} catch( FileUploadException fue ) {

				fue.printStackTrace();

			} catch( IOException ioe ) {

				ioe.printStackTrace();

			} catch( Exception e ) {

				e.printStackTrace();

			}

	}

		}