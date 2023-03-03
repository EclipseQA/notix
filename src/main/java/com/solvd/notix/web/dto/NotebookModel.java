package com.solvd.notix.web.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;

@Builder
@EqualsAndHashCode
public class NotebookModel {

    private String brandName;
    private String cpu;
    private String ram;
    private String resolution;
    private String year;
}
