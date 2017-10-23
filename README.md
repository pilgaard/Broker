# Broker

## Beskrivelse af loanbroaker

Broker systemet består af en masse delkomponenter, som alle er skrevet i Java.
Alle disse delkomponenter snakker med hinanden via messaging.
Ved at sende en message til getCreditScore som indeholder et cpr.nr. et lånebeløb og løbetiden for lånet, 
vil systemet finde ud af hvilken bank der kan tilbyde dette lån til den bedste rente.

## Diagrammer

Vores sequence diagram ser sådan ud
![Sequence Diagram](https://github.com/pilgaard/Broker/blob/master/diagrammer/Sequence%20diagram.png)

Og vores forskellige klassediagrammer
![GetBanks Diagram](https://github.com/pilgaard/Broker/blob/master/diagrammer/GetBanks.png)

![GetCreditScore Diagram](https://github.com/pilgaard/Broker/blob/master/diagrammer/GetCreditScore.png)

![JsonBank Diagram](https://github.com/pilgaard/Broker/blob/master/diagrammer/JsonBank.png)

![JsonTranslator Diagram](https://github.com/pilgaard/Broker/blob/master/diagrammer/JsonTranslator.png)

![RecipientList Diagram](https://github.com/pilgaard/Broker/blob/master/diagrammer/RecipientList.png)

![RuleBase Diagram](https://github.com/pilgaard/Broker/blob/master/diagrammer/RuleBase.png)

![XMLTranslator Diagram](https://github.com/pilgaard/Broker/blob/master/diagrammer/XMLTranslator.png)

![XmlBank Diagram](https://github.com/pilgaard/Broker/blob/master/diagrammer/XmlBank.png)

