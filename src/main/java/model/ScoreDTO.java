package model;

import lombok.Data;

@Data
public class ScoreDTO {
    private int id;
    private int userId;
    private int movieId;
    private int score;
    private String content;

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o instanceof ScoreDTO s)
            return s.id == id;
        return false;
    }

    public ScoreDTO() {
        content = "";
    }

    public ScoreDTO(int id) {
        this.id = id;
    }
}
