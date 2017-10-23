/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jsonbank;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;

/**
 *
 * @author Emil
 */
import org.json.JSONObject;

public class Main {

    private static final String QUEUE_IN = "Banks";
    private static final String QUEUE_OUT = "Normalizer";
    private static final String HOST_NAME = "datdb.cphbusiness.dk";
    private static final double R = 3.14;

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST_NAME);
        Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_IN, false, false, false, null);

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                
                try {
                    String recievedMessage = new String(body, "UTF-8");
                    JSONObject json = new JSONObject(recievedMessage);
                    System.out.println("json = " + json.toString());
                    
                    JSONObject jsObj = calc(json);
                    String send = jsObj.toString();
                    send(send);
                } catch (JSONException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (TimeoutException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        channel.basicConsume(QUEUE_IN, true, consumer);
        
    }

    public static void send(String jsonClient) throws IOException, TimeoutException {

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
            double interest = msg.getInt("amount") * r * msg.getInt("duration"); 
            js.put("interest", interest);
            js.put("ssn", msg.getString("ssn"));
            
        } catch (JSONException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return js;
    }
}
