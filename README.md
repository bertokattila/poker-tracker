# Poker Tracker specification

## Overview

This application intends to help professional poker players to follow and analyze their performance.
They can administrate their sessions by giving various information about them. This data is stored securely and can be accessed at any time.
The application creates other useful numerical and visual statistics derived from the poker sessions. Users can have social connections and get notifications about each other's game results.

## User-stories

| ID  | As a         | I want to                                                                             | So that I can                                                       |
| --- | ------------ | ------------------------------------------------------------------------------------- | ------------------------------------------------------------------- |
| 1   | Poker player | record how much time I played on a day                                                | use it to analyze my performance                                    |
| 2   | Poker player | record how much money I cashed in                                                     | use it to analyze my performance                                    |
| 3   | Poker player | record how much money I cashed out                                                    | use it to analyze my performance                                    |
| 4   | Poker player | access statistics about my peformance history                                         | summerize my profit or loss                                         |
| 5   | Poker player | have information my average result per hour                                           | interpret it as my hourly wage                                      |
| 6   | Poker player | administrate my poker sessions                                                        | use it to analyze my performance                                    |
| 7   | Poker player | add comments as session information                                                   | later read this information                                         |
| 8   | Poker player | add location data to my sessions                                                      | so I can later follow how I performed in different sites            |
| 9   | Poker player | add date information to my sessions                                                   | so I can later follow which days I played                           |
| 10  | Poker player | add size information to sessions (small/big blind e.g. 100/200)                       | have access to this information later                               |
| 11  | Poker player | add type information to my sessions (cash game/tournament)                            | have access to this information later                               |
| 12  | Poker player | add type information about tournaments                                                | so I can later analyze this information (MTT, Sit&Go, Turbo Sit&Go) |
| 13  | Poker player | have statistics about my win rate for different time periods (day, week, month, year) | use it to analyze my performance                                    |
| 14  | Poker player | know how much was my highest win                                                      | use it to analyze my performance                                    |
| 15  | Poker player | know how much I won or lost all time                                                  | use it to analyze my performance                                    |
| 16  | Poker player | watch visual charts about my statistics                                               | have a better understanding about my tendencies                     |
| 17  | Poker player | be able to register and log in                                                        | be sure no one else is able to access and modify my data            |
| 18  | Poker player | add other poker players to my friend list                                             | get notifications about their sessions and see their results        |
| 19  | Poker player | decide which of my sessions are private or public                                     | control what other players know about my performance                |
| 20  | Poker player | be able to use different currencies (HUF, EUR, USD, etc.)                             | add games played in different countries                             |
| 21  | Poker player | have bar charts about my sessions in weekly, monthly and yearly intervals             | can review my performance in different intervals                    |

## Architecture plan

![arch drawio-3](https://user-images.githubusercontent.com/22593928/157098965-c7000ce8-68b6-4f92-8d37-5b5712524471.svg)

## Data model

### Session service

#### Session table

| Column name | Type        | Description                                          | Nullable |
| ----------- | ----------- | ---------------------------------------------------- | -------- |
| ID          | Number      | Session ID, Primary Key, Auto Inc.                   | False    |
| UserID      | Number      | Foreign Key: User Service / User table / ID          | False    |
| Type        | Varchar     | Session type enum stored as Varchar                  | False    |
| Currency    | Varchar (3) | Currency acronym like (HUF, EUR, etc.)               | False    |
| BuyIn       | Number      | Amount of money used to buy in                       | False    |
| CashOut     | Number      | Amount of money cashed out at the end of the session | False    |
| Date        | Date        | Date of the session                                  | False    |
| Comment     | Text        | Comment provided at the time of the administration   | True     |
| LocationId  | Number      | Foreign Key: Session Service / Location table / ID   | True     |

#### Location table

| Column name | Type    | Description                                           | Nullable |
| ----------- | ------- | ----------------------------------------------------- | -------- |
| ID          | Number  | Location ID, Primary Key, Auto Inc.                   | False    |
| Name        | Varchar | Location name, can be anything (e.g. Duna Poker Club) | False    |

### Statisitcs service

#### Generic_Statistics table

| Column name         | Type   | Description                                | Nullable |
| ------------------- | ------ | ------------------------------------------ | -------- |
| ID                  | Number | User ID, Primary Key, Auto Inc.            | False    |
| UserID              | Number | User ID, Foreign Key                       | False    |
| LastMonthResult     | Number | Profit/loss result in the last 30 days     | False    |
| LastYearResult      | Number | Profit/loss result in the last 365 days    | False    |
| AllTimeResult       | Number | Profit/loss result all time                | False    |
| LastMonthPlayedTime | Number | Minutes spent playing in the last 30 days  | False    |
| LastYearPlayedTime  | Number | Minutes spent playing in the last 365 days | False    |
| AllTimePlayedTime   | Number | Minutes spent playing all time             | False    |

#### Statistics_History table

| Column name | Type    | Description                              | Nullable |
| ----------- | ------- | ---------------------------------------- | -------- |
| ID          | Number  | Record ID, Primary Key, Auto Inc.        | False    |
| UserID      | Number  | User ID, Foregin Key                     | False    |
| StartDate   | Date    | Starting date of the statistics interval | False    |
| EndDate     | Date    | Ending date of the statistics interval   | False    |
| Result      | Number  | Session type enum stored as Varchar      | False    |
| PlayedTime  | Number  | Currency acronym like (HUF, EUR, etc.)   | False    |
| Type        | Varchar | Cash game or tournament                  | False    |

### User service

#### User table

| Column name     | Type             | Description                                                   | Nullable |
| --------------- | ---------------- | ------------------------------------------------------------- | -------- |
| ID              | Number           | User ID, Primary Key, Auto Inc.                               | False    |
| Name            | Varchar          | Name of the player                                            | False    |
| Email           | Varchar          | E-mail address of the player, used at login                   | False    |
| Password        | Varchar (Hashed) | Hashed password, used at authentication                       | False    |
| DefaultCurrency | Varchar (3)      | Default currency for the user (must be given at registration) | False    |

#### Social table

| Column name  | Type       | Description                                                            | Nullable |
| ------------ | ---------- | ---------------------------------------------------------------------- | -------- |
| UserIDMaster | Number     | User ID of the player who initiated the social connection, Foreign key | False    |
| UserIDSlave  | Number     | User ID of the player who did not initiate the connection, Foreign key | False    |
| Active       | Number (1) | Boolean value, true if slave accepted the request                      | False    |

#### Notifications table

| Column name | Type       | Description                                                                                    | Nullable |
| ----------- | ---------- | ---------------------------------------------------------------------------------------------- | -------- |
| UserID      | Number     | User ID of the player who is the receiver of the notification                                  | False    |
| SessionId   | Number     | ID of the session this notification is about, Foreign key Session Service / Session table / ID | False    |
| Seen        | Number (1) | False initially, true if seen                                                                  | False    |

## API endpoints

If response column is empty, only http status code is expected.
Filter data payload generally contains limit, offset and other resource specific fields.
Most of the endpoints only respond if a valid access token is attached.

### Nginx static

| Path | Type | Description         |
| ---- | ---- | ------------------- |
| `/`  | GET  | Serving Angular SPA |

### Session service

| Path                   | Type   | Request                | Response      | Description                        |
| ---------------------- | ------ | ---------------------- | ------------- | ---------------------------------- |
| `/session/`            | POST   | session data           |               | Adding new session resource        |
| `/session/?userid`     | GET    | user ID, filter params | session data  | Getting list of user's sessions    |
| `/session/{sessionId}` | GET    | session ID             | session data  | Getting session resource by its id |
| `/session/{sessionId}` | PUT    | session data           |               | Modifying session                  |
| `/session/{sessionId}` | DELETE | session ID             |               | Deleting session by its id         |
| `/location/`           | POST   | location data          |               | Adding new location resource       |
| `/location/{id}`       | GET    | filter data            | location data | Getting list of locations          |
| `/location/?sessionId` | GET    | filter data            | location data | Getting list of locations          |

### Statisitcs service

| Path                                         | Type | Request                                                            | Response                | Description                                                                |
| -------------------------------------------- | ---- | ------------------------------------------------------------------ | ----------------------- | -------------------------------------------------------------------------- |
| `/statistics/refresh/?userId`                | POST | user id                                                            |                         | Updates the given user's stats                                             |
| `/statistics/general/?userId,interval`/      | GET  | user id, interval (month, year, all)                               | statistics data         | Returns general statistics about the given user for the specified interval |
| `/statistics/history/?userId,interval,type`/ | GET  | user id, interval (month, year, all), type (cash, tournament, all) | list of statistics data | Returns statistics history data                                            |

### User service

| Path                       | Type | Request                                        | Response             |
| -------------------------- | ---- | ---------------------------------------------- | -------------------- |
| `/registration`            | POST | email, password, name, default currency        |                      |
| `/login`                   | POST | email, password                                | access token         |
| `/logout`                  | POST |                                                |                      |
| `/socialconnection/`       | POST | userId, userId2                                |                      |
| `/notifiation/?userId`     | GET  | userId, filter data (last x)                   | list of notifiations |
| `/notification/?sessionId` | POST | creating notifiations about a session activity |                      |
