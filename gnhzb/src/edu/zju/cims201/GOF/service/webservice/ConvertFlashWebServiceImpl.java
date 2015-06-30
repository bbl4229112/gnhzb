package edu.zju.cims201.GOF.service.webservice;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import edu.zju.cims201.GOF.util.Constants;
@Component
public class ConvertFlashWebServiceImpl implements ConvertFlashWebService {
	
	
	private static final Log log = LogFactory.getLog(ConvertFlashWebServiceImpl.class);
    private static final String FLASH_SERVICE_ENDPOINT = Constants.FLASH_SERVICE_ENDPOINT;

	public String ConvertAndSaveFlash(Long documentID) {
		try {
            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new java.net.URL(
            		FLASH_SERVICE_ENDPOINT));
            call.setOperationName(new QName("http://ws.cims201.zju.edu",
                    "convertFile"));
            call.addParameter("id", org.apache.axis.Constants.XSD_LONG,
                    javax.xml.rpc.ParameterMode.IN);
            call.setReturnType(org.apache.axis.Constants.XSD_STRING);
            try {
                String ret = (String) call.invoke(new Object[] {documentID});
                return ret;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        log.error("call ConvertAndSaveFlash service error!");
        return null;
    }

}
