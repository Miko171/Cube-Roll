package sk.tuke.gamestudio.game.cube_roll;

import sk.tuke.gamestudio.game.cube_roll.consoleui.ConsoleUI;
import sk.tuke.gamestudio.game.cube_roll.core.Field;
import sk.tuke.gamestudio.game.cube_roll.core.MapFactory;

import java.util.Scanner;

public class CubeRoll {
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\033[38;5;227m";
    private static final String VIOLET = "\033[38;5;13m";
    private static final String LIGHTPINK = "\033[38;5;217m";
    private static final String LIGHTGREEN ="\033[38;5;10m";
    //private static final String PURPLE = "\033[38;5;57m";
    private static final String BLUE = "\033[38;5;27m";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(VIOLET + "====================="+ RESET);
            System.out.println(RED + "   --- MENU ---" + RESET);
            System.out.println(VIOLET + "====================="+ RESET);
            System.out.println(LIGHTPINK + "1 - Začať novú hru -");
            System.out.println("2 - Zobraziť pravidlá -");
            System.out.println("3 - Ukončiť hru- ");
            System.out.println("Vyber možnosť 1-3" + RESET);
            System.out.println(VIOLET + "====================="+ RESET);
            int choice;
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                scanner.nextLine(); // Vyčisti vstup
                System.out.println("Neplatná voľba, skús znova.");
                continue;
            }
            switch (choice) {
                case 1:
                    startNewGame(scanner);
                    break;
                case 2:
                    showRules(scanner);
                    continue;
                case 3:
                    System.out.println(YELLOW + "Ďakujeme za hranie!" + RESET);
                    System.exit(0);
                    break;
                default:
                    System.out.println(LIGHTGREEN +"Neplatná voľba, skús znova." + RESET);
                    //continue;

                }
                //break;
        }

    }
    private static void startNewGame(Scanner scanner){
        System.out.println(VIOLET + "==================="+ RESET);
        System.out.println(LIGHTPINK + "Zadaj meno hráča:" + RESET);
        String playerName;
        while(true) {
            try {
                playerName = scanner.next();
                break;
            } catch (Exception e) {
                scanner.nextLine(); // Vyčisti vstup
                System.out.println(LIGHTGREEN +"Neplatná voľba, skús znova." + RESET);

            }
        }
        while (true){
            System.out.println(VIOLET + "==================="+ RESET);
            System.out.println(RED + " --- NOVÁ HRA ---");
            System.out.println(VIOLET + "==================="+ RESET);
            System.out.println(BLUE + "1 --TRAINING--");
            System.out.println(YELLOW + "2 --ĽAHKÁ MAPA (4 ŽIVOTY)--");
            System.out.println(GREEN + "3 --STREDNE TAŽKÁ MAPA (2 ŽIVOTY)--");
            System.out.println(RED + "4 --ŤAŽKÁ MAPA(1 ŽIVOT)-- ");
            System.out.println(LIGHTPINK + "Vyber možnosť 1-4");
            System.out.println(VIOLET + "==================="+ RESET);

            int choice;
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                scanner.nextLine(); // Vyčisti vstup
                System.out.println(LIGHTGREEN +"Neplatná voľba, skús znova." + RESET);
                continue;
            }

            Field field;
            switch (choice) {
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
                    System.out.println(LIGHTGREEN +"Neplatná voľba, skús znova." + RESET);
                    continue;
            }

            //Field field = new Field(5, 5, 1, 1, 3, 3);
            //ConsoleUI ui = new ConsoleUI(field, choice, playerName);
            //ui.play();
            break;
        }

    }
    private static void showRules(Scanner scanner) {
        System.out.println(RED + "\n=== PRAVIDLÁ HRY ===");
        System.out.println(VIOLET + "==================="+ RESET);
        System.out.println(LIGHTPINK + "🎲 Cieľ hry: Dostaň kocku na cieľovú dlaždicu so správnou stranou dole a s čo najmenším počtom ťahov.🎲");
        System.out.println("❤️ Máš obmedzený počet životov. Na konci hry sa každý zostávajúci život premení na skóre.❤️");
        System.out.println("🏆 Skóre sa vypočíta na základe:");
        System.out.println("   - Počtu ťahov (čím menej tým viac skóre dostaneš).");
        System.out.println("   - Počtu zostávajúcich životov (1 život = +10/+25/+50 bodov podla mapy).");
        System.out.println(VIOLET + "==================="+ RESET);
        System.out.println(RED + "--- Typy dlaždíc ---");
        System.out.println(LIGHTPINK + "🧱  - Stena: Nedá sa ňou prejsť.");
        System.out.println(LIGHTGREEN + "⬛  - Diera: Ak do nej spadneš, stratíš život.");
        System.out.println(YELLOW + "🏁  - Cieľová dlaždica: Sem musíš dostať kocku.");
        System.out.println(LIGHTPINK + "\n-- Chceš sa vrátiť do menu? (" + GREEN + "Y" + LIGHTPINK + " / " + RED + "N" + LIGHTPINK + "): " + RESET);
        System.out.println(VIOLET + "==================="+ RESET);
        while(true) {
            char input = scanner.next().toUpperCase().charAt(0);
            if (input == 'N') {
                System.exit(0);
            } else if (input == 'Y') {
                break;
            } else {
                System.out.println(LIGHTGREEN+ "Neplatná voľba, skús znova." + RESET);
            }
        }

    }
}