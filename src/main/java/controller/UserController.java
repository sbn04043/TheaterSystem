package controller;

import model.UserDTO;

import java.util.ArrayList;

public class UserController {
    private ArrayList<UserDTO> list;
    private int nextId;

    public UserController() {
        list = new ArrayList<>();
        nextId = 1;

        UserDTO admin = new UserDTO("admin", "123", "admin1", 1);
        UserDTO critic = new UserDTO("critic", "123", "critic1", 2);
        UserDTO common = new UserDTO("common", "123", "common1", 3);

        insert(admin);
        insert(critic);
        insert(common);
    }

    public void insert(UserDTO u) {
        u.setId(nextId++);
        list.add(u);
    }

    public boolean isValidateUserName(String userName) {
        for (UserDTO u : list) {
            if (userName.equals(u.getUserName())) {
                return false;
            }
        }
        return true;
    }

    public boolean isSamePassword(String password1, String password2) {
        return password1.equals(password2);
    }

    public boolean isValidateNickName(String nickName) {
        for (UserDTO u : list) {
            if (nickName.equals(u.getNickName())) {
                return false;
            }
        }
        return true;
    }

    public UserDTO selectOne(String userName, String password) {
        for (UserDTO u : list) {
            if (userName.equals(u.getUserName()) && password.equals(u.getPassword())) {
                return u;
            }
        }
        return null;
    }

    public UserDTO selectOne(int id) {
        UserDTO u = new UserDTO();
        u.setId(id);
        return list.get(list.indexOf(u));
    }

    public boolean isValidateUserId(int id) {
        if (id == 0) return true;
        for (UserDTO u : list) {
            if (u.getId() == id) return true;
        }
        return false;
    }

    public ArrayList<UserDTO> selectAll() {
        return list;
    }

    public void delete(int id) {
        UserDTO u = new UserDTO();
        u.setId(id);
        list.remove(u);
    }
}
