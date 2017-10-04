/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package credit;

import java.util.Date;

/**
 *
 * @author Andreas
 */
public class Rulebase {

    public Bank EvaluateCreditScore(int creditScore) throws Exception {
        if (creditScore >= 1 && creditScore <= 199)
            return Bank.SOFT_Json;
        else if (creditScore >= 200 && creditScore <= 399)
            return Bank.SOFT_XML;
        else if (creditScore >= 400 && creditScore <= 599)
            return Bank.BROKER_Json;
        else if (creditScore >= 600 && creditScore <= 800)
            return Bank.BROKER_XML;
        else
            throw new Exception("Invalid input");
    }

    public enum Bank {
        SOFT_Json,
        SOFT_XML,
        BROKER_Json,
        BROKER_XML
    }
    

}
