# Broker

## Beskrivelse af loanbroaker

Broker systemet består af en masse delkomponenter, som alle er skrevet i Java.
Alle disse delkomponenter snakker med hinanden via messaging.
Ved at sende en message til getCreditScore som indeholder et cpr.nr. et lånebeløb og løbetiden for lånet, 
vil systemet finde ud af hvilken bank der kan tilbyde dette lån til den bedste rente.

Vi har ikke nået at samle de forskellige delkomponenter til en webservice, så for at få det hele til at køre skal hvert program startes op inden man begynder at sende data igennem systemet.

Når alt er startet op sender man en besked igennem systemet ved at køre broker programmet, som vil sende en message afsted med et cpr nummer, lånebeløb og løbetiden for lånet. 

Vi nåede heller ikke at lave aggregator færdig, og man vil aldrig få et svar på hvilken bank der gav den bedste rente

## Diagrammer

Vores sequence diagram ser sådan ud
![Sequence Diagram](https://github.com/pilgaard/Broker/blob/master/diagrammer/Sequence%20diagram.png)

Og vores forskellige klassediagrammer

![Broker Diagram](https://github.com/pilgaard/Broker/blob/master/diagrammer/Broker.png)

![GetCreditScore Diagram](https://github.com/pilgaard/Broker/blob/master/diagrammer/GetCreditScore.png)

![GetBanks Diagram](https://github.com/pilgaard/Broker/blob/master/diagrammer/GetBanks.png)

![RuleBase Diagram](https://github.com/pilgaard/Broker/blob/master/diagrammer/RuleBase.png)

![RecipientList Diagram](https://github.com/pilgaard/Broker/blob/master/diagrammer/RecipientList.png)

![JsonTranslator Diagram](https://github.com/pilgaard/Broker/blob/master/diagrammer/JsonTranslator.png)

![XMLTranslator Diagram](https://github.com/pilgaard/Broker/blob/master/diagrammer/XMLTranslator.png)

![JsonBank Diagram](https://github.com/pilgaard/Broker/blob/master/diagrammer/JsonBank.png)

![XmlBank Diagram](https://github.com/pilgaard/Broker/blob/master/diagrammer/XmlBank.png)

![Normalizer Diagram](https://github.com/pilgaard/Broker/blob/master/diagrammer/Normalizer.png)

## Skærmbilleder

Her har vi modtaget creditscore
![creditscore image](https://github.com/pilgaard/Broker/blob/master/Images/1GotCreditScore.png)

Her har vi modtaget bankerne
![Recipients image](https://github.com/pilgaard/Broker/blob/master/Images/2GotRecipients.png)

Her sender vi til translatorne 
![SendingToTranslators image](https://github.com/pilgaard/Broker/blob/master/Images/3SendingToTranslators.png)

Her sender vi en JSON låneforspørgelse  
![SendingJSONLoanRequest image](https://github.com/pilgaard/Broker/blob/master/Images/4RecievedAndSendingJSONLoanRequest.png)

Her sender vi en XML låneforspørgelse
![SendingXMLLoanRequest image](https://github.com/pilgaard/Broker/blob/master/Images/4SendingXMLLoanRequest.png)

Her modtager Normalizeren svar fra en JSON bank
![NormalizerGotJSON image](https://github.com/pilgaard/Broker/blob/master/Images/5NormalizerGotJSONLoanResponse.png)

Her modtager Normalizeren svar fra en XML bank
![NormalizerGotXML image](https://github.com/pilgaard/Broker/blob/master/Images/5NormalizerGotXMLLoanResponse.png)


## Flaskehalse i programmet

1. Lige nu sætter 30 sekunder til hvor længe vi vil vente på svar fra bankerne, dette er et sted der får tiden til at trække ud. Da vi ikke ved på forhånd hvor mange banker der er blevet kontaktet, er vi heller ikke klar over hvor mange svar der skal ventes på, og vi må derfpr bare sige at vi venter i et stykke tid, og hvis en bank ikke har nået at svare til tiden vil dette tilbud ikke komme med i overvejelserne, til et tilbud til kunden.

  - en løsning på dette kunne være at der bliver sendt en besked fra RecipientList til aggregatoren om hvor mange banker der er sendt besked til
 
## testabilitet

Meget af Vores kode er ikke særlig let at teste. I flere af sub-systemerne ligger der en del kode i main-metoden, og derudover er der måske en enkelt eller to metoder mere, dette var lige det letteste i øjeblikket da koden blev skrevet, men efterfølgende, gør det hurtigt koden lidt uoverskuelig at få et hurtigt overblik over, og der sker alt for meget i hver metode til det vil være sjovt at skulle teste.
