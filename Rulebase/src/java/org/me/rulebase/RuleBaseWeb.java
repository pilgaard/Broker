/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.rulebase;

import Bank.Bank;
import Bank.BankController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author Andreas
 */
@WebService(serviceName = "RuleBaseWeb")
public class RuleBaseWeb {

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private BankController bankController = new BankController();

    public String BankToJson(Bank bank) {
        return gson.toJson(bank.getName());
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "GetBanks")
    public List<String> GetBanks(@WebParam(name = "cs") int cs, @WebParam(name = "amount") int amount, @WebParam(name = "dur") int dur) {
        List<Bank> banks = bankController.GetAcceptedBanks(cs, amount, dur);
        List<String> JsonBanks = new ArrayList<>();
        for (int i = 0; i < banks.size(); i++) {
            JsonBanks.add(BankToJson(banks.get(i)));
        }
        return JsonBanks;
    }
}
