package rodrigues.ferreira.ricardo.authorization.authorization.server.security;


import rodrigues.ferreira.ricardo.authorization.authorization.server.dto.JwtRequestDto;
import rodrigues.ferreira.ricardo.authorization.authorization.server.dto.RegisterDto;

public interface AuthService {

    String login(JwtRequestDto jwtRequestDto);

    String register(RegisterDto registerDto);
}
