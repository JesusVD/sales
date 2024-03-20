package com.jesus.sales.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    @NotNull
    private Integer idUser;

    @NotNull
    private RoleDTO role;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 50)
    private String username;

    @NotNull
    @NotEmpty
    @Size(min = 10, max = 60)
    private String password;

    @NotNull
    private boolean enabled;
}
