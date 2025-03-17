package sk.tuke.kpi.kp.cube_roll.consoleui;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.CommentServiceJDBC;
import sk.tuke.gamestudio.service.RatingServiceJDBC;
import sk.tuke.gamestudio.service.ScoreServiceJDBC;
import sk.tuke.kpi.kp.cube_roll.core.Field;
import sk.tuke.kpi.kp.cube_roll.core.GameState;
import sk.tuke.kpi.kp.cube_roll.core.MapFactory;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\033[38;5;227m";
    private static final String VIOLET = "\033[38;5;13m";
    private static final String LIGHTPINK = "\033[38;5;217m";
    private static final String LIGHTGREEN ="\033[38;5;10m";
    private static final String PURPLE = "\033[38;5;57m";

    private Field field;
    private final Scanner scanner;
    private GameState gameState;
    private final int map;
    private int movesCount;
    private final String  playerName;

    public ConsoleUI( Field field, int map, String playerName ) {
        this.scanner = new Scanner(System.in);
        this.field = field;
        this.gameState = GameState.RUNNING;
        this.map = map;
        this.movesCount = 0;
        this.playerName = playerName;

    }

    public void play(){
        boolean again = true;
        while(again){
            while (gameState == GameState.RUNNING){
                show();
                showInput();
                handleInput();

                if(field.isFinishTile() && field.dice.isMarkedSideBottom()){
                    gameState = GameState.WON;
                }
                if(field.isVoidTile(field.getDiceRow(), field.getDiceCol())){
                    show();
                    field.loseLife();
                    try {
                        Thread.sleep(2000); // Pozastaví program na 2s
                    } catch (InterruptedException e) {
                        e.printStackTrace(); // Osetrenie vynimky bo inak to nechce ist ...
                    }
                    field.moveDice(field.getLastDirection());
                    if (field.getLivesCount() == 0){
                        gameState = GameState.LOST;
                    }
                }

            }

            if(gameState == GameState.WON) {
                handleScore();
            }
            if ((gameState == GameState.LOST && field.getLivesCount() == 0) || gameState == GameState.WON) { // restart iba ked WON alebo LOST s 0 ziv
                show();
                showLastStateMessage();
                again = askForRestart();
                if (again) {
                    switch (map) {
                        case 1:
                            field = MapFactory.createTrainMap();
                            break;
                        case 2:
                            field = MapFactory.createEasyMap();
                            break;
                        case 3:
                            field = MapFactory.createMediumMap();
                            break;
                        case 4:
                            field = MapFactory.createHardMap();
                            break;
                        default:
                            break;
                    }
                    this.gameState = GameState.RUNNING;
                    movesCount = 0;
                    field.setLivesCount(field.getLivesCount());
                }
            } else if (gameState == GameState.LOST && field.getLivesCount() > 0) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }
        }

        //System.out.println("--Koniec hry,ďakujem za hranie!--");
    }

    private void show() {
        System.out.println(VIOLET + "==================="+ RESET);
        System.out.println(LIGHTPINK +"Hracie pole:" +RESET);
        //vypis pola
        for (int i = 0; i < field.getRowCount(); i++) {
            for (int j = 0; j < field.getColumnCount(); j++) {
                if (i == field.getDiceRow() && j == field.getDiceCol() && !field.isVoidTile(i,j)) {
                    int topSide = field.dice.getTopSide();
                    int markedSide = field.dice.getMarkedSide();

                    if(markedSide == topSide) {
                        System.out.print( " " + RED + topSide + RESET);  // oznacena strana
                    }
                    else {
                        System.out.print( " "  + topSide );
                    }
                }
                else if (i == field.getFinishR() && j == field.getFinishC()) {
                    System.out.print(GREEN +"\uD83C\uDFC1" + RESET);  // Finish
                }
                else if(field.getDiceRow() == field.getFinishR() && field.getDiceCol() == field.getFinishC() && field.isFinishTile() && field.dice.isMarkedSideBottom()){
                    System.out.print(YELLOW +"\uD83C\uDFC1" + RESET); // Koniec hry
                }
                else if(field.isVoidTile(i,j) && i == field.getDiceRow() && j == field.getDiceCol()){
                    System.out.print("\uD83D\uDC7B");
                }
                else if(field.isVoidTile(i,j)){
                    System.out.print("⬛");
                }
                else if(field.isWallTile(i,j)){
                    System.out.print("\uD83E\uDDF1");
                }
                else {
                    System.out.print("⬜");  // Walkable dlaždica
                }
            }
            System.out.println();
            // rollovanie pola
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(VIOLET + "==== ŽIVOTY ==== ŤAH ====" + RESET);
        System.out.printf(LIGHTPINK +"---- %-3d/%-3d ---- %-2d----\n" + RESET,field.getLivesCount(),field.getMaxLives(),this.movesCount);
    }
    private void showInput(){
        // Vypis wsad vstupu hraca
        System.out.println(VIOLET + "==== OVLÁDANIE HRY ===========================================");
        System.out.println(LIGHTPINK + "W [hore],S [dole],A [doľava],D [doprava],M [menu],R [reštart]");
        System.out.println("T [top10],K [komentár],V [všetky komentáre],H [hodnotenie]");
        System.out.println( VIOLET + "==============================================================");
        System.out.println(LIGHTPINK + "Vizualizácia strán kocky (X je spodná strana)" + RESET);
        int frontSide = field.dice.getFrontSide();
        int leftSide = field.dice.getLeftSide();
        int rightSide = field.dice.getRightSide();
        int bottomSide = field.dice.getBottomSide();
        int backSide = field.dice.getBackSide();
        int markedSide = field.dice.getMarkedSide();
        if(markedSide == frontSide){ // ak je marknuta front
            System.out.println("  " + RED + frontSide + "                        ↑" + RESET);
        }
        else {
            System.out.println("  " + frontSide + "                        ↑");
        }
        // novy riadok
        if(markedSide == leftSide){ // ak je marknuta left
            System.out.println(RED + leftSide + RESET + " " + bottomSide + " " + rightSide + LIGHTPINK +" rozloženie stran:"+ RED +"  ←"+ RESET + " X →");
        }
        else if(field.dice.isMarkedSideBottom()){ // ak je marknuta bottom
            System.out.println(leftSide + " " + RED + bottomSide + RESET + " " + rightSide + LIGHTPINK +" rozloženie stran:"+ RESET + "  ← "+ RED + "X"+ RESET+ " →");
        }
        else if(markedSide == rightSide){ // ak je marknuta right
            System.out.println(leftSide + " " + bottomSide + " " + RED + rightSide + LIGHTPINK + " rozloženie stran:" + RESET + "  ← X " + RED +"→" + RESET);
        }
        else {
            System.out.println(leftSide + " " + bottomSide + " " + rightSide + LIGHTPINK + " rozloženie stran:" + RESET +"  ← X →");
        }
        // novy riadok
        if(markedSide == backSide){ // ak je marknuta back
            System.out.println("  " + RED + backSide +  "                        ↓" + RESET);
        }
        else {
            System.out.println("  " + backSide + "                        ↓");
        }
        System.out.println( VIOLET + "==============================================================" + RESET);
        //↑ ↓ → ←
    }

    private void handleInput() {
        while (true) {
            char input = scanner.next().toUpperCase().charAt(0);

            if (input == 'M') {
                System.out.println(GREEN + "--Vraciaš sa do menu--" + RESET);
                this.gameState = GameState.LOST;

                break;
            }
            else if (input == 'W' || input == 'S' || input == 'A' || input == 'D') {
                field.moveDice(input);
                this.movesCount++;
                break;
            } else if (input == 'K') {
                handleComment();
                break;
            }
            else if (input == 'T'){
                handleTopScores();
                break;
            }
            else if (input == 'H'){
                handleRating();
                break;
            }
            else if (input == 'V'){
                handleAllComments();
                break;
            } else if (input == 'R') {
                handleRestart();
                break;
            }
            else {
                System.out.println(LIGHTGREEN + "--Neplatný vstup! Skús znova.--" + RESET);
            }
        }
    }
    private void showLastStateMessage(){
        if(gameState == GameState.WON){
            System.out.println(YELLOW +"--🏆Gratulujem🏆--" + LIGHTPINK +" Vyhral si! Tvoje skóre je: "+ RED + getPlayerScore() + RESET + "--");
        }
        else if(gameState == GameState.LOST){
            System.out.println(RED + "--Prehral si!--" + RESET);
        }
    }
    private boolean askForRestart() {
        while(true) {
            System.out.println(LIGHTPINK + "--Chceš hrať znova? (" + GREEN + "Y" + RESET + LIGHTPINK + "/" + RED + "N" + RESET + "): ");
            char input = scanner.next().toUpperCase().charAt(0);
            if (input == 'Y') {
                return true;
            } else if (input == 'N') {
                return false;
            }
            else {
                System.out.println(LIGHTGREEN + "--Neplatný vstup! Skús znova.--" + RESET);
            }
        }
    }
    private int getPlayerScore() {
        int score;
        switch(map){
            case 1:
                score = 0;
                return score;
            case 2:
                if(movesCount < 11){
                    score  = 100;
                } else if (movesCount < 15) {
                    score  = 50;
                }
                else{
                    score  = 25;
                }
                return score + (field.getLivesCount() * 10);
            case 3:
                if(movesCount < 9){
                        score  = 100;
                }
                else if(movesCount < 12){
                    score  = 50;
                }
                else{
                    score  = 25;
                }
                return score + (field.getLivesCount() * 25);
            case 4:
                if(movesCount < 5){
                    score  = 100;
                }
                else if(movesCount < 15){
                    score  = 50;
                }
                else{
                    score  = 25;
                }
                return score + (field.getLivesCount() * 50);
            default: return -1;
        }
    }
    private void handleTopScores() {
        ScoreServiceJDBC scoreServiceJDBC = new ScoreServiceJDBC(); //instancia
        List<Score> topScores = scoreServiceJDBC.getTopScores("Cube Roll");
        System.out.println(VIOLET + "============================");
        System.out.println(RED + "-- MENO HRÁČA    |  SKÓRE --");
        System.out.println(VIOLET + "============================");
        int rank = 1;
        for (Score score : topScores) {
            System.out.printf(LIGHTPINK + "%-2d. %-12s| %-4d \n" + RESET,
                    rank++, score.getPlayer(), score.getPoints());
            if (rank > 10) break;
        }
        System.out.println(LIGHTPINK + "--Stlačte Enter pre pokračovanie--" + RESET);
        scanner.nextLine();
        scanner.nextLine();
    }

    private void handleScore() {
        ScoreServiceJDBC scoreServiceJDBC = new ScoreServiceJDBC();
        //System.out.println("--Ukladám skóre do databázy--");
        Score score = new Score("Cube Roll",playerName, getPlayerScore(),new Date());
        scoreServiceJDBC.addScore(score);
        //System.out.println("--Skóre uložené--");
    }

    private void handleComment(){
        System.out.println(PURPLE + "--Sem napíš svoj komentár k hre--");
        CommentServiceJDBC commentServiceJDBC = new CommentServiceJDBC();
        scanner.nextLine();
        String message = scanner.nextLine();
        Comment comment = new Comment("Cube Roll", playerName, message, new Date());
        commentServiceJDBC.addComment(comment);
        System.out.println(PURPLE + "--Ďakujem za pridanie komentára--" + RESET);
    }

    private void handleRating(){
        RatingServiceJDBC ratingServiceJDBC = new RatingServiceJDBC();
        System.out.println( VIOLET + "==============================================================" + RESET);
        System.out.println(LIGHTPINK + "--Ohodnoť hru od " + RED + "1*(zlá)"+ LIGHTPINK + " do " + GREEN +"5*(super)" + LIGHTPINK +"--");
        System.out.println("--Priemerné hodnotenie hry Cube Roll je "+ GREEN + ratingServiceJDBC.getAverageRating("Cube Roll") + YELLOW + "*" + RESET);
        scanner.nextLine();
        while (true) {
            int rated = scanner.nextInt();

            if (rated <= 5 && rated > 0) {
                Rating rating = new Rating("Cube Roll", playerName, rated, new Date());
                ratingServiceJDBC.setRating(rating);
                System.out.println(YELLOW + "--Ďakujem za hodnotenie hry Cube Roll--" + RESET);
                break;
            }
            else{
                System.out.println(LIGHTGREEN + "--Nesprávny vstup zadaj cislo od 1* po 5* --" + RESET);
            }
        }
    }
    private void handleAllComments(){
        System.out.println( VIOLET + "==============================================================");
        CommentServiceJDBC commentServiceJDBC = new CommentServiceJDBC();
        List<Comment> allComments = commentServiceJDBC.getComments("Cube Roll");
        for (Comment comment : allComments) {
            System.out.println(RED + comment.getPlayer() + VIOLET +" komentoval dňa " + comment.getCommentedOn() + "\n" + LIGHTPINK + comment.getComment());
        }
        System.out.println(VIOLET+ "=== Toto su všetky komentáre ===");
        System.out.println(LIGHTPINK + "--Stlačte Enter pre pokračovanie--" + RESET);
        scanner.nextLine();
        scanner.nextLine();
    }
    private void handleRestart() {
        switch (map) {
            case 1:
                field = MapFactory.createTrainMap();
                break;
            case 2        :
                field = MapFactory.createEasyMap();
                break;
            case 3:
                field = MapFactory.createMediumMap();
                break;
            case 4:
                field = MapFactory.createHardMap();
                break;
            default:
                break;
            }
            this.gameState = GameState.RUNNING;
            movesCount = 0;
            field.setLivesCount(field.getLivesCount());

    }

}
