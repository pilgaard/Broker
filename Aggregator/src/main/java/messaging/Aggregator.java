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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Andreas
 */
public class Aggregator {

    private static final String QUEUE_IN = "Aggregator";
    private static final String HOST_NAME = "datdb.cphbusiness.dk";
    private static final String USERNAME = "student";
    private static final String PASSWORD = "cph";
    private static List<String> tempList = new ArrayList<>();
    private static List<String> messages = new ArrayList<>();
    private static boolean isRunning = false;

    public static void main(String[] args) throws IOException, TimeoutException {
        recieve();
    }

    private static void recieve() throws IOException, TimeoutException {
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
                if (isRunning == true) {
                    tempList.add(recievedMessage);
                }
                if (isRunning == false) {
                    if (tempList.size() > 0) {
                        messages = tempList;
                        FindBest();
                    }
                    if(tempList.size() == 0){
                        tempList.add(recievedMessage);
                    }
                    tempList.clear();
                    sleeper.run();
                }

            }
        };
        channel.basicConsume(QUEUE_IN, true, consumer);
    }

    private static final Runnable sleeper = new Runnable() {

        public void run() {
            try {
                while (true) {
                    isRunning = true;
                    Thread.sleep(1000*30);
                    isRunning = false;
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Aggregator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    };

    private static void FindBest() {
        //Logic to find best result
    }

}
