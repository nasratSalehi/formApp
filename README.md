# Evently Forms

Backend service waarmee Evently inschrijfformulieren voor evenementen kan aanmaken en inzendingen kan opslaan. Gemaakt als technische opdracht.

## Starten

Je hebt alleen Docker nodig:

```bash
docker compose up --build
```

De API draait daarna op `http://localhost:8080`.

Swagger UI staat op `http://localhost:8080/swagger-ui.html`. Daar zie je alle endpoints en kun je ze direct uitproberen.

Stoppen doe je met `docker compose down`. Met `docker compose down -v` verwijder je ook de database.

## Wat ik heb gebouwd

Een REST API met JSON in en uit, op basis van drie begrippen:

- **Event:** een workshop, borrel of meet-up.
- **Form:** een inschrijfformulier dat bij één event hoort. Het formulier beschrijft welke velden er zijn.
- **Submission:** de ingevulde antwoorden van één persoon op een formulier.

De API kan:

- events en formulieren aanmaken en ophalen
- inzendingen opslaan en per formulier ophalen
- verplichte velden en veldtypes controleren
- duidelijke foutmeldingen teruggeven, met alle fouten tegelijk

**Stack:** Java 21, Spring Boot 4, Spring Data JPA (Hibernate), PostgreSQL 17, Docker Compose.

## Endpoints

| Methode | Pad | Omschrijving |
|---|---|---|
| POST | `/api/events` | Event aanmaken |
| GET | `/api/events` | Alle events ophalen |
| GET | `/api/events/{uuid}` | Eén event ophalen |
| GET | `/api/events/search?name=` | Event zoeken op naam |
| GET | `/api/events/{uuid}/forms` | Formulieren van een event ophalen |
| POST | `/api/forms` | Formulier aanmaken |
| GET | `/api/forms` | Alle formulieren ophalen |
| GET | `/api/forms/{uuid}` | Eén formulier ophalen |
| POST | `/api/forms/{uuid}/submissions` | Inzending opslaan |
| GET | `/api/forms/{uuid}/submissions` | Inzendingen van een formulier ophalen |

## Voorbeeld

Je kunt deze JSON gebruiken in Swagger UI of Postman.

**1. Maak een event aan** met `POST /api/events`:

```json
{
  "name": "Docker workshop",
  "eventType": "WORKSHOP"
}
```

**2. Maak een formulier aan** met `POST /api/forms`. Vul bij `eventId` het `id` uit de vorige response in:

```json
{
  "eventId": "EVENT-ID",
  "title": "Aanmelding Docker workshop",
  "fields": [
    { "name": "fullName", "label": "Naam", "fieldType": "TEXT", "required": true },
    { "name": "email", "label": "E-mail", "fieldType": "EMAIL", "required": true },
    { "name": "date", "label": "Datum", "fieldType": "DATE", "required": false }
  ]
}
```

**3. Verstuur een inzending** met `POST /api/forms/{uuid}/submissions`. Gebruik het `id` van het formulier als `uuid`:

```json
{
  "answers": {
    "fullName": "Sam",
    "email": "sam@example.com",
    "date": "2026-11-20"
  }
}
```

## Validatie

| Veldtype | Geldige waarde |
|---|---|
| `TEXT` | Tekst |
| `NUMBER` | JSON-getal, dus `12` en niet `"12"` |
| `BOOLEAN` | `true` of `false` |
| `DATE` | Datum in het formaat `yyyy-MM-dd` |
| `EMAIL` | Tekst in de vorm van een e-mailadres |

Een verplicht veld mag niet ontbreken of leeg zijn. Antwoorden op velden die niet op het formulier staan, worden geweigerd.

Bij ongeldige invoer krijg je een `400` met alle fouten:

```json
{
  "message": "Validation failed",
  "errors": [
    "fullName is required",
    "email must be a valid email"
  ]
}
```

Bestaat een event of formulier niet, dan krijg je een `404` met een `message`.

## Aannames

- Er is geen authenticatie: iedereen kan formulieren aanmaken en inzenden.
- Een formulier hoort bij precies één event. Een event kan meerdere formulieren hebben.
- Events en formulieren kunnen na het aanmaken niet worden gewijzigd of verwijderd. Zo blijven opgeslagen inzendingen altijd bij hun formulier passen.
- Eén persoon kan een formulier meerdere keren invullen, en er is geen maximum aantal inschrijvingen.
- Lijsten hebben geen paginering, omdat het om kleine evenementen gaat.
- De wachtwoorden in `compose.yaml` zijn alleen bedoeld voor lokaal gebruik.

## Keuzes

- **PostgreSQL:** de data blijft bewaard na een herstart, en `jsonb` past goed bij antwoorden die per formulier verschillen.
- **Antwoorden als `jsonb`:** elk formulier heeft andere velden, dus een vaste tabelstructuur past hier niet. Het nadeel is dat de database de inhoud niet zelf controleert. Dat doet `SubmissionValidatorService`.
- **Multi-stage Dockerfile:** de eerste stap bouwt de jar met Maven, de tweede draait die op een kleinere JRE-image.

## Verbeterpunten

- Keuzevelden (`CHOICE`) met vaste antwoordopties
- Authenticatie en autorisatie
- Paginering op lijsten
- Geautomatiseerde tests
- Databasemigraties met Flyway
