# Cube Roll

## Description
Welcome to Cube Roll. The point of the game is to roll dice with one colored side and fit that side on finish tile in the least amount of moves possible. You can roll dice in 4 different directions but beware of holes in map if u drop in you will lose life. Amount of lifes depends on map. Wall tiles will not let u walk on them you are not Spiderman.

## Requirements

- __Java__ (21 or later)  
- __PostgreSQL__ (make sure your version is matching the version in pom.xml) 
- __Maven__ (latest)
- __Spring Boot__(latest)
- __Spring Data JPA__(latest)
- __Spring WEB REST__(latest)

## Database

This game uses PostgreSQL database 'gamestudio' that has following tables with following columns:
```
score
  - player : TEXT
  - game : TEXT
  - points : INTEGER
  - playedOn: DATE

comment
  - player : TEXT
  - game : TEXT
  - comment : TEXT
  - commentedOn: DATE

rating
  - player : TEXT
  - game : TEXT
  - rating : INTEGER
  - ratedOn: DATE
```

If you are using a custom PostgreSQL username and password, update the database credentials in all serviceJDBC files.

Make sure PostgreSQL is running and the 'gamestudio' database is set up before launching the game.
## Usage

You can run the game by executing [GameStudioServe.java](src/main/java/sk/tuke/gamestudio/server/GameStudioServer.java)
file in your IDE to start Server part and after inicializing execute [SpringClient.java](src/main/java/sk/tuke/gamestudio/SpringClient.java) as klient part of game. You will be presented with a menu if u need to know rules u can load them or jump into the game starting with your name, after which you will select a map. Each map is fully solvable in the least amount of moves possible. Based on the amount of moves, you will be rewarded with a score. While playing, you can also view and add comments for game, view top scores, rate the game, restart map or go back to main menu.
Alternatively you can run the game in web browser by executing [GameStudioServe.java](src/main/java/sk/tuke/gamestudio/server/GameStudioServer.java) then going to http://localhost:8080/login in your browser. Unlike in IDE, you will have to log in. If your username does not yet exist you will need to register first. After login you will see 3 panels first is gamefield, controlls and chance to leave comment, second panel is Top 10 scores with chance to rate game and third panel which you can access comments. You can also change maps, restart the level or loggout if needed. 



## Commands

- Movement Commands
  - `W` - move up
  - `A` - move left
  - `S` - move down
  - `D` - move right

- Other Commands
  - `M` - return to main menu
  - `R` - restart map
  - `V` - view comments for the level
  - `T` - top 10 scores for the level
  - `K` - add a comment for the level
  - `H` - rate game

  
## Gameplay

Here you can see a quick video of gameplay:
- [https://youtu.be/4xulTef4cdo](https://youtu.be/4xulTef4cdo)
- [https://youtu.be/JUrTCPG73kE](https://youtu.be/JUrTCPG73kE) JPA/REST
- [https://youtu.be/Frv9U8pYZeI](https://youtu.be/Frv9U8pYZeI) WEB