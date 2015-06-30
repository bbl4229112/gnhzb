package edu.zju.cims201.GOF.util;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;

//import org.apache.fop.apps.FopFactory;
//import org.apache.fop.apps.MimeConstants;
import org.apache.fop.apps.Driver;
import org.apache.fop.apps.FOPException;
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;
import org.xml.sax.InputSource;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;



/*
 *  Class that converts HTML to PDF using
 *  the DOM interfaces of JTidy, Xalan, and FOP.
 *
 *  @author N. Afshartous
 * 
 */
public class Html2Pdf {


    public  void  toPdf(InputStream input,String filename) {

	

	

    	String dir = Constants.dir;	
    Tidy tidy = new Tidy();
    
    tidy.setShowWarnings(false);
    
    
    
	Document xmlDoc = tidy.parseDOM(input, null);
	
	Document foDoc = xml2FO(xmlDoc, dir+"\\xhtml2fo.xsl");
	  System.out.println("----eee7");

//	String pdfFileName = "12345678"+ ".pdf";
//	try {
//	    OutputStream pdf = new FileOutputStream(new File(pdfFileName));
//	    pdf.write(fo2PDF(foDoc));
//	}
//	catch (java.io.FileNotFoundException e) {
//	    System.out.println("Error creating PDF: " + pdfFileName);
//	}
//	catch (java.io.IOException e) {
//	    System.out.println("Error writing PDF: " + pdfFileName);
//	}
	
	
//	 SaveFileDialog filedialog_save= new FileDialog(null);
//	 File file = new File(filedialog_save.getDirectory(),filedialog_save.getFile());
	
	FileOutputStream outfile = null;
	ByteArrayOutputStream out = null;
	
try {
	 
//	 filename = new String(filename.getBytes("iso8859-1"), "gb2312");
//	 
//	 response.setContentType("application/x-msdownload");
//	 response.setHeader("Content-Disposition", "attachment; filename="+ URLEncoder.encode(filename, "UTF-8") + "");
	 outfile =new FileOutputStream(filename);
	  ByteArrayOutputStream baout = new ByteArrayOutputStream(16384);
		

	  System.out.println("----eee8");

		fo2PDF(foDoc,baout);
	      final byte[] content = baout.toByteArray();
      
	      outfile.write(content);
	      outfile.flush();
	      outfile.close();
	      
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (FOPException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	  System.out.println("----eee9");
    }
  
   
     
     
     
    /*
     *  Applies stylesheet to input.
     *
     *  @param xml  The xml input Document
     *  
     *  @param stylesheet Name of the stylesheet
     *
     *  @return Document  Result of the transform
     */
    private static Document xml2FO(Document xml, String styleSheet) {

	    DOMSource xmlDomSource = new DOMSource(xml);
      	DOMResult domResult = new DOMResult();

	Transformer transformer = getTransformer(styleSheet);
	
	if (transformer == null) {
	    System.out.println("Error creating transformer for " + styleSheet);
	    System.exit(1);
	}
	else{  System.out.println(" transformer for " + styleSheet);}
	try {
		System.out.println("before");
		
	    transformer.transform(xmlDomSource, domResult);
		System.out.println("after");
	}
	catch (Exception e) {
	System.out.println("????");
	    return null;
	}
	return (Document) domResult.getNode();

    }

    public void fo2PDF(Document doc, ByteArrayOutputStream baout) throws IOException, FOPException {

        System.out.println("Construct driver");
        Driver driver = new Driver();

        System.out.println("Setup Renderer (output format)");
        driver.setRenderer(Driver.RENDER_PDF);

        try {
          driver.setOutputStream(baout);
          System.out.println("Setup input");
          try {
        	  ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); 
        	  Source xmlSource = new DOMSource(doc); 
        	  Result outputTarget = new StreamResult(outputStream); 
        	  try {
				TransformerFactory.newInstance().newTransformer().transform(xmlSource, outputTarget);
			} catch (TransformerConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerFactoryConfigurationError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
        	  InputStream is = new ByteArrayInputStream(outputStream.toByteArray()); 

        	  
        	//   DOMSource source = new DOMSource(doc);
        	//   InputStream input=new FileInputStream(doc);
            driver.setInputSource(new InputSource (is));

            System.out.println("Process FO");
            driver.run();
            System.out.println("PDF file generation completed");
          } finally {
          }
        } finally {
        }
      }
 	
    
    	
    	
//    private  void fo2PDF(Document foDocument,BufferedOutputStream out) {
//
//    	
//    //	FopFactory fopFactory = FopFactory.newInstance();
//    	
//		try {
//			
//			
//			  System.out.println("----eee8.1");		
//    		FopFactory fopFactory = FopFactory.newInstance();
//	
//			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);
//			
//
// 
//    		
//    		TransformerFactory factory = TransformerFactory.newInstance();
//    		Transformer transformer = null;
//		
//			transformer = factory.newTransformer();
//	   		Source src = new DOMSource(foDocument);
// 
//    		Result res = null;
//		
//			res = new SAXResult(fop.getDefaultHandler());
//		 
//			transformer.transform(src, res);
//	   		   		
//			out.close();
//						
//    		
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		
//
//		} catch (FOPException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//				} catch (TransformerConfigurationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		
//	    } catch (TransformerException e) {
//		// TODO Auto-generated catch block
//		   e.printStackTrace();
//	    } catch (IOException e) {
//		// TODO Auto-generated catch block
//	       e.printStackTrace();
//	    }
//        
//    }
    


    /*
     *  Create and return a Transformer for the specified stylesheet.
     *  
     *  Based on the DOM2DOM.java example in the Xalan distribution.
     */
    private static Transformer getTransformer(String styleSheet) {

	try {
         //System.out.println("----dd");
	    TransformerFactory tFactory = TransformerFactory.newInstance();
	    //System.out.println("----eee1");
	    DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
	    //System.out.println("----eee2");
	    dFactory.setNamespaceAware(true);
	    //System.out.println("----eee3");
	    DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
	   //System.out.println("----eee4");
	    Document xslDoc = dBuilder.parse(styleSheet);
	    //System.out.println("----eee5");
	    DOMSource xslDomSource = new DOMSource(xslDoc);
	   // System.out.println("----eee6.1");
	    Transformer temp=tFactory.newTransformer(xslDomSource);
	   // System.out.println("----eee7.1");
	    return temp;

	}
	catch (javax.xml.transform.TransformerException e) {
	    e.printStackTrace();
	    return null;
	}
	catch (java.io.IOException e) {
	    e.printStackTrace();
	    return null;
	}
	catch (javax.xml.parsers.ParserConfigurationException e) {
	    e.printStackTrace();
	    return null;
	}
	catch (org.xml.sax.SAXException e) {	
	    e.printStackTrace();
	    return null;
	}

    }
    
    
    /*
     * 合并PDF文件
     * 
     */
    
    public  void mergePdfFiles(String[] files, HttpServletResponse response,String filename)  
    {  
       
   	 response.setContentType("application/x-msdownload");
   	 response.setHeader("Content-Disposition", "attachment; filename="+ filename + "");
   	// File file = new File("e:\\patent\\"+filename);
   	 
    FileOutputStream outfile=null;
    
    String dir=Constants.dir;
	try {
		outfile = new FileOutputStream(dir+"\\"+filename);
	 } catch (FileNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	 }
	 //out = new BufferedOutputStream(outfile);
   	 
    	
    	try   
          {  
   		 com.lowagie.text.Document document = new com.lowagie.text.Document(new PdfReader(files[0]).getPageSize(1)); 
   		 
              
          PdfCopy copy = new PdfCopy((com.lowagie.text.Document) document, outfile);  
          //  PDFMaker maker = new PDFMaker();
           
      	
//  			   HeaderFooter footer = new HeaderFooter(new Phrase("page: "), false);
//  			   footer.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
//  			   footer.setAlignment(Element.ALIGN_CENTER);
//  			   document.setFooter(footer); 
            
  		  document.open();
              
            for(int i=0; i<files.length; i++)  
            {  
                PdfReader reader = new PdfReader(files[i]);  
                File filetmp= new File(files[i]);
                  
                int n = reader.getNumberOfPages();  

                for(int j=1; j<=n; j++)  
                {  
                    document.newPage();   
                    PdfImportedPage page = copy.getImportedPage(reader, j); 
                    copy.addPage(page);  
                   
                } 
                filetmp.delete();
                
           }  
           
            document.close();  
            
           FileInputStream  infile =null;
           infile = new FileInputStream(dir+"\\"+filename);
           BufferedInputStream br = new BufferedInputStream(infile);
           OutputStream out = response.getOutputStream();
            int len =0;
		    byte[] buf = new byte[1024];
   		    while ((len = br.read(buf)) > 0)
				out.write(buf, 0, len);
	 
			out.close();
			infile.close();  
            

        } catch (IOException e) {  
           e.printStackTrace();  
        } catch(DocumentException e) {  
            e.printStackTrace();  
 	        }
        
      }
    
    public  void mergePdfFilesLocal(String[] files, String savepath)  
    {  
       


   	 
    FileOutputStream outfile=null;
    
	try {
		outfile = new FileOutputStream(savepath);
	 } catch (FileNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	 }
	 //out = new BufferedOutputStream(outfile);
   	 
    	
    	try   
          {  
   		 com.lowagie.text.Document document = new com.lowagie.text.Document(new PdfReader(files[0]).getPageSize(1)); 
   		 
              
          PdfCopy copy = new PdfCopy((com.lowagie.text.Document) document, outfile);  
            
  		  document.open();
              
            for(int i=0; i<files.length; i++)  
            {  
                PdfReader reader = new PdfReader(files[i]);  
                File filetmp= new File(files[i]);
                  
                int n = reader.getNumberOfPages();  

                for(int j=1; j<=n; j++)  
                {  
                    document.newPage();   
                    PdfImportedPage page = copy.getImportedPage(reader, j); 
                    copy.addPage(page);  
                   
                } 
                filetmp.delete();
                
           }  
           
            document.close();  
       


        } catch (IOException e) {  
           e.printStackTrace();  
        } catch(DocumentException e) {  
            e.printStackTrace();  
 	        }
        
      }
    
    
    
    }

