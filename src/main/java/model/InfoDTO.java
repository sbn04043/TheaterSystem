package model;

import lombok.Data;

@Data
public class InfoDTO {
    private int id;
    private int movieId;
    private int theaterId;
    private String time;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof InfoDTO i)
            return i.id == id;
        return false;
    }

    public InfoDTO() {

    }

    public InfoDTO(int id) {
        this.id = id;
    }

    public InfoDTO(int movieId, int theaterId, String time) {
        this.movieId = movieId;
        this.theaterId = theaterId;
        this.time = time;
    }
}
