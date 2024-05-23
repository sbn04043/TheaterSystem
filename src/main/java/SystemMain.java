import controller.*;
import viewer.*;

import java.util.Scanner;

public class SystemMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        UserController userController = new UserController();
        UserViewer userViewer = new UserViewer();

        MovieController movieController = new MovieController();
        MovieViewer movieViewer = new MovieViewer();

        ScoreController scoreController = new ScoreController();
        ScoreViewer scoreViewer = new ScoreViewer();

        TheaterController theaterController = new TheaterController();
        TheaterViewer theaterViewer = new TheaterViewer();

        InfoController infoController = new InfoController();
        InfoViewer infoViewer = new InfoViewer();

        userViewer.setScanner(scanner);
        userViewer.setUserController(userController);
        userViewer.setMovieViewer(movieViewer);
        userViewer.setScoreViewer(scoreViewer);
        userViewer.setTheaterViewer(theaterViewer);
        userViewer.setInfoViewer(infoViewer);
        userViewer.setScoreController(scoreController);

        movieViewer.setScanner(scanner);
        movieViewer.setMovieController(movieController);
        movieViewer.setScoreController(scoreController);
        movieViewer.setScoreViewer(scoreViewer);
        movieViewer.setInfoController(infoController);

        scoreViewer.setScoreController(scoreController);
        scoreViewer.setScanner(scanner);
        scoreViewer.setUserController(userController);

        scoreController.setUserController(userController);

        theaterViewer.setTheaterController(theaterController);
        theaterViewer.setInfoController(infoController);
        theaterViewer.setInfoViewer(infoViewer);
        theaterViewer.setScanner(scanner);


        infoViewer.setScanner(scanner);
        infoViewer.setInfoController(infoController);
        infoViewer.setMovieController(movieController);
        infoViewer.setTheaterController(theaterController);

        userViewer.showList();
    }
}
