package by.nika_doroshkevich.userManagement.dto;

import by.nika_doroshkevich.userManagement.dto.validation.UsernameExistsValidation;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class UserDto {

    @NotEmpty(message = "Please, enter username.")
    @Size(min = 1, max = 63, message = "Username length must be between 1 and 255.")
    @UsernameExistsValidation
    private String username;

    @NotEmpty(message = "Please, enter password.")
    @Size(min = 1, max = 255, message = "Password length must be between 1 and 255.")
    private String password;

    @NotEmpty(message = "Please, enter email.")
    @Email(message = "Email must be valid.")
    @Size(min = 1, max = 63, message = "Email length must be between 1 and 255.")
    private String email;
}
