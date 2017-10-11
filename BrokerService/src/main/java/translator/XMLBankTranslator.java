/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package translator;

import com.rabbitmq.client.AMQP;
import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author Andreas
 */
public class XMLBankTranslator {
    
    public static String QUEUE_NAME;
    private final static String EXCHANGE_NAME = "cphbusiness.bankXML";
    private final static String HOST_NAME = "datdb.cphbusiness.dk";
    
    static String tempmsg = "<LoanRequest>"
                + "<ssn>12345678</ssn>"
                + "<creditScore>685</creditScore>"
                + "<loanAmount>1000.0</loanAmount>"
                + "<loanDuration>2020-01-01 01:00:00.0 CET</loanDuration>"
                + "</LoanRequest>";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST_NAME);
        factory.setUsername("student");
        factory.setPassword("cph");
        Connection connection = factory.newConnection();

        Channel sendChannel = connection.createChannel();
        sendChannel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        Channel replyChannel = connection.createChannel();
        String replyQueue = replyChannel.queueDeclare().getQueue();

        //TODO - receive message from other source and call send wih that message
        receive(connection, replyChannel, replyQueue);
        send(replyChannel, replyQueue);
    }

    private static void send(Channel chan, String queue) throws IOException, TimeoutException {
        String replyKey = "xmlbank";
        chan.exchangeDeclare(EXCHANGE_NAME, "fanout");
        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties()
                .builder()
                .replyTo(queue)
                .build();
        chan.basicPublish(EXCHANGE_NAME, replyKey, basicProperties, tempmsg.getBytes());
    }

    private static void receive(Connection conn, Channel chan, String queue) throws ShutdownSignalException, InterruptedException, ConsumerCancelledException, TimeoutException, IOException {
        Consumer qc = new DefaultConsumer(chan) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String replyMessage = new String(body);
                System.out.println("Reply: " + replyMessage);
            }
        };
        chan.basicConsume(queue, false, qc);
    }
    
}
