package viewer;

import controller.InfoController;
import controller.MovieController;
import controller.ScoreController;
import lombok.Setter;
import model.MovieDTO;
import model.UserDTO;
import util.ScannerUtil;

import java.util.ArrayList;
import java.util.Scanner;

@Setter
public class MovieViewer {
    private UserDTO logIn;
    private Scanner scanner;
    private MovieController movieController;
    private ScoreController scoreController;
    private ScoreViewer scoreViewer;
    private InfoController infoController;

    public void showAdminList() {
        String message = "1. 영화 추가 2. 영화 목록 3. 뒤로가기";
        int choice = -1;

        while (choice != 3) {
            choice = ScannerUtil.nextInt(scanner, message, 1, 3);

            if (choice == 1) {
                register();
                showList();
            } else if (choice == 2) {
                showList();
            }
        }
    }

    public void showList() {
        String message;
        int choice;
        ArrayList<MovieDTO> movieList = movieController.selectAll();

        for (MovieDTO m : movieList) {
            System.out.printf("%d. %s - 평균별점: %f\n", m.getId(), m.getTitle(), scoreController.averageScore(m.getId()));
        }

        message = "상세보기 할 영화의 번호를 누르세요(뒤로가기 0)";
        choice = ScannerUtil.nextInt(scanner, message);

        while (!movieController.isValidateId(choice)) {
            message = "번호를 다시 눌러주세요(뒤로가기 0)";
            choice = ScannerUtil.nextInt(scanner, message);
        }

        if (choice != 0) {
            showOne(choice);
        }
    }

    public void showOne(int id) {
        MovieDTO m = movieController.selectOne(id);
        String ableAge = movieController.getAge(m.getGrade());
        System.out.printf("%d. %s - %s\n", m.getId(), m.getTitle(), ableAge);
        System.out.printf("%s\n", m.getContent());

        String message;
        int choice;

        //관리자일 때 실행
        if (logIn.getId() == 1) {
            message = "1. 영화 수정 2. 영화 제거 3. 평점 보기 4. 뒤로 가기";
            choice = ScannerUtil.nextInt(scanner, message, 1, 4);

            if (choice == 1) {
                update(id);
                showOne(id);
            } else if (choice == 2) {
                delete(id);
                showList();
            } else if (choice == 3) {
                scoreViewer.setMovie(m);
                scoreViewer.showScoreByMovieId(id);
                showList();
            } else if (choice == 4) {
                showList();
            }
        } else {
            message = "평점 페이지로 가시겠습니까?(Y)";
            String answer = ScannerUtil.nextLine(scanner, message);
            if (!answer.equalsIgnoreCase("y")) {
                showList();
            } else {
                scoreViewer.setMovie(m);
                scoreViewer.showScoreByMovieId(id);
                showList();
            }
        }
    }

    public void register() {
        MovieDTO m = new MovieDTO();
        String message = "영화 제목을 입력해주세요";
        m.setTitle(ScannerUtil.nextLine(scanner, message));

        message = "영화 등급을 입력해주세요(1~5)";
        m.setGrade(ScannerUtil.nextInt(scanner, message, 1, 5));

        message = "영화 내용을 입력해주세요";
        m.setContent(ScannerUtil.nextLine(scanner, message));

        movieController.insert(m);
    }

    public void update(int id) {
        MovieDTO m = movieController.selectOne(id);
        String message = "변경할 정보를 고르세요 1. 제목 2. 등급 3. 내용 4. 뒤로가기";
        int choice = ScannerUtil.nextInt(scanner, message, 1, 4);

        if (choice == 1) {
            message = "변경할 제목을 입력하세요";
            m.setTitle(ScannerUtil.nextLine(scanner, message));
        } else if (choice == 2) {
            message = "변경할 등급을 입력하세요(1~5)";
            m.setGrade(ScannerUtil.nextInt(scanner, message, 1, 5));
        } else if (choice == 3) {
            message = "변경할 내용을 입력하세요";
            m.setContent(ScannerUtil.nextLine(scanner, message));
        } else {
            showOne(id);
        }
    }

    public void delete(int id) {
        String message = "정말로 지우겠습니까(Y/N)";
        String answer = ScannerUtil.nextLine(scanner, message);

        while (!(answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("n"))) {
            message = "잘못 입력하셨습니다. 정말로 지우겠습니까?(Y/N)";
            answer = ScannerUtil.nextLine(scanner, message);
        }

        if (answer.equalsIgnoreCase("y")) {
            movieController.remove(id);
            infoController.removeAllByMovieID(id);
            scoreController.removeAllByMovieId(id);
        }
    }
}
