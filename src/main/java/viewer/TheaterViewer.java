package viewer;

import controller.InfoController;
import controller.TheaterController;
import lombok.Setter;
import model.TheaterDTO;
import model.UserDTO;
import util.ScannerUtil;

import java.util.ArrayList;
import java.util.Scanner;

@Setter
public class TheaterViewer {
    private TheaterController theaterController;
    private InfoController infoController;
    private InfoViewer infoViewer;

    private UserDTO logIn;
    private Scanner scanner;

    public void showAdminList() {
        String message;
        int choice = -1;

        if (logIn.getId() == 1) {
            message = "1. 영화관 추가 2. 영화관 상세보기 3. 뒤로 가기";
            choice = ScannerUtil.nextInt(scanner, message, 1, 3);

            if (choice == 1) {
                register();
                showAdminList();
            } else if (choice == 2) {
                showList();
            } else if (choice == 3) {

            }
        }
    }

    public void showList() {
        String message;
        int choice = -1;

        ArrayList<TheaterDTO> theaterList = theaterController.selectAll();
        for (TheaterDTO t : theaterList) {
            System.out.printf("%d. %s\n", t.getId(), t.getName());
        }

        message = "상세보기 할 영화관을 고르세요(뒤로가기 0)";
        choice = ScannerUtil.nextInt(scanner, message);

        while (!theaterController.isValidateId(choice)) {
            message = "유효하지 않은 번호입니다. 상세보기 할 영화관을 고르세요";
            choice = ScannerUtil.nextInt(scanner, message);
        }

        if (choice != 0) {
            showOne(choice);
        }
    }

    public void showOne(int id) {
        TheaterDTO t = theaterController.selectOne(id);
        System.out.printf("%d. %s 위치: %s\n", t.getId(), t.getName(), t.getLocation());
        System.out.println(t.getNumber());

        String message;
        int choice = -1;
        String answer = "";

        if (logIn.getGrade() == 1) {
            message = "1. 수정 2. 제거 3. 상영목록 보기 4. 뒤로가기";
            choice = ScannerUtil.nextInt(scanner, message, 1, 4);
            if (choice == 1) {
                update(id);
                showOne(id);
            } else if (choice == 2) {
                delete(id);
                showList();
            } else if (choice == 3) {
                //해당 영화관 번호의 상영 목록 뽑기
                infoViewer.setTheater(t);
                infoViewer.showList();
                showList();
            } else if (choice == 4) {
                showList();
            }
        } else {
            message = "해당 영화관의 상영 목록을 보시겠습니까? (Y/N)";
            answer = ScannerUtil.nextLine(scanner, message);
            while (!(answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("N"))) {
                message = "다른 것을 입력하셨습니다. (Y/N)";
                answer = ScannerUtil.nextLine(scanner, message);
            }

            if (answer.equalsIgnoreCase("Y")) {
                //해당 영화관 번호의 상영 목록 뽑기
                infoViewer.setTheater(t);
                infoViewer.showList();
                showList();
            } else {
                showList();
            }
        }

    }

    public void register() {
        ArrayList<TheaterDTO> theaterList = theaterController.selectAll();
        TheaterDTO theaterDTO = new TheaterDTO();
        String message;

        message = "영화관 이름을 입력하세요";
        theaterDTO.setName(ScannerUtil.nextLine(scanner, message));

        //추가할 영화관과 기존의 영화관 이름을 비교해 중복이면 추가 막기
        while (!theaterController.isValidateName(theaterDTO.getName())) {
            message = "중복된 이름입니다 영화관 이름을 다시 입력하세요";
            theaterDTO.setName(ScannerUtil.nextLine(scanner, message));
        }

        message = "영화관의 지역을 입력하세요";
        theaterDTO.setLocation(ScannerUtil.nextLine(scanner, message));

        message = "영화관의 번호를 입력하세요";
        theaterDTO.setNumber(ScannerUtil.nextLine(scanner, message));

        theaterController.insert(theaterDTO);
    }

    public void update(int id) {
        TheaterDTO theaterDTO = theaterController.selectOne(id);
        String message = "변경하실 이름을 입력하세요";
        theaterDTO.setName(ScannerUtil.nextLine(scanner, message));
        if (!theaterController.isValidateName(theaterDTO.getName())) {
            message = "중복된 이름입니다. 변경하실 이름을 입력하세요";
            theaterDTO.setName(ScannerUtil.nextLine(scanner, message));
        }

        message = "변경하실 지역을 입력하세요";
        theaterDTO.setLocation(ScannerUtil.nextLine(scanner, message));

        message = "변경하실 번호를 입력하세요";
        theaterDTO.setNumber(ScannerUtil.nextLine(scanner, message));
    }

    public void delete(int id) {
        String message = "정말로 삭제하시겠습니까?(Y)";
        String answer = ScannerUtil.nextLine(scanner, message);

        if (answer.equalsIgnoreCase("y")) {
            theaterController.remove(id);
            infoController.removeAllByTheaterId(id);
        }

    }
}
