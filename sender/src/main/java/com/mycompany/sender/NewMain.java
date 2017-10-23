/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.sender;


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
public class NewMain {
    private static final String QUEUE_IN = "Banks";
    private static final String QUEUE_OUT = "Banks";
    private static final String HOST_NAME = "localhost";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(HOST_NAME);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            
            channel.queueDeclare(QUEUE_IN, false, false, false, null);
            
            
            JSONObject obj = new JSONObject();
            obj.put("ssn", "123456-7890");
            obj.put("amount", 10);
            obj.put("duration", 360);
            String message = obj.toString();
            channel.basicPublish("", QUEUE_IN, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
            
            channel.close();
            connection.close();
        } catch (IOException | TimeoutException | JSONException ex) {
            Logger.getLogger(NewMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
}
