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
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CreditController cc = new CreditController();
            String SSN = "123456-7890";
            int result = cc.GetCreditScore(SSN);
            System.out.println(result);
    }
    
}
