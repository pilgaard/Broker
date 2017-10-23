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

![GetCreditScore Diagram](https://github.com/pilgaard/Broker/blob/master/diagrammer/GetCreditScore.png)

![GetBanks Diagram](https://github.com/pilgaard/Broker/blob/master/diagrammer/GetBanks.png)

![RuleBase Diagram](https://github.com/pilgaard/Broker/blob/master/diagrammer/RuleBase.png)

![RecipientList Diagram](https://github.com/pilgaard/Broker/blob/master/diagrammer/RecipientList.png)

![JsonTranslator Diagram](https://github.com/pilgaard/Broker/blob/master/diagrammer/JsonTranslator.png)

![XMLTranslator Diagram](https://github.com/pilgaard/Broker/blob/master/diagrammer/XMLTranslator.png)

![JsonBank Diagram](https://github.com/pilgaard/Broker/blob/master/diagrammer/JsonBank.png)

![XmlBank Diagram](https://github.com/pilgaard/Broker/blob/master/diagrammer/XmlBank.png)

![Normalizer Diagram](https://github.com/pilgaard/Broker/blob/master/diagrammer/Normalizer.png)

