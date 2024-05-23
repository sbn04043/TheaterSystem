package model;

import lombok.Data;

@Data
public class MovieDTO {
    private int id;
    private String title;
    private String content;
    //1: 19, 2: 15, 3: 12, 4: 7, 5: 전체
    private int grade;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof MovieDTO m)
            return id == m.id;
        return false;
    }

    public MovieDTO(String title, String content, int grade) {
        this.title = title;
        this.content = content;
        this.grade = grade;
    }

    public MovieDTO() {
    }

}
