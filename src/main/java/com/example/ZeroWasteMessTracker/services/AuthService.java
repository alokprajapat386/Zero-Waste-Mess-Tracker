package com.example.ZeroWasteMessTracker.services;

import com.example.ZeroWasteMessTracker.dto.*;
import com.example.ZeroWasteMessTracker.entities.ApprovalRequest;
import com.example.ZeroWasteMessTracker.entities.Hostel;
import com.example.ZeroWasteMessTracker.entities.PasswordResetToken;
import com.example.ZeroWasteMessTracker.entities.User;
import com.example.ZeroWasteMessTracker.exceptions.*;
import com.example.ZeroWasteMessTracker.repositories.ApprovalRepository;
import com.example.ZeroWasteMessTracker.repositories.HostelRepository;
import com.example.ZeroWasteMessTracker.repositories.PasswordResetTokenRepository;
import com.example.ZeroWasteMessTracker.repositories.UserRepository;
import com.example.ZeroWasteMessTracker.security.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ApprovalRepository approvalRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final HostelRepository hostelRepository;
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, ApprovalRepository approvalRepository, PasswordResetTokenRepository passwordResetTokenRepository, HostelRepository hostelRepository) {
        this.userRepository = userRepository;
        this.approvalRepository=approvalRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordResetTokenRepository=passwordResetTokenRepository;
        this.jwtService = jwtService;
        this.hostelRepository=hostelRepository;
    }

    @Transactional
    public void register(RegisterRequest request) throws UsernameAlreadyExistsException {
        if (userRepository.existsByUsername(
                request.getUsername()
        )) {
            throw new UsernameAlreadyExistsException();
        }
        User user=new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setEnrollmentId(request.getEnrollmentId());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setActive(false);
        user.setRole(request.getRole());
        user.setName(request.getName());

        Hostel hostel = hostelRepository.findByUniqueHostelId(request.getUniqueHostelId())
                        .orElseThrow(HostelNotFoundException::new);
        user.setHostel(hostel);
        userRepository.save(user);

        ApprovalRequest approvalRequest=new ApprovalRequest();
        approvalRequest.setUser(user);
        approvalRequest.setHostel(hostel);
        approvalRepository.save(approvalRequest);
    }

    @Transactional
    public void registerHostelAdmin(RegisterRequest request) throws UsernameAlreadyExistsException {
        if (userRepository.existsByUsername(
                request.getUsername()
        )) {
            throw new UsernameAlreadyExistsException();
        }
        User user=new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setEnrollmentId(request.getEnrollmentId());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setActive(true);
        user.setRole(request.getRole());
        user.setName(request.getName());

        Hostel hostel = hostelRepository.findByUniqueHostelId(request.getUniqueHostelId())
                .orElseThrow(HostelNotFoundException::new);
        user.setHostel(hostel);
        userRepository.save(user);

    }



    public LoginResponse login(LoginRequest request){
        User user=userRepository
                .findByUsernameOrEmail(request.getIdentifier(), request.getIdentifier())
                .orElseThrow(UserNotFoundException::new);
        if(!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())){
            throw new InvalidCredentialsException();
        }
        if(!user.isActive()){
            throw  new AccountNotActiveException();
        }

        String token= jwtService.generateToken(user.getUsername());
        return new LoginResponse(token, user.getUsername(), user.getRole());

    }


    public void generateToken(PasswordResetTokenRequest request){


        PasswordResetToken token= new PasswordResetToken();
        User user=userRepository.findByUsernameOrEmail(request.getIdentifier(), request.getIdentifier())
                .orElseThrow(UserNotFoundException::new);
        token.setUsername(user.getUsername());
        passwordResetTokenRepository.save(token);

    }

    public void resetPassword(PasswordResetRequest request){
        User user=userRepository.findByUsernameOrEmail(request.getIdentifier(), request.getIdentifier())
                .orElseThrow(UserNotFoundException::new);

        PasswordResetToken token=passwordResetTokenRepository.findByToken(request.getToken())
                .orElseThrow(InvalidPasswordResetTokenException::new);

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        passwordResetTokenRepository.delete(token);
        userRepository.save(user);
    }
}
