/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CreditScoreController;

/**
 *
 * @author emil
 */
public class CreditController {

    public int GetCreditScore(String SSN) {
        int result =0;
        try { // Call Web Service Operation
            org.bank.credit.web.service.CreditScoreService_Service service = new org.bank.credit.web.service.CreditScoreService_Service();
            org.bank.credit.web.service.CreditScoreService port = service.getCreditScoreServicePort();
            // TODO process result here
            result = port.creditScore(SSN);
            System.out.println("Result = " + result);
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return result;
    }
    
    
}
