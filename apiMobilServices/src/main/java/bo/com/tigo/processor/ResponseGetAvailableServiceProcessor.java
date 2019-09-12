package bo.com.tigo.processor;

import java.util.Map;
import java.util.List;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import bo.com.tigo.ws.ResponseGetAvailableService;
import bo.com.tigo.ws.ResponseHeader;
import bo.com.tigo.ws.ResponseBody;
import bo.com.tigo.ws.ServiceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ResponseGetAvailableServiceProcessor implements Processor {
	static Logger LOG = LoggerFactory.getLogger(ResponseGetAvailableServiceProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		
		List dbResponse = exchange.getIn().getBody(List.class);
        String consumer = exchange.getIn().getHeader("consumer").toString();
        String transactionID = exchange.getIn().getHeader("transactionID").toString();
        String msisdn = exchange.getIn().getHeader("msisdn").toString();
		
		ResponseGetAvailableService response = new ResponseGetAvailableService();
		ResponseHeader header = new ResponseHeader();
		ResponseBody body = new ResponseBody();	
		Object tempHeader;
		Object tempData;
		

		if (dbResponse == null || dbResponse.isEmpty() || (dbResponse.size() == 0)) {
			header.setTransactionID("");
			header.setStatus("Error");
			header.setCode("200");
			header.setDescription("Campos obligatorios están en blanco o nulo");

			ServiceType serviceType = new ServiceType();
			serviceType.setServiceId("");
			serviceType.setServiceDescription("");
			serviceType.setServiceName("");
			serviceType.setServiceCurrency("");
			serviceType.setServiceCost("");
			serviceType.setServiceStatus("");

			
			body.setConsumer("");
			body.setMsisdn("");

			body.getServiceType().add(serviceType);
			response.setResponseBody(body);

		} else {
			//Map mapHeader = (Map) dbResponse.get(0);
			header.setTransactionID(transactionID.equals("?") ? "" : transactionID);
			header.setStatus("Ok");
			header.setCode("000");
			header.setDescription("Transacción Satisfactoria");

			body.setConsumer(consumer.equals("?") ? "" : consumer);
			body.setMsisdn(msisdn == null ? "" : msisdn);

			for (Object object : dbResponse) {
				if (object instanceof Map) {

					Map map = (Map) object;
					ServiceType serviceType = new ServiceType();			
					tempData = map.get("serviceid");
					serviceType.setServiceId(tempData == null ? "" : tempData.toString());
					tempData = map.get("servicedescription");
					serviceType.setServiceDescription(tempData == null ? "" : tempData.toString());
					tempData = map.get("name");
					serviceType.setServiceName(tempData == null ? "" : tempData.toString());
					tempData = map.get("currency");
					serviceType.setServiceCurrency(tempData == null ? "" : tempData.toString());
					tempData = map.get("servicecost");
					serviceType.setServiceCost(tempData == null ? "" : tempData.toString());
					tempData = map.get("status");
					serviceType.setServiceStatus(tempData == null ? "" : tempData.toString());
					
					body.getServiceType().add(serviceType);
				}
			}
			response.setResponseBody(body);
		}
		response.setResponseHeader(header);
		exchange.getOut().setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        exchange.getOut().setHeader("Pragma", "no-cache");
		exchange.getOut().setBody(response);
    }
}
