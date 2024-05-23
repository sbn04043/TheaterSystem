package viewer;

import controller.ScoreController;
import controller.UserController;
import lombok.Setter;
import model.UserDTO;
import util.ScannerUtil;

import java.util.ArrayList;
import java.util.Scanner;

@Setter
public class UserViewer {
    Scanner scanner;
    UserController userController;
    MovieViewer movieViewer;
    ScoreViewer scoreViewer;
    TheaterViewer theaterViewer;
    InfoViewer infoViewer;
    ScoreController scoreController;
    UserDTO logIn = null;

    public void showList() {
        String message = "1. 로그인 2. 회원가입 3. 프로그램 종료";

        while (true) {
            int choice = ScannerUtil.nextInt(scanner, message, 1, 3);

            if (choice == 1) {
                //로그인
                logIn();
                if (logIn != null) {
                    //로그인 돼 있으면 메뉴 실행
                    movieViewer.setLogIn(logIn);
                    scoreViewer.setLogIn(logIn);
                    theaterViewer.setLogIn(logIn);
                    infoViewer.setLogIn(logIn);
                    showMenu();
                }
            } else if (choice == 2) {
                //회원가입
                register();
            } else if (choice == 3) {
                System.out.println("프로그램을 종료합니다");
                //System.exit(0);
                break;
            }

            if (choice == 3) break;
        }
    }

    public void logIn() {
        String message = "아이디를 입력하세요";
        String userName = ScannerUtil.nextLine(scanner, message);

        message = "비밀번호를 입력하세요";
        String password = ScannerUtil.nextLine(scanner, message);

        logIn = userController.selectOne(userName, password);

        if (logIn == null) {
            System.out.println("유효하지 않은 아이디입니다");
        }

    }

    public void register() {
        String message = "아이디를 입력하세요";
        String userName = ScannerUtil.nextLine(scanner, message);
        while (!userController.isValidateUserName(userName)) {
            System.out.println("중복된 아이디입니다.");
            userName = ScannerUtil.nextLine(scanner, message);
        }

        message = "비밀번호를 입력해주세요";
        String password1 = ScannerUtil.nextLine(scanner, message);
        message = "다시" + message;
        String password2 = ScannerUtil.nextLine(scanner, message);
        if (!userController.isSamePassword(password1, password2)) {
            System.out.println("두 비밀번호가 다릅니다");
            message = "비밀번호를 입력해주세요";
            password1 = ScannerUtil.nextLine(scanner, message);
            message = "다시" + message;
            password2 = ScannerUtil.nextLine(scanner, message);
        }

        message = "닉네임을 입력해주세요";
        String nickName = ScannerUtil.nextLine(scanner, message);
        if (!userController.isValidateNickName(nickName)) {
            System.out.println("중복된 닉네임입니다");
            message = "다시 닉네임을 입력해주세요";
            nickName = ScannerUtil.nextLine(scanner, message);
        }

        UserDTO userDTO = new UserDTO(userName, password1, nickName, 3);
        userController.insert(userDTO);
    }

    public void showMenu() {
        String message;
        int choice;

        while (logIn != null) {
            if (logIn.getGrade() == 1) {
                //관리자일 경우
                message = "1. 영화 메뉴 2. 영화관 메뉴 3. 유저 관리 4. 로그아웃";
                choice = ScannerUtil.nextInt(scanner, message, 1, 4);
                if (choice == 1) {
                    movieViewer.showAdminList();
                } else if (choice == 2) {
                    theaterViewer.showAdminList();
                } else if (choice == 3) {
                    showAllUser();
                } else if (choice == 4) {
                    //로그아웃
                    logIn = null;
                    break;
                }
            } else {
                //관리자가 아닐 경우
                message = "1. 영화 메뉴 2. 영화관 메뉴 3. 내 정보 4. 로그아웃";
                choice = ScannerUtil.nextInt(scanner, message, 1, 4);
                if (choice == 1) {
                    movieViewer.showList();
                } else if (choice == 2) {

                    theaterViewer.showList();
                } else if (choice == 3) {
                    showOne(logIn.getId());
                } else if (choice == 4) {
                    //로그아웃
                    logIn = null;
                    break;
                }
            }
        }
    }

    public void showAllUser() {
        ArrayList<UserDTO> userList = userController.selectAll();
        for (UserDTO u : userList) {
            System.out.printf("%d. %s\n", u.getId(), u.getNickName());
        }
        String message = "정보 변경 할 이용자의 번호를 입력해주세요(뒤로가기 0)";
        int choice = ScannerUtil.nextInt(scanner, message);

        while (!userController.isValidateUserId(choice)) {
            message = "해당 유저가 없습니다. 이용자 번호를 입력해주세요(뒤로가기 0)";
            choice = ScannerUtil.nextInt(scanner, message);
        }

        if (choice == 0) {
            showMenu();
        } else if (choice == logIn.getId()) {
            System.out.println("관리자 자기 자신의 정보는 변경할 수 없습니다");
            showAllUser();
        } else {
            showOne(choice);
        }
    }

    public void showOne(int id) {
        UserDTO u = userController.selectOne(id);
        String message;
        int choice;
        if (logIn.getGrade() == 1) {
            message = "1. 수정 2. 제거 3. 등급 변경 4. 뒤로가기";
            choice = ScannerUtil.nextInt(scanner, message, 1, 4);

            if (choice == 1) {
                update(id);
            } else if (choice == 2) {
                if (logIn.getId() == id) {
                    System.out.println("관리자는 자기 자신을 지울 수 없습니다");
                } else {
                    delete(id);
                }
            } else if (choice == 3) {
                if (logIn.getId() == id) {
                    System.out.println("관리자는 자신의 등급을 변경할 수 없습니다");
                } else {
                    changeGrade(id);
                }
            } else if (choice == 4) {
                showMenu();
            }
        } else {
            message = "1. 수정 2. 탈퇴 3. 뒤로가기";
            choice = ScannerUtil.nextInt(scanner, message, 1, 3);

            if (choice == 1) {
                update(logIn.getId());
            } else if (choice == 2) {
                delete(logIn.getId());
            } else if (choice == 3) {
                showMenu();
            }
        }
    }

    public void update(int id) {
        UserDTO user = userController.selectOne(id);
        String message;
        int choice;
        if (logIn.getGrade() == 1) {

        } else {
            message = "현재 비밀번호를 입력하세요";
            String password = ScannerUtil.nextLine(scanner, message);

            if (!userController.isSamePassword(password, user.getPassword())) {
                System.out.println("비밀번호가 다릅니다. 메뉴로 돌아갑니다.");
                showMenu();
            }

            message = "1. 닉네임 변경 2. 비밀번호 변경 3. 뒤로가기";
            choice = ScannerUtil.nextInt(scanner, message, 1, 3);

            if (choice == 1) {
                updateNickName(id);
            } else if (choice == 2) {
                updatePassword(id);
            } else if (choice == 3) {
                showMenu();
            }

        }
    }

    public void delete(int id) {
        String message = "정말로 탈퇴하시겠습니까(Y/N)";
        String answer = ScannerUtil.nextLine(scanner, message);

        while (!(answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("N"))) {
            message = "Y나 N을 입력해주세요";
            answer = ScannerUtil.nextLine(scanner, message);
        }

        if (answer.equalsIgnoreCase("Y")) {
            userController.delete(id);
            scoreController.removeAllByUserId(id);
            if (logIn.getId() == id) {
                logIn = null;
                showList();
            } else {
                showAllUser();
            }
        } else if (answer.equalsIgnoreCase("N")) {

        }
    }

    public void updateNickName(int id) {
        UserDTO user = userController.selectOne(id);
        String message = "변경할 닉네임을 입력하세요";
        String nickName = ScannerUtil.nextLine(scanner, message);

        if (!userController.isValidateNickName(nickName)) {
            message = "이미 사용 중인 닉네임입니다";
            nickName = ScannerUtil.nextLine(scanner, message);
        }

        user.setNickName(nickName);
    }

    public void updatePassword(int id) {
        UserDTO user = userController.selectOne(id);

        String message = "비밀번호를 입력해주세요";
        String password1 = ScannerUtil.nextLine(scanner, message);
        message = "다시" + message;
        String password2 = ScannerUtil.nextLine(scanner, message);

        if (!userController.isSamePassword(password1, password2)) {
            System.out.println("두 비밀번호가 다릅니다");
            message = "비밀번호를 입력해주세요";
            password1 = ScannerUtil.nextLine(scanner, message);
            message = "다시" + message;
            password2 = ScannerUtil.nextLine(scanner, message);
        }

        user.setPassword(password1);
    }

    public void changeGrade(int id) {
        UserDTO user = userController.selectOne(id);

        String message = "1. 관리자 2. 평론가 3. 일반 4. 뒤로가기";
        int choice = ScannerUtil.nextInt(scanner, message, 1, 4);

        if (choice == 4) {
            showAllUser();
        } else {
            user.setGrade(choice);
        }
    }
}
