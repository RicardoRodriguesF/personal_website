package rodrigues.ferreira.ricardo.authorization.authorization.server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rodrigues.ferreira.ricardo.authorization.authorization.server.dto.JwtRequestDto;
import rodrigues.ferreira.ricardo.authorization.authorization.server.dto.RegisterDto;
import rodrigues.ferreira.ricardo.authorization.authorization.server.entity.User;
import rodrigues.ferreira.ricardo.authorization.authorization.server.repository.RoleRepository;
import rodrigues.ferreira.ricardo.authorization.authorization.server.repository.UserRepository;

import java.util.Collection;
import java.util.HashSet;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    private CryptConfig CryptConfig;
    private PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String login(JwtRequestDto jwtRequestDto) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        jwtRequestDto.getUsernameOrEmail(),
                        jwtRequestDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "User logged in successfully";
    }

    @Override
    public String register(RegisterDto registerDto) {

        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        var user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(CryptConfig.passwordEncoder().encode(registerDto.getPassword()));
        user.setEmail(registerDto.getEmail());
        user.setRoles(new HashSet<>((Collection) roleRepository.findByName("ROLE_USER").get()));
        userRepository.save(user);

        return "User registered successfully!.";
    }
}
