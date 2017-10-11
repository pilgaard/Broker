/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package credit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;
import models.Client;
import models.ClientRequest;
import translator.Translator;
import static translator.XMLBankTranslator.QUEUE_NAME;

/**
 *
 * @author emil
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, TimeoutException {
        /*
        CreditController cc = new CreditController();
        RulebaseController rbc = new RulebaseController();
        Translator t = new Translator();

        //Create client
        Client c = new Client();
        c.SSN = "123456-7890";

        //Retrieve CS
        c.CreditScore = cc.GetCreditScore(c.SSN);

        c.request = new ClientRequest(15000, 100);

        //Get banks
        List<String> results = rbc.RequestBanks(c.CreditScore, c.request.loanAmount, c.request.loanDuration);

        //Translate
        t.Decode(results);
                */
        
        initQueue();

    }

    private static void initQueue() throws IOException, TimeoutException {
        String QUEUE_NAME = "GetCreditScore";
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("datdb.cphbusiness.dk");
        factory.setUsername("student");
        factory.setPassword("cph");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "Oh god I love memes";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }

}
