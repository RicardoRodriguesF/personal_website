package rodrigues.ferreira.ricardo.authorization.authorization.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rodrigues.ferreira.ricardo.authorization.authorization.server.dto.JwtRequestDto;
import rodrigues.ferreira.ricardo.authorization.authorization.server.dto.RegisterDto;
import rodrigues.ferreira.ricardo.authorization.authorization.server.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    //Build login REST API
    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<?> authenticate(@RequestBody JwtRequestDto jwtRequestDto) {
        if (checkIfParamsIsNotNull(jwtRequestDto))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        var token = authService.login(jwtRequestDto);
        if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        return token;
    }

    // Build Register REST API
    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(value = "/refresh/{usernameOrEmail}")
    public ResponseEntity<?> refreshToken(@PathVariable("usernameOrEmail") String usernameOrEmail,
                                                       @RequestHeader("Authorization") String refreshToken) {
        if (checkIfParamsIsNotNull(usernameOrEmail, refreshToken))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        var token = authService.refreshToken(usernameOrEmail, refreshToken);
        if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        return token;
    }

    private boolean checkIfParamsIsNotNull(String usernameOrEmail, String refreshToken) {
        return refreshToken == null || refreshToken.isBlank() ||
                usernameOrEmail == null || usernameOrEmail.isBlank();
    }
    private boolean checkIfParamsIsNotNull(JwtRequestDto jwtRequestDto) {
        return jwtRequestDto == null || jwtRequestDto.getUsernameOrEmail()== null || jwtRequestDto.getUsernameOrEmail().isBlank()
                || jwtRequestDto.getPassword() == null || jwtRequestDto.getPassword().isBlank();
    }

}
