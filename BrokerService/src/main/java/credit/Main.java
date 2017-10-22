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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * @author emil
 */
public class Main {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, TimeoutException {
        
        CreditController cc = new CreditController();
        //RulebaseController rbc = new RulebaseController();
        Translator t = new Translator();

        //Create client
        Client c = new Client();
        c.ssn = "123456-7890";
        c.duration = 100;
        c.amount = 15000;

        //Retrieve CS
        //c.CreditScore = cc.GetCreditScore(c.SSN);

        //Get banks
        //List<String> results = rbc.RequestBanks(c.CreditScore, c.request.loanAmount, c.request.loanDuration);

        //Translate
        //t.Decode(results);
                
        
        initQueue(c);

    }

    private static void initQueue(Client client) throws IOException, TimeoutException {
        String QUEUE_NAME = "GetCreditScore";
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("datdb.cphbusiness.dk");
        factory.setUsername("student");
        factory.setPassword("cph");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = gson.toJson(client);
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }

}
