/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package credit;

/**
 *
 * @author emil
 */
public class CreditController {
    
    private String CreditScoreUrl = "http://138.68.85.24:8080/CreditScoreService/CreditScoreService?wsdl";

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
