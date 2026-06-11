package org.unexpected.slience.screen.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Screen {

    private Long id;

    private String name;

    private int totalRows;
    private int totalCols;

    private List<Long> seatIds = new ArrayList<>();

}
