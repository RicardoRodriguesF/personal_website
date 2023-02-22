package rodrigues.ferreira.ricardo.authorization.authorization.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rodrigues.ferreira.ricardo.authorization.authorization.server.dto.JwtRequestDto;
import rodrigues.ferreira.ricardo.authorization.authorization.server.dto.JwtResponseDto;
import rodrigues.ferreira.ricardo.authorization.authorization.server.dto.RegisterDto;
import rodrigues.ferreira.ricardo.authorization.authorization.server.entity.Role;
import rodrigues.ferreira.ricardo.authorization.authorization.server.entity.User;
import rodrigues.ferreira.ricardo.authorization.authorization.server.repository.RoleRepository;
import rodrigues.ferreira.ricardo.authorization.authorization.server.repository.UserRepository;
import rodrigues.ferreira.ricardo.authorization.authorization.server.security.jwt.JwtTokenProvider;
import rodrigues.ferreira.ricardo.authorization.authorization.server.utils.CryptConfig;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CryptConfig cryptconfig;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public ResponseEntity<JwtResponseDto> login(JwtRequestDto jwtRequestDto) {
        var usernameOrEmail = jwtRequestDto.getUsernameOrEmail();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        jwtRequestDto.getUsernameOrEmail(),
                        jwtRequestDto.getPassword()
                )
        );

        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail));
        ;

        var tokenResponse = new JwtResponseDto();
        tokenResponse = jwtTokenProvider.createAccessToken(usernameOrEmail, user.getRoles().stream().map(Role::getDescription).collect(Collectors.toList()));

        return ResponseEntity.ok(tokenResponse);

    }

    public ResponseEntity<JwtResponseDto> refreshToken(String usernameOrEmail, String refreshToken) {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail));
        ;
        var tokenResponse = new JwtResponseDto();

        tokenResponse = jwtTokenProvider.refreshToken(refreshToken);

        return ResponseEntity.ok(tokenResponse);
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
        user.setName(registerDto.getUsername());
        user.setUsername(registerDto.getUsername());
        user.setPassword(cryptconfig.passwordEncoder().encode(registerDto.getPassword()));
        user.setEmail(registerDto.getEmail());

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);

        return "User registered successfully!.";
    }
}
