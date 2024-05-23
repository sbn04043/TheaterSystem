package controller;

import model.MovieDTO;
import model.UserDTO;

import java.util.ArrayList;

public class MovieController {
    private ArrayList<MovieDTO> list;
    private int nextId;

    public MovieController() {
        list = new ArrayList<>();
        nextId = 1;

        MovieDTO ring = new MovieDTO("반지의제왕", "반지 파괴하기", 3);
        MovieDTO titanic = new MovieDTO("타이타닉", "항해하기", 2);
        MovieDTO tazza = new MovieDTO("타짜", "도박하기", 1);

        insert(ring);
        insert(titanic);
        insert(tazza);
    }

    public void insert(MovieDTO m) {
        m.setId(nextId++);
        list.add(m);
    }

    public ArrayList<MovieDTO> selectAll() {
        return list;
    }

    public boolean isValidateId(int id) {
        if (id == 0) return true;

        MovieDTO m = new MovieDTO();
        m.setId(id);
        return list.contains(m);
    }

    public MovieDTO selectOne(int id) {
        for (MovieDTO m : list) {
            if (id == m.getId())
                return m;
        }
        return null;
    }

    public String getAge(int grade) {
        if (grade == 1) return "19세 이상";
        else if (grade == 2) return "15세 이상";
        else if (grade == 3) return "12세 이상";
        else if (grade == 4) return "7세 이상";
        else if (grade == 5) return "전연령";
        return "뭔가 이상함";
    }

    public void remove(int id) {
        MovieDTO m = new MovieDTO();
        m.setId(id);
        list.remove(m);
    }
}
