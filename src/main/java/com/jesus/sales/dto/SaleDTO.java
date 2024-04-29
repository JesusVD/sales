package com.jesus.sales.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SaleDTO {

    private Integer idSale;

    @NotNull
    @JsonIncludeProperties(value = {"idClient"})
    private ClientDTO client;

    @NotNull
    private UserDTO user;

    @NotNull
    private LocalDateTime datetime; //ISO DATE

    @NotNull
    @Min(value = 1)
    private double total;

    @NotNull
    @Min(value = 1)
    private double tax;

    @NotNull
    private boolean enabled;

    @NotNull
    @JsonManagedReference
    private List<SaleDetailDTO> details;
}
