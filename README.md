# Broker

Broker systemet består af en masse delkomponenter, som alle er skrevet i Java.
Alle disse delkomponenter snakker med hinanden via messaging.
Ved at sende en message til getCreditScore som indeholder et cpr.nr. et lånebeløb og løbetiden for lånet, 
vil systemet finde ud af hvilken bank der kan tilbyde dette lån til den bedste rente.
