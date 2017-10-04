/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bank;

/**
 *
 * @author Andreas
 */
public class Bank {

    String name;

    int minCreditScore;

    int minLoanAmount;
    int maxLoanAmount;

    int maxLoanDuration;

    public boolean IsRequirementMet(int cs, int amount, int dur) {
        int i = 0;
        if (cs > minCreditScore) {
            i++;
        }
        if (amount > minLoanAmount && amount < maxLoanAmount) {
            i++;
        }
        if (dur < maxLoanDuration) {
            i++;
        }
        System.out.println(i);
        if (i == 3) {
            return true;
        } else {
            return false;
        }
    }

    public Bank(String name, int minCreditScore, int minLoanAmount, int maxLoanAmount, int maxLoanDuration) {
        this.name = name;
        this.minCreditScore = minCreditScore;
        this.minLoanAmount = minLoanAmount;
        this.maxLoanAmount = maxLoanAmount;
        this.maxLoanDuration = maxLoanDuration;
    }

    public String getName() {
        return name;
    }
}
