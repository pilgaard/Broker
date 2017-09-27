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
public class Consumer {
    
    private String SSN;
    private ConsumerRequest request;
    private int CreditScore;

    public int getCreditScore() {
        return CreditScore;
    }

    public String getSSN() {
        return SSN;
    }

    public ConsumerRequest getRequest() {
        return request;
    }

    public void setRequest(ConsumerRequest request) {
        this.request = request;
    }
    
    
    
}
