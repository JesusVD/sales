package com.jesus.sales.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO {

    private Integer id;
    private String nameCategory;
    private String descriptionCategory;
    private boolean enabledCategory;
}
