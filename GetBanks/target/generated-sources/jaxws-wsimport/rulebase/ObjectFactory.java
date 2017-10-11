
package rulebase;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the rulebase package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _BankToJsonResponse_QNAME = new QName("http://rulebase/", "BankToJsonResponse");
    private final static QName _GetBanks_QNAME = new QName("http://rulebase/", "GetBanks");
    private final static QName _BankToJson_QNAME = new QName("http://rulebase/", "BankToJson");
    private final static QName _GetBanksResponse_QNAME = new QName("http://rulebase/", "GetBanksResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: rulebase
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BankToJson }
     * 
     */
    public BankToJson createBankToJson() {
        return new BankToJson();
    }

    /**
     * Create an instance of {@link GetBanksResponse }
     * 
     */
    public GetBanksResponse createGetBanksResponse() {
        return new GetBanksResponse();
    }

    /**
     * Create an instance of {@link BankToJsonResponse }
     * 
     */
    public BankToJsonResponse createBankToJsonResponse() {
        return new BankToJsonResponse();
    }

    /**
     * Create an instance of {@link GetBanks }
     * 
     */
    public GetBanks createGetBanks() {
        return new GetBanks();
    }

    /**
     * Create an instance of {@link Bank }
     * 
     */
    public Bank createBank() {
        return new Bank();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BankToJsonResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://rulebase/", name = "BankToJsonResponse")
    public JAXBElement<BankToJsonResponse> createBankToJsonResponse(BankToJsonResponse value) {
        return new JAXBElement<BankToJsonResponse>(_BankToJsonResponse_QNAME, BankToJsonResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBanks }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://rulebase/", name = "GetBanks")
    public JAXBElement<GetBanks> createGetBanks(GetBanks value) {
        return new JAXBElement<GetBanks>(_GetBanks_QNAME, GetBanks.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BankToJson }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://rulebase/", name = "BankToJson")
    public JAXBElement<BankToJson> createBankToJson(BankToJson value) {
        return new JAXBElement<BankToJson>(_BankToJson_QNAME, BankToJson.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBanksResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://rulebase/", name = "GetBanksResponse")
    public JAXBElement<GetBanksResponse> createGetBanksResponse(GetBanksResponse value) {
        return new JAXBElement<GetBanksResponse>(_GetBanksResponse_QNAME, GetBanksResponse.class, null, value);
    }

}
