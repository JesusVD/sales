package com.jesus.sales.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDTO {

    private Integer id;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 50)
    private String nameCategory;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 150)
    private String descriptionCategory;

    @NotNull
    private boolean enabledCategory;
}
