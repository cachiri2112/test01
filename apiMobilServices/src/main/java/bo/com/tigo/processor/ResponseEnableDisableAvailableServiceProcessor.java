package bo.com.tigo.processor;

import java.util.Map;
import java.util.List;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import bo.com.tigo.ws.ResponseEnableDisableAvailableService;
import bo.com.tigo.ws.ResponseHeader;
import bo.com.tigo.ws.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ResponseEnableDisableAvailableServiceProcessor implements Processor {
	static Logger LOG = LoggerFactory.getLogger(ResponseEnableDisableAvailableServiceProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {
	
		Map map = exchange.getIn().getBody(Map.class);
		Object tempData;
		
		ResponseEnableDisableAvailableService response = new ResponseEnableDisableAvailableService();
		ResponseHeader header = new ResponseHeader();
		ResponseBody body = new ResponseBody();
	
		//tempData = map.get("transactionID");
		header.setTransactionID(map.get("transactionID") == null ? "" : map.get("transactionID").toString());
	//	tempData = map.get("status");
		header.setStatus( map.get("status") == null ? "" :  map.get("status").toString());
	//	tempData = map.get("code");
		header.setCode(map.get("code")== null ? "" : map.get("code").toString());
        tempData = map.get("code");
		switch(tempData.toString()){
			case "200": {
				header.setStatus("ERROR");
				header.setDescription("Campos obligatorios están en blanco o nulo");
			} break;
			case "900": {
				header.setStatus("ERROR");
				//tempData = map.get("description");
				header.setDescription(map.get("description")== null ? "" : map.get("description").toString());
			} break;
			case "2": {
				header.setCode("000");
				header.setStatus("OK");
				header.setDescription("Transaccion Satisfactoria");
			} break;
			case "601": {
				header.setCode("601");
				header.setStatus("ERROR");
				//tempData = map.get("description");
				header.setDescription(map.get("description") == null ? "" : map.get("description").toString());
			} break;
			case "0": {
				header.setCode("0");
				header.setStatus("OK");
				//tempData = map.get("description");
				header.setDescription(map.get("description") == null ? "" : map.get("description").toString());
			} break;
			default: {
				header.setStatus("KNOW");
				header.setDescription("Status Desconocido: Code" + tempData.toString());
			}
		}

	//	tempData = map.get("consumerID");
		body.setConsumer(map.get("consumerID") == null ? "" : map.get("consumerID").toString());
	//	tempData = map.get("msisdn");
		body.setMsisdn( map.get("msisdn") == null ? "" :  map.get("msisdn").toString());

		response.setResponseBody(body);
		response.setResponseHeader(header);
	    exchange.getOut().setHeader("Cache-Control","no-store" );
		exchange.getOut().setBody(response);
    }
}
