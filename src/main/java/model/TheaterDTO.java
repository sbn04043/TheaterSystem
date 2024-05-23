package model;

import lombok.Data;

@Data
public class TheaterDTO {
    private int id;
    private String name;
    private String location;
    private String number;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof TheaterDTO t) {
            return t.id == id;
        }
        return false;
    }

    public TheaterDTO(String name, String location, String number) {
        this.name = name;
        this.location = location;
        this.number = number;
    }

    public TheaterDTO() {

    }

    public TheaterDTO(int id) {
        this.id = id;
    }
}
