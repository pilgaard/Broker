/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.json.JSONObject;

/**
 *
 * @author Andreas
 */
public class RecipientListReciever {
    
    private static final String QUEUE_IN = "RecipientList";
    private static final String QUEUE_OUT_JSON = "Translator_JSON";
    private static final String QUEUE_OUT_XML = "Translator_XML";
    private static final String HOST_NAME = "datdb.cphbusiness.dk";
    private static final String USERNAME = "student";
    private static final String PASSWORD = "cph";
    
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
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
                System.out.println(recievedMessage);
            }
        };
        channel.basicConsume(QUEUE_IN, true, consumer);
    }
    
}
