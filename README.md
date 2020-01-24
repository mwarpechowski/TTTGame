# Tic-tac-toe

Yet another Tic-tac-toe game.

Original purpose of this project was to practice coding REST API with Spring.
Then the idea of extending it with web layer came to learn something new. 
Finally I decided to publish it because someone may benefit.

## Requirements

* java 8 (tested on 1.8.0_231)
* tomcat 9 (tested on 9.0.30)
* maven 3.5.2 or newer

## Configuration

To be done

## Installation

```
mvn clean install tomcat7:deploy
```

## TODO

### Phase 1
* Add dynamic board size support
   * Add requested board margin and force view refresh
* Marking move effects (predicting)
* Fix logo and status check mark
### Phase 2
* Add swagger for REST API
* Add running on Docker
* Add MySQL/PostgreSQL backend
* Add NoSQL backend (Mongo?, CouchDB?)
* Add start/stop scripts
### Phase 3
* Add network play capabilities:
  * Players with profiles (unique id, name, stats)
    * Login in wiht OAuth?
    * Profile confirmation with email
  * 'Play again' option with score counting for the game
  * Score list for a game
  * High scores view - comparing all players 
  * Creating game by single player and waiting for other to join
### Phase 4
* Metrics collecting and presenting (ELK?, Graphana?, Prometheus?)

## License

[MIT](LICENSE.md)

## Thanks to

Many thanks to authors of following web pages which provided tons of information and explanations that I used working on this project.

* [Baeldung](https://www.baeldung.com)
* [JournalDev](https://www.journaldev.com)
* [mkyong](https://www.mkyong.com)

Special thanks to Will Hartung for [this comment](https://stackoverflow.com/questions/1296235/jsp-tricks-to-make-templating-easier)

Also thanks to [HiClipart](https://www.hiclipart.com) where I found most of images used in this project