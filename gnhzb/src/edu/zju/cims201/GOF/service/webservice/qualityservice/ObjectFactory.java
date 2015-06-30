
package edu.zju.cims201.GOF.service.webservice.qualityservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the test package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ReceiveAQS10A1Arg1_QNAME = new QName("", "arg1");
    private final static QName _ReceiveAQS10A1Arg0_QNAME = new QName("", "arg0");
    private final static QName _ConnectResponseReturn_QNAME = new QName("", "return");
    private final static QName _ConnectResponse_QNAME = new QName("http://service.application.javaplus.org/", "connectResponse");
    private final static QName _Connect_QNAME = new QName("http://service.application.javaplus.org/", "connect");
    private final static QName _Receive_QNAME = new QName("http://service.application.javaplus.org/", "receive");
    private final static QName _HasAccountResponse_QNAME = new QName("http://service.application.javaplus.org/", "hasAccountResponse");
    private final static QName _ReceiveAQS10Troublereg_QNAME = new QName("http://service.application.javaplus.org/", "receiveAQS10Troublereg");
    private final static QName _ReceiveAQS10A1Response_QNAME = new QName("http://service.application.javaplus.org/", "receiveAQS10A1Response");
    private final static QName _ProcessEPBTroubleregTMPResponse_QNAME = new QName("http://service.application.javaplus.org/", "processEPBTroubleregTMPResponse");
    private final static QName _ProcessEPBTroubleregTMP_QNAME = new QName("http://service.application.javaplus.org/", "processEPBTroubleregTMP");
    private final static QName _ReceiveResponse_QNAME = new QName("http://service.application.javaplus.org/", "receiveResponse");
    private final static QName _ProcessEPBA1TMPResponse_QNAME = new QName("http://service.application.javaplus.org/", "processEPBA1TMPResponse");
    private final static QName _ReceiveAQS10TroubleregResponse_QNAME = new QName("http://service.application.javaplus.org/", "receiveAQS10TroubleregResponse");
    private final static QName _ReceiveAQS10A1_QNAME = new QName("http://service.application.javaplus.org/", "receiveAQS10A1");
    private final static QName _HasAccount_QNAME = new QName("http://service.application.javaplus.org/", "hasAccount");
    private final static QName _GetAllA1_QNAME = new QName("http://service.application.javaplus.org/", "getAllA1");
    private final static QName _ProcessEPBA1TMP_QNAME = new QName("http://service.application.javaplus.org/", "processEPBA1TMP");
    private final static QName _Exception_QNAME = new QName("http://service.application.javaplus.org/", "Exception");
    private final static QName _GetAllA1Response_QNAME = new QName("http://service.application.javaplus.org/", "getAllA1Response");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: test
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link HasAccount }
     * 
     */
    public HasAccount createHasAccount() {
        return new HasAccount();
    }

    /**
     * Create an instance of {@link GetAllA1Response }
     * 
     */
    public GetAllA1Response createGetAllA1Response() {
        return new GetAllA1Response();
    }

    /**
     * Create an instance of {@link ProcessEPBA1TMP }
     * 
     */
    public ProcessEPBA1TMP createProcessEPBA1TMP() {
        return new ProcessEPBA1TMP();
    }

    /**
     * Create an instance of {@link ProcessEPBTroubleregTMP }
     * 
     */
    public ProcessEPBTroubleregTMP createProcessEPBTroubleregTMP() {
        return new ProcessEPBTroubleregTMP();
    }

    /**
     * Create an instance of {@link Receive }
     * 
     */
    public Receive createReceive() {
        return new Receive();
    }

    /**
     * Create an instance of {@link ReceiveResponse }
     * 
     */
    public ReceiveResponse createReceiveResponse() {
        return new ReceiveResponse();
    }

    /**
     * Create an instance of {@link ReceiveAQS10TroubleregResponse }
     * 
     */
    public ReceiveAQS10TroubleregResponse createReceiveAQS10TroubleregResponse() {
        return new ReceiveAQS10TroubleregResponse();
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link ProcessEPBA1TMPResponse }
     * 
     */
    public ProcessEPBA1TMPResponse createProcessEPBA1TMPResponse() {
        return new ProcessEPBA1TMPResponse();
    }

    /**
     * Create an instance of {@link ProcessEPBTroubleregTMPResponse }
     * 
     */
    public ProcessEPBTroubleregTMPResponse createProcessEPBTroubleregTMPResponse() {
        return new ProcessEPBTroubleregTMPResponse();
    }

    /**
     * Create an instance of {@link ReceiveAQS10A1 }
     * 
     */
    public ReceiveAQS10A1 createReceiveAQS10A1() {
        return new ReceiveAQS10A1();
    }

    /**
     * Create an instance of {@link HasAccountResponse }
     * 
     */
    public HasAccountResponse createHasAccountResponse() {
        return new HasAccountResponse();
    }

    /**
     * Create an instance of {@link ReceiveAQS10Troublereg }
     * 
     */
    public ReceiveAQS10Troublereg createReceiveAQS10Troublereg() {
        return new ReceiveAQS10Troublereg();
    }

    /**
     * Create an instance of {@link ReceiveAQS10A1Response }
     * 
     */
    public ReceiveAQS10A1Response createReceiveAQS10A1Response() {
        return new ReceiveAQS10A1Response();
    }

    /**
     * Create an instance of {@link ConnectResponse }
     * 
     */
    public ConnectResponse createConnectResponse() {
        return new ConnectResponse();
    }

    /**
     * Create an instance of {@link Connect }
     * 
     */
    public Connect createConnect() {
        return new Connect();
    }

    /**
     * Create an instance of {@link GetAllA1 }
     * 
     */
    public GetAllA1 createGetAllA1() {
        return new GetAllA1();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "arg1", scope = ReceiveAQS10A1 .class)
    public JAXBElement<byte[]> createReceiveAQS10A1Arg1(byte[] value) {
        return new JAXBElement<byte[]>(_ReceiveAQS10A1Arg1_QNAME, byte[].class, ReceiveAQS10A1 .class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "arg0", scope = ReceiveAQS10A1 .class)
    public JAXBElement<byte[]> createReceiveAQS10A1Arg0(byte[] value) {
        return new JAXBElement<byte[]>(_ReceiveAQS10A1Arg0_QNAME, byte[].class, ReceiveAQS10A1 .class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "arg1", scope = ProcessEPBA1TMP.class)
    public JAXBElement<byte[]> createProcessEPBA1TMPArg1(byte[] value) {
        return new JAXBElement<byte[]>(_ReceiveAQS10A1Arg1_QNAME, byte[].class, ProcessEPBA1TMP.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "arg0", scope = ProcessEPBA1TMP.class)
    public JAXBElement<byte[]> createProcessEPBA1TMPArg0(byte[] value) {
        return new JAXBElement<byte[]>(_ReceiveAQS10A1Arg0_QNAME, byte[].class, ProcessEPBA1TMP.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "arg1", scope = ProcessEPBTroubleregTMP.class)
    public JAXBElement<byte[]> createProcessEPBTroubleregTMPArg1(byte[] value) {
        return new JAXBElement<byte[]>(_ReceiveAQS10A1Arg1_QNAME, byte[].class, ProcessEPBTroubleregTMP.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "arg0", scope = ProcessEPBTroubleregTMP.class)
    public JAXBElement<byte[]> createProcessEPBTroubleregTMPArg0(byte[] value) {
        return new JAXBElement<byte[]>(_ReceiveAQS10A1Arg0_QNAME, byte[].class, ProcessEPBTroubleregTMP.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "arg1", scope = ReceiveAQS10Troublereg.class)
    public JAXBElement<byte[]> createReceiveAQS10TroubleregArg1(byte[] value) {
        return new JAXBElement<byte[]>(_ReceiveAQS10A1Arg1_QNAME, byte[].class, ReceiveAQS10Troublereg.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "arg0", scope = ReceiveAQS10Troublereg.class)
    public JAXBElement<byte[]> createReceiveAQS10TroubleregArg0(byte[] value) {
        return new JAXBElement<byte[]>(_ReceiveAQS10A1Arg0_QNAME, byte[].class, ReceiveAQS10Troublereg.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "return", scope = ConnectResponse.class)
    public JAXBElement<byte[]> createConnectResponseReturn(byte[] value) {
        return new JAXBElement<byte[]>(_ConnectResponseReturn_QNAME, byte[].class, ConnectResponse.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "arg0", scope = Receive.class)
    public JAXBElement<byte[]> createReceiveArg0(byte[] value) {
        return new JAXBElement<byte[]>(_ReceiveAQS10A1Arg0_QNAME, byte[].class, Receive.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "return", scope = ReceiveResponse.class)
    public JAXBElement<byte[]> createReceiveResponseReturn(byte[] value) {
        return new JAXBElement<byte[]>(_ConnectResponseReturn_QNAME, byte[].class, ReceiveResponse.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "arg0", scope = Connect.class)
    public JAXBElement<byte[]> createConnectArg0(byte[] value) {
        return new JAXBElement<byte[]>(_ReceiveAQS10A1Arg0_QNAME, byte[].class, Connect.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConnectResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.application.javaplus.org/", name = "connectResponse")
    public JAXBElement<ConnectResponse> createConnectResponse(ConnectResponse value) {
        return new JAXBElement<ConnectResponse>(_ConnectResponse_QNAME, ConnectResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Connect }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.application.javaplus.org/", name = "connect")
    public JAXBElement<Connect> createConnect(Connect value) {
        return new JAXBElement<Connect>(_Connect_QNAME, Connect.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Receive }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.application.javaplus.org/", name = "receive")
    public JAXBElement<Receive> createReceive(Receive value) {
        return new JAXBElement<Receive>(_Receive_QNAME, Receive.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HasAccountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.application.javaplus.org/", name = "hasAccountResponse")
    public JAXBElement<HasAccountResponse> createHasAccountResponse(HasAccountResponse value) {
        return new JAXBElement<HasAccountResponse>(_HasAccountResponse_QNAME, HasAccountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReceiveAQS10Troublereg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.application.javaplus.org/", name = "receiveAQS10Troublereg")
    public JAXBElement<ReceiveAQS10Troublereg> createReceiveAQS10Troublereg(ReceiveAQS10Troublereg value) {
        return new JAXBElement<ReceiveAQS10Troublereg>(_ReceiveAQS10Troublereg_QNAME, ReceiveAQS10Troublereg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReceiveAQS10A1Response }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.application.javaplus.org/", name = "receiveAQS10A1Response")
    public JAXBElement<ReceiveAQS10A1Response> createReceiveAQS10A1Response(ReceiveAQS10A1Response value) {
        return new JAXBElement<ReceiveAQS10A1Response>(_ReceiveAQS10A1Response_QNAME, ReceiveAQS10A1Response.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProcessEPBTroubleregTMPResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.application.javaplus.org/", name = "processEPBTroubleregTMPResponse")
    public JAXBElement<ProcessEPBTroubleregTMPResponse> createProcessEPBTroubleregTMPResponse(ProcessEPBTroubleregTMPResponse value) {
        return new JAXBElement<ProcessEPBTroubleregTMPResponse>(_ProcessEPBTroubleregTMPResponse_QNAME, ProcessEPBTroubleregTMPResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProcessEPBTroubleregTMP }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.application.javaplus.org/", name = "processEPBTroubleregTMP")
    public JAXBElement<ProcessEPBTroubleregTMP> createProcessEPBTroubleregTMP(ProcessEPBTroubleregTMP value) {
        return new JAXBElement<ProcessEPBTroubleregTMP>(_ProcessEPBTroubleregTMP_QNAME, ProcessEPBTroubleregTMP.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReceiveResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.application.javaplus.org/", name = "receiveResponse")
    public JAXBElement<ReceiveResponse> createReceiveResponse(ReceiveResponse value) {
        return new JAXBElement<ReceiveResponse>(_ReceiveResponse_QNAME, ReceiveResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProcessEPBA1TMPResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.application.javaplus.org/", name = "processEPBA1TMPResponse")
    public JAXBElement<ProcessEPBA1TMPResponse> createProcessEPBA1TMPResponse(ProcessEPBA1TMPResponse value) {
        return new JAXBElement<ProcessEPBA1TMPResponse>(_ProcessEPBA1TMPResponse_QNAME, ProcessEPBA1TMPResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReceiveAQS10TroubleregResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.application.javaplus.org/", name = "receiveAQS10TroubleregResponse")
    public JAXBElement<ReceiveAQS10TroubleregResponse> createReceiveAQS10TroubleregResponse(ReceiveAQS10TroubleregResponse value) {
        return new JAXBElement<ReceiveAQS10TroubleregResponse>(_ReceiveAQS10TroubleregResponse_QNAME, ReceiveAQS10TroubleregResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReceiveAQS10A1 }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.application.javaplus.org/", name = "receiveAQS10A1")
    public JAXBElement<ReceiveAQS10A1> createReceiveAQS10A1(ReceiveAQS10A1 value) {
        return new JAXBElement<ReceiveAQS10A1>(_ReceiveAQS10A1_QNAME, ReceiveAQS10A1 .class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HasAccount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.application.javaplus.org/", name = "hasAccount")
    public JAXBElement<HasAccount> createHasAccount(HasAccount value) {
        return new JAXBElement<HasAccount>(_HasAccount_QNAME, HasAccount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllA1 }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.application.javaplus.org/", name = "getAllA1")
    public JAXBElement<GetAllA1> createGetAllA1(GetAllA1 value) {
        return new JAXBElement<GetAllA1>(_GetAllA1_QNAME, GetAllA1 .class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProcessEPBA1TMP }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.application.javaplus.org/", name = "processEPBA1TMP")
    public JAXBElement<ProcessEPBA1TMP> createProcessEPBA1TMP(ProcessEPBA1TMP value) {
        return new JAXBElement<ProcessEPBA1TMP>(_ProcessEPBA1TMP_QNAME, ProcessEPBA1TMP.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.application.javaplus.org/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllA1Response }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.application.javaplus.org/", name = "getAllA1Response")
    public JAXBElement<GetAllA1Response> createGetAllA1Response(GetAllA1Response value) {
        return new JAXBElement<GetAllA1Response>(_GetAllA1Response_QNAME, GetAllA1Response.class, null, value);
    }

}
