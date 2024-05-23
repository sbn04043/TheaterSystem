package model;

import lombok.Data;

@Data
public class UserDTO {
    private int id;
    private String userName;
    private String password;
    private String nickName;
    //admin: 1, 평론가: 2, 일반: 3
    private int grade;

    public UserDTO(String userName, String password, String nickName, int grade) {
        id = -1;
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
        this.grade = grade;
    }

    public UserDTO() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof UserDTO) {
            UserDTO userDTO = (UserDTO) o;
            return userDTO.id == id;
        }
        return false;
    }
}
