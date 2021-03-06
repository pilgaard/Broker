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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Emil
 */
public class GetBanksReciever {

    private static final String QUEUE_IN = "GetBanks";
    private static final String QUEUE_OUT = "RecipientList";
    private static final String HOST_NAME = "datdb.cphbusiness.dk";
    private static final String USERNAME = "student";
    private static final String PASSWORD = "cph";

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        final RulebaseController rbc = new RulebaseController();
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
                    //System.out.println(rbc.RequestBanks(json));
                    send(rbc.RequestBanks(json), json);
                } catch (JSONException ex) {
                    Logger.getLogger(GetBanksReciever.class.getName()).log(Level.SEVERE, null, ex);
                } catch (TimeoutException ex) {
                    Logger.getLogger(GetBanksReciever.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        };
        channel.basicConsume(QUEUE_IN, true, consumer);
    }

    public static void send(List<String> jsonBanks, JSONObject jsonObj) throws IOException, TimeoutException, JSONException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST_NAME);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_OUT, false, false, false, null);
        JSONArray jarray = new JSONArray();
        for (int i = 0; i < jsonBanks.size(); i++) {
            jarray.put(jsonBanks.get(i));

        }
        jsonObj.put("banks", jarray);
        String message = jsonObj.toString();
        channel.basicPublish("", QUEUE_OUT, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();

    }
}
