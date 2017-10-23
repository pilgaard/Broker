/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.xmlbank;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;


/**
 *
 * @author emil
 */
@WebService(serviceName = "BankWS")
@Stateless()
public class BankWS {
    
    private static final String QUEUE_OUT = "Normalizer";
    private static final String HOST_NAME = "localhost";
    private static final double R = 5;

    /**
     * This is a sample web service operation
     * @param ssn
     * @param creditScore
     * @param loanAmount
     * @param loanDuration
     */
    @WebMethod(operationName = "LoanRequest")
    public void LoanRequest(@WebParam(name = "ssn") String ssn, @WebParam(name = "creditScore") int creditScore, @WebParam(name = "loanAmount") int loanAmount, @WebParam(name = "loanDuration") int loanDuration) {
        
        try {
            JSONObject json = new JSONObject();
            json.put("ssn", ssn);
            json.put("creditScore", creditScore);
            json.put("loanAmount", loanAmount);
            json.put("loanDuration", loanDuration);
            
            System.out.println("json = " + json.toString());
            
            JSONObject jsObj = calc(json);
            String send = jsObj.toString();
            send(send);
        } catch (JSONException ex) {
            Logger.getLogger(BankWS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BankWS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TimeoutException ex) {
            Logger.getLogger(BankWS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void send(String jsonClient) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST_NAME);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_OUT, false, false, false, null);
        String message = jsonClient;
        channel.basicPublish("", QUEUE_OUT, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }
    
    private static JSONObject calc(JSONObject msg){
        JSONObject js = new JSONObject();
        try {
            double r = R/100.0;
            double interest = msg.getInt("loanAmount") * r * msg.getInt("loanDuration"); 
            js.put("interest", interest);
            js.put("ssn", msg.getString("ssn"));
            js.put("sender", "SoapBank");
            
        } catch (JSONException ex) {
            Logger.getLogger(BankWS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return js;
    }
}
