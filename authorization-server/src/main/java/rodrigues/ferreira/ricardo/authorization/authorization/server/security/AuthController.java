package rodrigues.ferreira.ricardo.authorization.authorization.server.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rodrigues.ferreira.ricardo.authorization.authorization.server.dto.JwtRequestDto;
import rodrigues.ferreira.ricardo.authorization.authorization.server.dto.JwtResponseDto;
import rodrigues.ferreira.ricardo.authorization.authorization.server.dto.RegisterDto;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    //Build login REST API
    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<String>authenticate(@RequestBody JwtRequestDto jwtRequestDto) {
        return ResponseEntity.ok(authService.login(jwtRequestDto));
    }

    // Build Register REST API
    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}
