package rodrigues.ferreira.ricardo.auth.user.api.controller;

import rodrigues.ferreira.ricardo.auth.user.api.response.UserResponse;
import rodrigues.ferreira.ricardo.auth.user.api.exception.ResourceNotFoundException;
import rodrigues.ferreira.ricardo.auth.user.api.request.MyUserRegisterRequest;
import rodrigues.ferreira.ricardo.auth.user.api.request.MyUserUpdatePasswordRequest;
import rodrigues.ferreira.ricardo.auth.user.api.request.MyUserUpdateRequest;
import rodrigues.ferreira.ricardo.auth.user.domain.entity.UserEntity;
import rodrigues.ferreira.ricardo.auth.user.domain.repository.UserRepository;
import rodrigues.ferreira.ricardo.auth.user.security.CanEditMyUser;
import rodrigues.ferreira.ricardo.auth.user.security.CanReadMyUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class MyUserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public MyUserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @CanReadMyUser
    @GetMapping
    public UserResponse me(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getClaims().get("sub").toString();

        return userRepository.findByEmail(email)
               .map(UserResponse::of)
               .orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

    @CanEditMyUser
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal Jwt jwt,
                        @RequestBody MyUserUpdateRequest request) {
        String email = jwt.getClaims().get("sub").toString();
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
        request.update(user);
        userRepository.save(user);
    }

    @CanEditMyUser
    @PutMapping("/update-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@AuthenticationPrincipal Jwt jwt,
                        @RequestBody MyUserUpdatePasswordRequest request) {
        String email = jwt.getClaims().get("sub").toString();
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@RequestBody @Valid MyUserRegisterRequest request){
        UserEntity user = request.toEntity();
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user = this.userRepository.save(user);
        return UserResponse.of(user);
    }

}
