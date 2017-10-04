/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.Bank;
import controller.BankController;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Holder;

/**
 *
 * @author Andreas
 */

@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public class RuleService {
    
    private BankController bankController;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    public RuleService(){
        this.bankController = new BankController();
    }
    
    public String BankToJson(Bank bank){
        return gson.toJson(bank.getName());
    }

    @WebMethod(operationName="GetBanks")
    public List<String> asdsad(@WebParam(name="cs") int cs, @WebParam(name="amount") int amount, @WebParam(name="dur")int dur){
        List<Bank> banks = bankController.GetAcceptedBanks(cs, amount, dur);
        List<String> JsonBanks = new ArrayList<>();
        for (int i = 0; i < banks.size(); i++) {
            JsonBanks.add(BankToJson(banks.get(i)));
        }
        return JsonBanks;
    }
    
}
