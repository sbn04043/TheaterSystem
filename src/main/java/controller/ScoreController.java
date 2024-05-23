package controller;

import lombok.Setter;
import model.ScoreDTO;
import model.UserDTO;

import java.util.ArrayList;

public class ScoreController {
    private ArrayList<ScoreDTO> list;
    private int nextId;
    @Setter
    private UserController userController;

    public ScoreController() {
        list = new ArrayList<>();
        nextId = 1;
    }

    public double averageScore(int movieId) {
        ArrayList<ScoreDTO> scoreList = selectAllByMovieId(movieId);
        int sum = 0;
        for (ScoreDTO s : scoreList) {
            sum += s.getScore();
        }
        if (sum == 0) return 0;
        return (double) sum / scoreList.size();
    }

    public double averageScore(ArrayList<ScoreDTO> list) {
        int sum = 0;
        for (ScoreDTO s : list) {
            sum += s.getScore();
        }
        if (sum == 0) return 0;
        return (double) sum / list.size();
    }

    public ArrayList<ScoreDTO> selectAllByMovieId(int movieId) {
        ArrayList<ScoreDTO> scoreList = new ArrayList<>();
        for (ScoreDTO s : list) {
            if (s.getMovieId() == movieId) {
                scoreList.add(s);
            }
        }
        return scoreList;
    }

    public ArrayList<ScoreDTO> getCriticList(ArrayList<ScoreDTO> list) {
        ArrayList<ScoreDTO> criticList = new ArrayList<>();
        for (ScoreDTO s : list) {
            UserDTO u = userController.selectOne(s.getUserId());
            if (u.getGrade() == 2) {
                criticList.add(s);
            }
        }
        return criticList;
    }

    public ArrayList<ScoreDTO> getCommonList(ArrayList<ScoreDTO> list) {
        ArrayList<ScoreDTO> commonList = new ArrayList<>();
        for (ScoreDTO s : list) {
            UserDTO u = userController.selectOne(s.getUserId());
            if (u.getId() != 2) {
                commonList.add(s);
            }
        }
        return commonList;
    }

    public void insert(ScoreDTO s) {
        s.setId(nextId++);
        list.add(s);
    }

    public ScoreDTO selectByMovieIdUserId(int userId, int movieId) {
        for (ScoreDTO s : list) {
            if (s.getUserId() == userId && s.getMovieId() == movieId) {
                return s;
            }
        }
        return null;
    }

    public void removeAllByMovieId(int movieId) {
        ArrayList<Integer> intList = new ArrayList<>();

        for (ScoreDTO s : list) {
            if (s.getMovieId() == movieId) {
                intList.add(s.getId());
            }
        }
        for (int i : intList) {
            ScoreDTO scoreDTO = new ScoreDTO(i);
            list.remove(scoreDTO);
        }
    }

    public void removeAllByUserId(int userId) {
        ArrayList<Integer> intList = new ArrayList<>();

        for (ScoreDTO s : list) {
            if (s.getUserId() == userId) {
                intList.add(s.getId());
            }
        }
        for (int i : intList) {
            ScoreDTO scoreDTO = new ScoreDTO(i);
            list.remove(scoreDTO);
        }
    }
}
