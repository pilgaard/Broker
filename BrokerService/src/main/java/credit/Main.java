/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package credit;

import java.util.List;
import models.Client;
import models.ClientRequest;
import translator.Translator;

/**
 *
 * @author emil
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
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
        
    }

}
