/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Emil
 */
public class Receiver {

    private static final String QUEUE_NAME = "GetCreditScore";
    private static final String HOST_NAME = "datdb.cphbusiness.dk";
    private static final String USERNAME = "student";
    private static final String PASSWORD = "cph";

    public static void main(String[] args) throws IOException, TimeoutException {

        try {
            /*ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(HOST_NAME);
            factory.setUsername(USERNAME);
            factory.setPassword(PASSWORD);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            
            Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
            throws IOException {
            String message = new String(body, "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            }
            };
            channel.basicConsume(QUEUE_NAME, true, consumer);
             */
            //jsontran();
            RulebaseController rbc = new RulebaseController();
            JSONObject json = new JSONObject();
            json.put("ssn", "123456-7890");
            json.put("duration", 20);
            json.put("amount", "15000");
            json.put("creditscore", "567");
            System.out.println("ssn = " + json.getInt("creditscore"));
            List list = rbc.RequestBanks(json);
            System.out.println("list = " + list);
        } catch (JSONException ex) {
            Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
