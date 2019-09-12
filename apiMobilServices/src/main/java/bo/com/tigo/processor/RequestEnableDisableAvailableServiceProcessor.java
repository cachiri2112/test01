package bo.com.tigo.processor;

import java.util.HashMap;
import java.util.Map;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.PropertyInject;

import bo.com.tigo.ws.EnableDisableAvailableService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.parsers.DocumentBuilderFactory;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.io.ByteArrayInputStream;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



public class RequestEnableDisableAvailableServiceProcessor implements Processor {
    static Logger LOG = LoggerFactory.getLogger(RequestEnableDisableAvailableServiceProcessor.class);
    @PropertyInject(value = "info.url")
	private String infourl;
	@Override
    public void process(Exchange exchange) throws Exception {

		EnableDisableAvailableService request1 = exchange.getIn().getBody(EnableDisableAvailableService.class);
        Map<String,String> Parameters = new HashMap<>();
           String transactionID = (request1.getRequestHeader().getTransactionID()).toString();
            String correlationID = (request1.getRequestHeader().getCorrelationID()).toString();
            String country = (request1.getRequestHeader().getCountry()).toString();

            String msisdn = (request1.getRequestBody().getMsisdn()).toString();
            String consumerID = (request1.getRequestBody().getConsumer()).toString();
            String serviceCode = (request1.getRequestBody().getServiceCode()).toString();
            String enable =  (request1.getRequestBody().getOperation()).toString();
            String parameterName = "";
            String parameterValue = "";

        try {
            OkHttpClient client = new OkHttpClient();

           
            
            if (country == null || country.isEmpty() 
                                || msisdn == null 
                                || msisdn.isEmpty() 
                                || serviceCode == null 
                                || serviceCode.isEmpty() 
                                || enable == null 
                                || enable.isEmpty() 
                                || !country.equals("BOL")) {
                                    
                Parameters.put("transactionID", transactionID);
                Parameters.put("status", "ERROR");
                Parameters.put("consumerID", consumerID);
                Parameters.put("msisdn", msisdn);
                Parameters.put("country", country);
                Parameters.put("code", "200");
            } else {
                if(enable.equals("ENABLE")){enable="1";}
                else if(enable.equals("DISABLE")){enable="0";}

                MediaType mediaType = MediaType.parse("text/xml");
                
                RequestBody body = RequestBody.create(mediaType, "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.enabledisablebccsservice.lothar.com.py/\" xmlns:v3=\"http://xmlns.tigo.com/RequestHeader/V3\" xmlns:sch=\"http://xmlns.tigo.com/TigoApi/EnableDisableBccsService/V1/schema\" xmlns:v2=\"http://xmlns.tigo.com/ParameterType/V2\">\r\n  <soapenv:Header/>\r\n   <soapenv:Body>\r\n  <ws:enableDisableBccsService>\r\n   <v3:RequestHeader>\r\n  <v3:GeneralConsumerInformation>\r\n <v3:consumerID>"+consumerID+"</v3:consumerID>\r\n    <v3:transactionID>"+transactionID+"</v3:transactionID>\r\n  <v3:country>"+country+"</v3:country>\r\n  <v3:correlationID>"+correlationID+"</v3:correlationID>\r\n  </v3:GeneralConsumerInformation>\r\n    </v3:RequestHeader>\r\n <sch:requestBody>\r\n   <sch:msisdn>"+msisdn+"</sch:msisdn>\r\n <sch:serviceCode>"+serviceCode+"</sch:serviceCode>\r\n  <sch:enable>"+enable+"</sch:enable>\r\n <sch:additionalParameters>\r\n  <v2:ParameterType>\r\n  <v2:parameterName>?</v2:parameterName>\r\n  <v2:parameterValue>?</v2:parameterValue>\r\n    </v2:ParameterType>\r\n </sch:additionalParameters>\r\n </sch:requestBody>\r\n  </ws:enableDisableBccsService>\r\n  </soapenv:Body>\r\n </soapenv:Envelope>");

                Request request = new Request.Builder()
                        .url(infourl+"=")
                        .post(body)
                        .addHeader("Cache-Control", "no-cache")
                        .build();
                Response response = client.newCall(request).execute();
                String bodyResponse = response.body().string();

                Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                        .parse(new ByteArrayInputStream(bodyResponse.getBytes("UTF-8")));
                
                
                document.getDocumentElement().normalize();

                NodeList responseHeader = document.getElementsByTagName("ns4:ResponseHeader");
                Node nodeRH = responseHeader.item(0);
                Element elementRH = (Element) nodeRH;
                
                NodeList responseBody = document.getElementsByTagName("ns2:responseBody");
                Node nodeRB = responseBody.item(0);
                Element elementRB = (Element) nodeRB;
            
                Parameters.put("transactionID", transactionID);
                Parameters.put("status", elementRH.getElementsByTagName("ns4:status").item(0).getTextContent());
                Parameters.put("consumerID", consumerID);
                Parameters.put("msisdn", msisdn);
                Parameters.put("country", country);
                Parameters.put("code", elementRH.getElementsByTagName("ns4:code").item(0).getTextContent());
                Parameters.put("description", elementRH.getElementsByTagName("ns4:description").item(0).getTextContent());
            }

        } catch (Exception ex) {
            String tempEX = ex.toString();
                Parameters.put("transactionID", transactionID);
                Parameters.put("status", "ERROR");
                Parameters.put("consumerID", consumerID);
                Parameters.put("msisdn", msisdn);
                Parameters.put("country", country);
                Parameters.put("code", "200");
                 Parameters.put("description", tempEX);

        }
        exchange.getOut().setBody(Parameters);
    }
}
