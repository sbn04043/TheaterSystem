package controller;

import model.InfoDTO;

import java.util.ArrayList;

public class InfoController {
    private ArrayList<InfoDTO> list;
    private int nextId;

    public InfoController() {
        list = new ArrayList<>();
        nextId = 1;

        InfoDTO i1 = new InfoDTO(1, 1, "2200");
        InfoDTO i2 = new InfoDTO(2, 1, "1000");
        InfoDTO i3 = new InfoDTO(3, 1, "1500");

        insert(i1);
        insert(i2);
        insert(i3);
    }

    public void insert(InfoDTO i) {
        i.setId(nextId++);
        list.add(i);
    }

    public InfoDTO selectOne(int id) {
        InfoDTO infoDTO = new InfoDTO(id);
        return list.get(list.indexOf(infoDTO));
    }

    public ArrayList<InfoDTO> selectAll() {
        return list;
    }

    public ArrayList<InfoDTO> selectAllByMovieId(int movieId) {
        ArrayList<InfoDTO> infoList = new ArrayList<>();
        for (InfoDTO i : list) {
            if (movieId == i.getMovieId())
                infoList.add(i);
        }
        return infoList;
    }

    public ArrayList<InfoDTO> selectAllByTheaterId(int theaterId) {
        ArrayList<InfoDTO> infoList = new ArrayList<>();
        for (InfoDTO i : list) {
            if (theaterId == i.getTheaterId())
                infoList.add(i);
        }
        return infoList;
    }

    public void remove(int id) {
        InfoDTO infoDTO = new InfoDTO(id);
        list.remove(infoDTO);
    }

    public void removeAllByMovieID(int movieId) {
        ArrayList<Integer> intList = new ArrayList<>();
        for (InfoDTO i : list) {
            if (i.getMovieId() == movieId) {
                intList.add(i.getId());
            }
        }
        for (int i : intList) {
            InfoDTO infoDTO = new InfoDTO(i);
            list.remove(infoDTO);
        }
    }

    public void removeAllByTheaterId(int theaterId) {
        ArrayList<Integer> intList = new ArrayList<>();
        for (InfoDTO i : list) {
            if (i.getTheaterId() == theaterId) {
                intList.add(i.getId());
            }
        }
        for (int i : intList) {
            InfoDTO infoDTO = new InfoDTO(i);
            list.remove(infoDTO);
        }
    }
}
