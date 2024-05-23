package controller;

import model.TheaterDTO;

import java.util.ArrayList;

public class TheaterController {
    private ArrayList<TheaterDTO> list;
    private int nextInt;

    public TheaterController() {
        list = new ArrayList<>();
        nextInt = 1;

        TheaterDTO t1 = new TheaterDTO("월드타워", "잠실", "00000000000");
        TheaterDTO t2 = new TheaterDTO("강남", "사거리", "11111111111");
        TheaterDTO t3 = new TheaterDTO("강북", "로터리", "22222222222");

        insert(t1);
        insert(t2);
        insert(t3);
    }

    public void insert(TheaterDTO t) {
        t.setId(nextInt++);
        list.add(t);
    }

    public ArrayList<TheaterDTO> selectAll() {
        return list;
    }

    public boolean isValidateId(int id) {
        if (id == 0) return true;
        TheaterDTO t = new TheaterDTO(id);
        return list.contains(t);
    }

    public TheaterDTO selectOne(int id) {
        TheaterDTO t = new TheaterDTO(id);
        return list.get(list.indexOf(t));
    }

    public boolean isValidateName(String name) {
        for (TheaterDTO t : list) {
            if (t.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }

    public void remove(int id) {
        TheaterDTO t = new TheaterDTO(id);
        list.remove(t);
    }
}
