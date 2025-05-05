package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.cube_roll.core.Field;
import sk.tuke.gamestudio.game.cube_roll.core.GameState;
import sk.tuke.gamestudio.game.cube_roll.core.MapFactory;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.Date;


@Controller
@RequestMapping("/CubeRoll")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class CubeRollController {

    private Field field = MapFactory.createTrainMap();
    private GameState gameState = GameState.RUNNING;
    private int map = 1;
    private int movesCount;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserController userController;

    @RequestMapping
    public String cubeRoll(@RequestParam(required = false) String direction, Model model) {
        // Check if user is logged in
        if (!userController.isLoggedIn()) {
            return "redirect:/login";
        }

        String playerName = userController.getLoggedUser().getUsername();
        model.addAttribute("playerName", playerName);
        model.addAttribute("topScores", scoreService.getTopScores("Cube Roll"));
        model.addAttribute("comments", commentService.getComments("Cube Roll"));
        model.addAttribute("avgRating", ratingService.getAverageRating("Cube Roll"));
        model.addAttribute("getRating", ratingService.getRating("Cube Roll", playerName));

        if (gameState.equals(GameState.RUNNING)) {
            if (direction != null) {
                switch (direction) {
                    case "W" -> field.moveDice('W');
                    case "S" -> field.moveDice('S');
                    case "A" -> field.moveDice('A');
                    case "D" -> field.moveDice('D');
                }
                movesCount++;
            }
        }

        if (field.isFinishTile() && field.dice.isMarkedSideBottom()) {
            gameState = GameState.WON;
        }

        if (field.isVoidTile(field.getDiceRow(), field.getDiceCol())) {
            field.loseLifeHtml();
            field.moveDice(field.getLastDirection());
            if (field.getLivesCount() == 0) {
                gameState = GameState.LOST;
            }
        }

        if (gameState == GameState.WON) {
            Score score = new Score("Cube Roll", playerName, getPlayerScore(), new Date());
            scoreService.addScore(score);
        }

        return "CubeRoll";
    }

    public String getLivesCount() {
        return Integer.toString(field.getLivesCount());
    }


    public String getHtmlField() {
        StringBuilder sb = new StringBuilder();
        sb.append("<table class='gamefield'>\n");

        for (int i = 0; i < field.getRowCount(); i++) {
            sb.append("<tr>\n");
            for (int j = 0; j < field.getColumnCount(); j++) {
                if (i == field.getDiceRow() && j == field.getDiceCol() && !field.isVoidTile(i, j)) {
                    int topSide = field.dice.getTopSide();
                    int markedSide = field.dice.getMarkedSide();

                    if (markedSide == topSide) {
                        sb.append("<td>\n");

                        switch (topSide) {
                            case 1:
                                sb.append("<img src = '/images/dice_red_1.png'>");
                                break;
                            case 2:
                                sb.append("<img src = '/images/dice_red_2.png'>");
                                break;
                            case 3:
                                sb.append("<img src = '/images/dice_red_3.png'>");
                                break;
                            case 4:
                                sb.append("<img src = '/images/dice_red_4.png'>");
                                break;
                            case 5:
                                sb.append("<img src = '/images/dice_red_5.png'>");
                                break;
                            case 6:
                                sb.append("<img src = '/images/dice_red_6.png'>");
                                break;
                        }

                        sb.append("<td>\n");
                    } else {
                        sb.append("<td>\n");
                        switch (topSide) {
                            case 1:
                                sb.append("<img src = '/images/dice_b_1.png'>");
                                break;
                            case 2:
                                sb.append("<img src = '/images/dice_b_2.png'>");
                                break;
                            case 3:
                                sb.append("<img src = '/images/dice_b_3.png'>");
                                break;
                            case 4:
                                sb.append("<img src = '/images/dice_b_4.png'>");
                                break;
                            case 5:
                                sb.append("<img src = '/images/dice_b_5.png'>");
                                break;
                            case 6:
                                sb.append("<img src = '/images/dice_b_6.png'>");
                                break;
                        }
                        sb.append("<td>\n");
                    }
                } else if (i == field.getFinishR() && j == field.getFinishC()) {
                    sb.append("<td>\n");  // Finish
                    sb.append("<img src = '/images/finish.png'>");
                    sb.append("<td>\n");
                } else if (field.isVoidTile(i, j)) {
                    sb.append("<td>\n");  // Void
                    sb.append("<img src = '/images/void_tile.png'>");
                    sb.append("<td>\n");
                } else if (field.isWallTile(i, j)) {
                    sb.append("<td>\n");  // wall
                    sb.append("<img src = '/images/wall_tile.png'>");
                    sb.append("<td>\n");
                } else {
                    sb.append("<td>\n");
                    sb.append("<img src = '/images/basic_tile.png'>");
                    sb.append("<td>\n");  // Walkable dlaÅ¾dica
                }
            }
            sb.append("</tr>\n");
        }

        sb.append("</table>\n");
        return sb.toString();
    }

    public String getDiceSideshtml() {
        StringBuilder sb = new StringBuilder();
        int front = field.dice.getFrontSide();
        int left = field.dice.getLeftSide();
        int right = field.dice.getRightSide();
        int bottom = field.dice.getBottomSide();
        int back = field.dice.getBackSide();
        int marked = field.dice.getMarkedSide();

        sb.append("<table class='dicesides'>\n");

        // First row (only back side in center)
        sb.append("<tr><td></td>");
        sb.append("<td>").append(getDiceImage(front, marked)).append("</td>");
        sb.append("<td></td></tr>\n");

        // Second row (left, front, right)
        sb.append("<tr>");
        sb.append("<td>").append(getDiceImage(left, marked)).append("</td>");
        sb.append("<td>").append(getDiceImage(bottom, marked)).append("</td>");
        sb.append("<td>").append(getDiceImage(right, marked)).append("</td>");
        sb.append("</tr>\n");

        // Third row (only bottom side in center)
        sb.append("<tr><td></td>");
        sb.append("<td>").append(getDiceImage(back, marked)).append("</td>");
        sb.append("<td></td></tr>\n");

        sb.append("</table>\n");
        return sb.toString();
    }

    private String getDiceImage(int side, int markedSide) {
        String color = (side == markedSide) ? "red" : "b";
        return "<img src='/images/dice_" + color + "_" + side + ".png'>";
    }

    public String getControlsHtml() {
        StringBuilder sb = new StringBuilder();
        sb.append("<form action='/CubeRoll' method='post'>\n");
        sb.append("<table class='dicesides'>\n");

        // prvy riadok
        sb.append("<tr><td></td>");
        sb.append("<td>")
                .append(getArrowButton("W", "/images/arrow_up.png", "Up"))
                .append("</td><td></td></tr>\n");

        // druhy riadok
        sb.append("<tr>");
        sb.append("<td>")
                .append(getArrowButton("A", "/images/arrow_left.png", "Left"))
                .append("</td>");
        sb.append("<td></td>");
        sb.append("<td>")
                .append(getArrowButton("D", "/images/arrow_right.png", "Right"))
                .append("</td>");
        sb.append("</tr>\n");

        // treti riadok
        sb.append("<tr><td></td>");
        sb.append("<td>")
                .append(getArrowButton("S", "/images/arrow_down.png", "Down"))
                .append("</td><td></td></tr>\n");

        sb.append("</table>\n");
        sb.append("</form>\n");
        return sb.toString();
    }

    private String getArrowButton(String direction, String imagePath, String alt) {
        return "<button type='submit' name='direction' value='" + direction + "'>"
                + "<img src='" + imagePath + "' alt='" + alt + "'></button>";
    }

    @RequestMapping("/train")
    public String train() {
        // Check if user is logged in
        if (!userController.isLoggedIn()) {
            return "redirect:/login";
        }

        map = 1;
        field = MapFactory.createTrainMap();
        gameState = GameState.RUNNING;
        movesCount = 0;
        return "CubeRoll";
    }

    @RequestMapping("/easy")
    public String easy() {
        // Check ci je prihlaseny
        if (!userController.isLoggedIn()) {
            return "redirect:/login";
        }

        map = 2;
        field = MapFactory.createEasyMap();
        gameState = GameState.RUNNING;
        movesCount = 0;
        return "CubeRoll";
    }

    @RequestMapping("/medium")
    public String medium() {
        // Check ci je prihlaseny
        if (!userController.isLoggedIn()) {
            return "redirect:/login";
        }

        map = 3;
        field = MapFactory.createMediumMap();
        gameState = GameState.RUNNING;
        movesCount = 0;
        return "CubeRoll";
    }

    @RequestMapping("/hard")
    public String hard() {
        // Check ci je prihlaseny
        if (!userController.isLoggedIn()) {
            return "redirect:/login";
        }

        map = 4;
        field = MapFactory.createHardMap();
        gameState = GameState.RUNNING;
        movesCount = 0;
        return "CubeRoll";
    }

    @RequestMapping("/restart")
    public String restart() {
        // Check ci je prihlaseny
        if (!userController.isLoggedIn()) {
            return "redirect:/login";
        }

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
        gameState = GameState.RUNNING;
        movesCount = 0;

        return "CubeRoll";
    }

    @RequestMapping("/gamestate")
    public String getGameState() {
        return gameState.toString();
    }

    @PostMapping("/addComment")
    public String addComment(@RequestParam String message) {
        // Check ci je prihlaseny
        if (!userController.isLoggedIn()) {
            return "redirect:/login";
        }

        String playerName = userController.getLoggedUser().getUsername();
        Comment comment = new Comment("Cube Roll", playerName, message, new Date());
        commentService.addComment(comment);

        return "redirect:/CubeRoll";
    }

    @PostMapping("/addRating")
    public String addRating(@RequestParam int rating) {
        // Check ci je prihlaseny
        if (!userController.isLoggedIn()) {
            return "redirect:/login";
        }

        String playerName = userController.getLoggedUser().getUsername();
        if (rating >= 1 && rating <= 5) {
            ratingService.setRating(new sk.tuke.gamestudio.entity.Rating("Cube Roll", playerName, rating, new Date()));
        }

        return "redirect:/CubeRoll";
    }

    private int getPlayerScore() {
        int score;
        switch (map) {
            case 1:
                score = 0;
                return score;
            case 2:
                if (movesCount < 11) {
                    score = 100;
                } else if (movesCount < 15) {
                    score = 50;
                } else {
                    score = 25;
                }
                return score + (field.getLivesCount() * 10);
            case 3:
                if (movesCount < 9) {
                    score = 100;
                } else if (movesCount < 12) {
                    score = 50;
                } else {
                    score = 25;
                }
                return score + (field.getLivesCount() * 25);
            case 4:
                if (movesCount < 5) {
                    score = 100;
                } else if (movesCount < 15) {
                    score = 50;
                } else {
                    score = 25;
                }
                return score + (field.getLivesCount() * 50);
            default:
                return -1;
        }
    }
}