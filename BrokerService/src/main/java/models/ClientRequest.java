/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Andreas
 */
public class ClientRequest {
    
    public int loanAmount;
    public int loanDuration;

    public ClientRequest(int loanAmount, int loanDuration) {
        this.loanAmount = loanAmount;
        this.loanDuration = loanDuration;
    }
    
    

    public int getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(int loanAmount) {
        this.loanAmount = loanAmount;
    }

    public int getLoanDuration() {
        return loanDuration;
    }

    public void setLoanDuration(int loanDuration) {
        this.loanDuration = loanDuration;
    }
    
    
}
