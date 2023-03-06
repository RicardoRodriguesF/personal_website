package rodrigues.ferreira.ricardo.auth.user.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import rodrigues.ferreira.ricardo.auth.user.api.request.PasswordResetRequest;
import rodrigues.ferreira.ricardo.auth.user.api.request.PasswordUpdateWithTokenRequest;
import rodrigues.ferreira.ricardo.auth.user.domain.entity.UserEntity;
import rodrigues.ferreira.ricardo.auth.user.domain.service.UserPasswordService;
import rodrigues.ferreira.ricardo.auth.user.domain.repository.UserRepository;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PasswordResetController {
    private final UserPasswordService userPasswordService;
    private final UserRepository userRepository;

    @PostMapping("/public/forgot-password")
    public void forgotPassword(@RequestBody @Valid PasswordResetRequest input) {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(input.getEmail());
        optionalUser.ifPresent(user -> {
            String token = userPasswordService.generateToken(user);
            //email
            System.out.println(token);
        });
    }

    @PostMapping("/public/change-password")
    public void changePassword(@RequestBody @Valid PasswordUpdateWithTokenRequest input) {
        try {
            userPasswordService.changePassword(input.getPassword(), input.getToken());
        } catch (Exception e) {
            log.error("Error while changing password using token", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
