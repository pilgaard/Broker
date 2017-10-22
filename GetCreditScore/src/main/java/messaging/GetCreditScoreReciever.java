/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging;

import CreditScoreController.CreditController;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;

/**
 *
 * @author Andreas
 */
import org.json.JSONObject;

public class GetCreditScoreReciever {

    private static final String QUEUE_IN = "GetCreditScore";
    private static final String QUEUE_OUT = "GetBanks";
    private static final String HOST_NAME = "datdb.cphbusiness.dk";
    private static final String USERNAME = "student";
    private static final String PASSWORD = "cph";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        final CreditController cc = new CreditController();
        factory.setHost(HOST_NAME);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);
        Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_IN, false, false, false, null);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String recievedMessage = new String(body, "UTF-8");
                try {
                    JSONObject json = new JSONObject(recievedMessage);
                    int result = cc.GetCreditScore(json.get("ssn").toString());
                    json.put("cs", result);
                    String send = json.toString();
                    send(send);
                } catch (JSONException ex) {
                    Logger.getLogger(GetCreditScoreReciever.class.getName()).log(Level.SEVERE, null, ex);
                } catch (TimeoutException ex) {
                    Logger.getLogger(GetCreditScoreReciever.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        channel.basicConsume(QUEUE_IN, true, consumer);
    }

    public static void send(String jsonClient) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST_NAME);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_OUT, false, false, false, null);
        String message = jsonClient;
        channel.basicPublish("", QUEUE_OUT, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();

    }

}
