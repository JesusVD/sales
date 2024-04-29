package com.jesus.sales.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SaleDetailDTO {

    @JsonBackReference
    private SaleDTO sale;

    @NotNull
    private ProductDTO product;

    @NotNull
    @Min(value = 1)
    private short quantity;

    @NotNull
    @Min(value = 1)
    private double salePrice;

    @NotNull
    @Min(value = 0)
    private double discount;
}
