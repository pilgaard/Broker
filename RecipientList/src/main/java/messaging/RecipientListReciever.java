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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Andreas
 */
public class RecipientListReciever {

    private static final String QUEUE_IN = "RecipientList";
    private static final String QUEUE_OUT_CPHJSON = "Translator_CPH_JSON";
    private static final String QUEUE_OUT_CPHXML = "Translator_CPH_XML";
    private static final String QUEUE_OUT_STUDJSON = "Translator_STUD_JSON";
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
                try {
                    send(recievedMessage, GetBanksFromJson(recievedMessage));
                } catch (TimeoutException ex) {
                    Logger.getLogger(RecipientListReciever.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JSONException ex) {
                    Logger.getLogger(RecipientListReciever.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        channel.basicConsume(QUEUE_IN, true, consumer);
    }

    private static List<String> GetBanksFromJson(String message) throws JSONException {
        JSONObject json = new JSONObject(message);
        List<String> recipients = new ArrayList<>();
        for (int i = 0; i < json.getJSONArray("banks").length(); i++) {
            if (json.getJSONArray("banks").get(i).toString().split("\\.")[0] == "CPH") {
                if (json.getJSONArray("banks").get(i).toString().split("\\.")[1] == "JSON") {
                    recipients.add(QUEUE_OUT_CPHJSON);
                } else if (json.getJSONArray("banks").get(i).toString().split("\\.")[1] == "XML") {
                    recipients.add(QUEUE_OUT_CPHXML);
                }
            } else if (json.getJSONArray("banks").get(i).toString().split("\\.")[0] == "STUD") {
                if (json.getJSONArray("banks").get(i).toString().split("\\.")[1] == "JSON") {
                    recipients.add(QUEUE_OUT_STUDJSON);
                }
            }
        }
        return recipients;
    }

    public static void send(String message, List<String> recipients) throws IOException, TimeoutException, JSONException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST_NAME);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        for (int i = 0; i < recipients.size(); i++) {
            String QUEUE_OUT = recipients.get(i);
            channel.queueDeclare(QUEUE_OUT, false, false, false, null);
            channel.basicPublish("", QUEUE_OUT, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
        channel.close();
        connection.close();

    }

}
