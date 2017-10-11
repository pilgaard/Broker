/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Andreas
 */
public class Client {
    
    public String SSN;
    public ClientRequest request;
    public int CreditScore;

    public int getCreditScore() {
        return CreditScore;
    }

    public String getSSN() {
        return SSN;
    }

    public ClientRequest getRequest() {
        return request;
    }

    public void setRequest(ClientRequest request) {
        this.request = request;
    }
    
    
    
}
