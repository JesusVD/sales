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
public class ClientDTO {

    @Min(value = 1)
    private Integer idClient;
    @NotEmpty
    @NotNull
    @Size(min = 3, max = 100)
    private String firstName;
    @NotEmpty
    @NotNull
    @Size(min = 3, max = 100)
    private String lastName;

    @NotEmpty
    @NotNull
    @Size(min = 10, max = 10)
    private String cardId;

    @NotEmpty
    @NotNull
    @Pattern(regexp = "[0-9]+")
    private String phoneNumber;

    @NotEmpty
    @NotNull
    @Email
    private String email;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 100)
    private String address;
}
