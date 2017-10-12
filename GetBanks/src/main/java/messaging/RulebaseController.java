/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author Andreas
 */
public class RulebaseController {

    public List<String> RequestBanks(JSONObject json) {
        List result = new ArrayList();
        try {
            rulebase.RuleBaseWeb_Service service = new rulebase.RuleBaseWeb_Service();
            rulebase.RuleBaseWeb port = service.getRuleBaseWebPort();
            System.out.println(json.getInt("creditscore"));
            result = port.getBanks(json.getInt("creditscore"), json.getInt("amount"), json.getInt("duration"));
        } catch (Exception ex) {
            System.out.println("ex = " + ex);
        }
        return result;
    }


}
