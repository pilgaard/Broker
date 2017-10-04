/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bank;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andreas
 */
public class BankController {

    private List<Bank> banks = new ArrayList<Bank>();

    public List<Bank> GetAcceptedBanks(int cs, int amount, int dur) {
        System.out.println("hello");
        List<Bank> acceptedBanks = new ArrayList<Bank>();
        for (int i = 0; i < banks.size(); i++) {
            if (banks.get(i).IsRequirementMet(cs, amount, dur)) {
                acceptedBanks.add(banks.get(i));
            }
        }
        return acceptedBanks;
    }

    public BankController() {
        Bank SOFT_XML_Bank = new Bank("SOFT_XML_Bank", 0, 1000, 10000, 90);
        Bank SOFT_JSON_Bank = new Bank("SOFT_JSON_Bank", 200, 10000, 30000, 120);
        Bank STUD_XML_Bank = new Bank("STUD_XML_Bank", 400, 20000, 50000, 180);
        Bank STUD_JSON_Tank = new Bank("STUD_JSON_Tank", 600, 100000, 500000, 240);

        banks.add(SOFT_XML_Bank);
        banks.add(SOFT_JSON_Bank);
        banks.add(STUD_XML_Bank);
        banks.add(STUD_JSON_Tank);
    }
}
