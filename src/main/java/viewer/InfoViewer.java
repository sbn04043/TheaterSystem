package viewer;

import controller.InfoController;
import controller.MovieController;
import controller.TheaterController;
import lombok.Setter;
import model.InfoDTO;
import model.MovieDTO;
import model.TheaterDTO;
import model.UserDTO;
import util.ScannerUtil;

import java.util.ArrayList;
import java.util.Scanner;

@Setter
public class InfoViewer {
    private UserDTO logIn;
    private TheaterDTO theater;
    private Scanner scanner;

    private InfoController infoController;
    private MovieController movieController;
    private TheaterController theaterController;

    //영화관
    public void showList() {
        ArrayList<InfoDTO> infoList = infoController.selectAllByTheaterId(theater.getId());
        System.out.println("  " + theater.getName());
        if (infoList.isEmpty()) {
            System.out.println("아직 상영정보가 없습니다");
        }

        for (InfoDTO i : infoList) {
            //movieId로 영화 제목을 가져온다
            String movieTitle = movieController.selectOne((i.getMovieId())).getTitle();
            System.out.printf("%d. %s 상영시간: %s\n", i.getId(), movieTitle, i.getTime());
        }

        if (logIn.getGrade() == 1) {
            String message = "1. 추가 2. 수정 3. 제거 4. 뒤로가기";
            int choice = ScannerUtil.nextInt(scanner, message, 1, 4);

            if (choice == 1) {
                register();
                showList();
            } else if (choice == 2) {
                message = "수정할 번호를 입력해주세요";
                choice = ScannerUtil.nextInt(scanner, message);

                while (!infoList.contains(new InfoDTO(choice))) {
                    if (choice == 0) return;
                    choice = ScannerUtil.nextInt(scanner, message);
                }

                update(choice);
                showList();
            } else if (choice == 3) {
                message = "제거할 번호를 입력해주세요";
                choice = ScannerUtil.nextInt(scanner, message);

                while (!infoList.contains(new InfoDTO(choice))) {
                    if (choice == 0) return;
                    choice = ScannerUtil.nextInt(scanner, message);
                }

                delete(choice);
                showList();
            }
        }
    }

    public void register() {
        InfoDTO infoDTO = new InfoDTO();
        ArrayList<MovieDTO> movieList = movieController.selectAll();
        for (MovieDTO m : movieList) {
            System.out.printf("%d. %s\n", m.getId(), m.getTitle());
        }
        String message = "추가할 영화 번호를 입력하세요";
        int choice = ScannerUtil.nextInt(scanner, message);

        while (!movieController.isValidateId(choice) || choice == 0) {
            message = "번호를 잘못 입력하셨습니다 다시 입력하세요";
            choice = ScannerUtil.nextInt(scanner, message);
        }
        infoDTO.setMovieId(choice);
        infoDTO.setTheaterId(theater.getId());

        message = "시간을 입력하세요";
        infoDTO.setTime(ScannerUtil.nextLine(scanner, message));

        infoController.insert(infoDTO);
    }

    public void update(int id) {
        InfoDTO infoDTO = infoController.selectOne(id);
        ArrayList<MovieDTO> movieList = movieController.selectAll();
        for (MovieDTO m : movieList) {
            System.out.printf("%d. %s\n", m.getId(), m.getTitle());
        }

        String message = "수정할 영화 번호를 입력하세요";
        int choice = ScannerUtil.nextInt(scanner, message);

        while (!movieController.isValidateId(choice) || choice == 0) {
            message = "번호를 잘못 입력하셨습니다 다시 입력하세요";
            choice = ScannerUtil.nextInt(scanner, message);
        }
        infoDTO.setMovieId(choice);

        message = "수정할 시간을 입력하세요";
        infoDTO.setTime(ScannerUtil.nextLine(scanner, message));
    }

    public void delete(int id) {
        String message = "정말로 지우시겠습니까?(Y)";
        String answer = ScannerUtil.nextLine(scanner, message);

        if (!answer.equalsIgnoreCase("y")) {
            showList();
        } else {
            infoController.remove(id);
            showList();
        }
    }
}
