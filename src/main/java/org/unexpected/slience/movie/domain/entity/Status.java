package org.unexpected.slience.movie.domain.entity;

public enum Status {

    PLAYING("상영중"),
    COMING_SOON("개봉예정"),
    CLOSED("상영종료"),;

    final String desc;

    Status(String desc) {
        this.desc = desc;
    }
}
