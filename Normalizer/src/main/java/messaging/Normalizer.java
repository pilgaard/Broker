/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

/**
 *
 * @author Andreas
 */
public class Normalizer {

    private static final String QUEUE_OUT = "Aggregator";
    private static final String HOST_NAME = "datdb.cphbusiness.dk";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST_NAME);
        factory.setUsername("student");
        factory.setPassword("cph");
        Connection connection = factory.newConnection();
        Channel receiveChannel = connection.createChannel();
        receiveChannel.queueDeclare("Normalizer", true, false, false, null);
        String replyQueue = "Normalizer";
        recieve(receiveChannel, replyQueue);
    }

    private static void send(String message) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST_NAME);
        factory.setUsername("student");
        factory.setPassword("cph");
        Connection connection = factory.newConnection();
        Channel aggregatorChannel = connection.createChannel();
        aggregatorChannel.queueDeclare(QUEUE_OUT, true, false, false, null);
        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties()
                .builder()
                .correlationId("Normalizer")
                .build();
        aggregatorChannel.basicPublish("", QUEUE_OUT, basicProperties, message.getBytes());

        aggregatorChannel.close();
        connection.close();

    }

    private static void recieve(final Channel channel, String queue) throws IOException {
        Consumer qc = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String receivedMessage = new String(body);
                System.out.println("Received message: " + receivedMessage);
                channel.basicAck(envelope.getDeliveryTag(), false);
                System.out.println(envelope.getDeliveryTag());
                try {
                    send(ConverToJSON(properties.getCorrelationId(), receivedMessage));
                } catch (TimeoutException ex) {
                    Logger.getLogger(Normalizer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        channel.basicConsume(queue, false, qc);
    }

    private static String ConverToJSON(String coID, String message) {
        String ssn;
        double rate;
        String converted = "";
        try {
            if (coID.contains("XML")) {
                JSONObject xml = XML.toJSONObject(message);
                JSONObject response = xml.getJSONObject("LoanResponse");
                ssn = response.get("ssn").toString();
                rate = response.getDouble("interestRate");
            } else {
                JSONObject json = new JSONObject(message);
                ssn = json.get("ssn").toString();
                rate = json.getDouble("interestRate");
            }
            if (!ssn.contains("-")) {
                String tempSSN = ssn;
                String ssn1 = tempSSN.substring(0, 6);
                String ssn2 = tempSSN.substring(6, 10);
                String ssnFull = ssn1 + "-" + ssn2;
                ssn = ssnFull;
            }
            converted = "{\"ssn\":\"" + ssn + "\","
                    + "\"interestRate\":" + rate + ","
                    + "\"bank\":\"" + coID + "\"}";

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return converted;
    }

}
