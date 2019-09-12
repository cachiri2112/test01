
package bo.com.tigo.ws;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the bo.com.tigo.ws package. 
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


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: bo.com.tigo.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ResponseGetAvailableService }
     * 
     */
    public ResponseGetAvailableService createResponseGetAvailableService() {
        return new ResponseGetAvailableService();
    }

    /**
     * Create an instance of {@link RequesBody }
     * 
     */
    public RequesBody createRequesBody() {
        return new RequesBody();
    }

    /**
     * Create an instance of {@link GetAvailableService }
     * 
     */
    public GetAvailableService createGetAvailableService() {
        return new GetAvailableService();
    }

    /**
     * Create an instance of {@link ResponseBody }
     * 
     */
    public ResponseBody createResponseBody() {
        return new ResponseBody();
    }

    /**
     * Create an instance of {@link EnableDisableAvailableService }
     * 
     */
    public EnableDisableAvailableService createEnableDisableAvailableService() {
        return new EnableDisableAvailableService();
    }

    /**
     * Create an instance of {@link ResponseHeader }
     * 
     */
    public ResponseHeader createResponseHeader() {
        return new ResponseHeader();
    }

    /**
     * Create an instance of {@link ResponseEnableDisableAvailableService }
     * 
     */
    public ResponseEnableDisableAvailableService createResponseEnableDisableAvailableService() {
        return new ResponseEnableDisableAvailableService();
    }

    /**
     * Create an instance of {@link RequestBody }
     * 
     */
    public RequestBody createRequestBody() {
        return new RequestBody();
    }

    /**
     * Create an instance of {@link ServiceType }
     * 
     */
    public ServiceType createServiceType() {
        return new ServiceType();
    }

    /**
     * Create an instance of {@link RequestHeader }
     * 
     */
    public RequestHeader createRequestHeader() {
        return new RequestHeader();
    }

}
