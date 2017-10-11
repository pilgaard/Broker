/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package credit;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Andreas
 */
public class RulebaseController {

    public List<String> RequestBanks(int cs, int amount, int dur) {
        try {
            rulebase.RuleBaseWeb_Service service = new rulebase.RuleBaseWeb_Service();
            rulebase.RuleBaseWeb port = service.getRuleBaseWebPort();
            java.util.List<java.lang.String> result = port.getBanks(cs, amount, dur);
            System.out.println("Result = " + result);
            return result;
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

}
