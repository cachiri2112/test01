package bo.com.tigo.processor;

import java.util.HashMap;
import java.util.Map;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import bo.com.tigo.ws.RequestBody;
import bo.com.tigo.ws.GetAvailableService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestGetAvailableServiceProcessor implements Processor {
    static Logger LOG = LoggerFactory.getLogger(RequestGetAvailableServiceProcessor.class);

	@Override
    public void process(Exchange exchange) throws Exception {
		GetAvailableService request = exchange.getIn().getBody(GetAvailableService.class);
        Map<String,String> sqlParameters = new HashMap<>();
        String linea = (request.getRequestBody().getMsisdn()).toString();
        String country = (request.getRequestHeader().getCountry()).toString();
        String consumer = (request.getRequestBody().getConsumer()).toString();
        String transactionID = (request.getRequestHeader().getTransactionID()).toString();
        if ( (linea == null) || (linea.isEmpty()) || (country == null) || (country.isEmpty()) || !country.equals("BOL")) {
            sqlParameters.put("linea", "");
        } else {
            sqlParameters.put("linea", linea);
        }
        sqlParameters.put("correlationID", (request.getRequestHeader().getCorrelationID()).toString());
        exchange.setProperty("sqlParameters", sqlParameters);
        exchange.getOut().setBody(sqlParameters);
        exchange.getOut().setHeader("consumer", consumer);
        exchange.getOut().setHeader("transactionID", transactionID);
        exchange.getOut().setHeader("msisdn", linea);
    }
}
