/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(serviceName="SOAPService") 
public class SOAPService {
    
    @WebMethod(operationName="HelloWorld") 
    public String HelloWorld(@WebParam(name="name") String name){
        return "Hello World " + name;
    }
    
}
