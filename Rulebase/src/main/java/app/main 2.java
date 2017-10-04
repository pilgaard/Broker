/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import javax.xml.ws.Endpoint;
import service.RuleService;

/**
 *
 * @author Andreas
 */
public class main {
    
    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8089/rules", new RuleService());
    }
    
}
