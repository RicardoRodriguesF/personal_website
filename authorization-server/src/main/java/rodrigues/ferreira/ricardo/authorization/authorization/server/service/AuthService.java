package rodrigues.ferreira.ricardo.authorization.authorization.server.service;


import org.springframework.http.ResponseEntity;
import rodrigues.ferreira.ricardo.authorization.authorization.server.dto.JwtRequestDto;
import rodrigues.ferreira.ricardo.authorization.authorization.server.dto.RegisterDto;

public interface AuthService {

    ResponseEntity<?> login(JwtRequestDto jwtRequestDto);

    ResponseEntity<?> refreshToken(String usernameOrEmail, String refreshToken);
    String register(RegisterDto registerDto);
}
