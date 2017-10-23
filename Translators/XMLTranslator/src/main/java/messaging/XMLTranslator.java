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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author Andreas
 */
public class XMLTranslator {

    private static final String Normalizer_Queue = "Normalizer";
    private static final String QUEUE_IN = "Translator_CPH_XML";
    private static final String QUEUE_OUT = "cphbusiness.bankXML";
    private static final String HOST_NAME = "datdb.cphbusiness.dk";

    public static void main(String[] args) throws IOException, TimeoutException {
        recieve();
    }

    private static String FormatMessage(String message) {
        JSONObject json = new JSONObject(message);
        String ssn = json.getString("ssn");
        String newssn = ssn.replace("-", "");
        int duration = json.getInt("duration");
        int amount = json.getInt("amount");
        int cs = json.getInt("cs");
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970, 0, 1);
        calendar.add(Calendar.MONTH, duration);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        String newdate = format.format(calendar.getTime());
        String xml = "<LoanRequest>\n"
                + "   <ssn>" + newssn + "</ssn>\n"
                + "   <creditScore>" + cs + "</creditScore>\n"
                + "   <loanAmount>" + amount + "</loanAmount>\n"
                + "   <loanDuration>" + newdate + "</loanDuration>\n"
                + "</LoanRequest>";
        return xml;
    }

    private static void recieve() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST_NAME);
        factory.setUsername("student");
        factory.setPassword("cph");
        Connection connection = factory.newConnection();       
        Channel sendChannel = connection.createChannel();
        sendChannel.exchangeDeclare(QUEUE_IN, "direct");
        Channel replyChannel = connection.createChannel();
        replyChannel.queueDeclare(Normalizer_Queue, true, false, false, null);
        String replyQueue = replyChannel.queueDeclare().getQueue();
        replyChannel.queueBind(replyQueue, QUEUE_IN, "BankXML");
        Consumer consumer = new DefaultConsumer(replyChannel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String receivedMessage = new String(body);
                System.out.println("Received message: " + receivedMessage);
                try {
                    send(FormatMessage(receivedMessage));

                } catch (TimeoutException ex) {
                    Logger.getLogger(XMLTranslator.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        replyChannel.basicConsume(replyQueue, false, consumer);
    }

    private static void send(String message) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST_NAME);
        factory.setUsername("student");
        factory.setPassword("cph");
        Connection connection = factory.newConnection();
        Channel JSONChannel = connection.createChannel();
        String replyKey = "xmlbank";
        JSONChannel.exchangeDeclare(QUEUE_OUT, "fanout");
        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties()
                .builder()
                .replyTo(Normalizer_Queue)
                .correlationId("BankXML")
                .build();
        System.out.println("Sent message: " + message);
        JSONChannel.basicPublish(QUEUE_OUT, replyKey, basicProperties, message.getBytes());
        JSONChannel.close();
        connection.close();
    }

}
