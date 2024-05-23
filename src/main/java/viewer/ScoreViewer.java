package viewer;

import controller.ScoreController;
import controller.UserController;
import lombok.Setter;
import model.MovieDTO;
import model.ScoreDTO;
import model.UserDTO;
import util.ScannerUtil;

import java.util.ArrayList;
import java.util.Scanner;

@Setter
public class ScoreViewer {
    private ScoreController scoreController;
    private UserController userController;
    private UserDTO logIn;
    private MovieDTO movie;
    private Scanner scanner;

    public void showScoreByMovieId(int movieId) {
        ArrayList<ScoreDTO> scoreList = scoreController.selectAllByMovieId(movieId);
        String message = "1.전체 평점 2. 평론가 평점 3. 일반인 평점 4. 평점 남기기 5. 뒤로가기";
        int choice = ScannerUtil.nextInt(scanner, message, 1, 5);

        if (choice == 1) {
            showAll(scoreList);
        } else if (choice == 2) {
            showCritic(scoreList);
        } else if (choice == 3) {
            showCommon(scoreList);
        } else if (choice == 4) {
            leaveComment(scoreList);
        } else if (choice == 5) {
        }


    }

    public void showAll(ArrayList<ScoreDTO> scoreList) {

        System.out.printf("평균 평점: %f\n", scoreController.averageScore(scoreList));
        for (int i = 0; i < scoreList.size(); i++) {
            ScoreDTO s = scoreList.get(i);
            //s의 userId를 통해 UserDTO의 닉네임을 가져옴
            String nickName = userController.selectOne(s.getUserId()).getNickName();

            System.out.printf("%d. %s: %d점\n\n", i + 1, nickName, s.getScore());
        }
    }

    public void showCritic(ArrayList<ScoreDTO> scoreList) {
        ArrayList<ScoreDTO> criticList = scoreController.getCriticList(scoreList);
        System.out.printf("평론가 평균 평점: %f\n", scoreController.averageScore(criticList));
        for (int i = 0; i < criticList.size(); i++) {
            ScoreDTO s = scoreList.get(i);
            String nickName = userController.selectOne(s.getUserId()).getNickName();

            System.out.printf("%d. %s: %s - (%d)\n", i + 1, nickName, s.getContent(), s.getScore());
        }
    }

    public void showCommon(ArrayList<ScoreDTO> scoreList) {
        ArrayList<ScoreDTO> commonList = scoreController.getCommonList(scoreList);
        System.out.printf("일반인 평균 평점: %f\n", scoreController.averageScore(commonList));
        for (int i = 0; i < commonList.size(); i++) {
            ScoreDTO s = scoreList.get(i);
            String nickName = userController.selectOne(s.getUserId()).getNickName();

            System.out.printf("%d. %s: %d점\n", i + 1, nickName, s.getScore());
        }
    }

    public void leaveComment(ArrayList<ScoreDTO> scoreList) {
        String message;
        String answer = "";
        ScoreDTO score;
        boolean isCommented = false;
        for (ScoreDTO s : scoreList) {
            if (s.getUserId() == logIn.getId()) {
                message = "이미 별점을 등록한 영화입니다 다시 입력하시겠습니까?(Y)";
                answer = ScannerUtil.nextLine(scanner, message);
                isCommented = true;
                break;
            }
        }

        if (isCommented) {
            if (answer.equalsIgnoreCase("y")) {
                score = scoreController.selectByMovieIdUserId(logIn.getId(), movie.getId());
            } else {
                return;
            }
        } else {
            score = new ScoreDTO();
        }
        message = "평점을 입력해주세요(1~5)";
        score.setScore(ScannerUtil.nextInt(scanner, message, 1, 5));

        score.setUserId(logIn.getId());
        score.setMovieId(movie.getId());

        if (logIn.getGrade() == 2) {
            message = "평을 남겨주세요";
            score.setContent(ScannerUtil.nextLine(scanner, message));
        }

        if (!isCommented)
            scoreController.insert(score);
    }
}
