package by.nika_doroshkevich.userManagement.dto.validation;

import by.nika_doroshkevich.userManagement.enums.UserStatus;
import by.nika_doroshkevich.userManagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class UsernameExistsValidator implements ConstraintValidator<UsernameExistsValidation, String> {

    private static final String DEFAULT_SPRING_USER = "anonymousUser";

    private final UserRepository userRepository;

    @Override
    public void initialize(UsernameExistsValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value.equals(DEFAULT_SPRING_USER)) {
            return false;
        }

        var existingUsers = userRepository.findByUsernameAndStatusIsNot(value, UserStatus.DELETED);
        if (!existingUsers.isEmpty()) {
            return false;
        }

        return true;
    }
}
