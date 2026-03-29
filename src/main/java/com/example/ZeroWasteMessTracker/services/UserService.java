package com.example.ZeroWasteMessTracker.services;

import com.example.ZeroWasteMessTracker.dto.ApprovalResponse;
import com.example.ZeroWasteMessTracker.dto.ChangePasswordRequest;
import com.example.ZeroWasteMessTracker.dto.UpdateUserRequest;
import com.example.ZeroWasteMessTracker.dto.UserProfileResponse;
import com.example.ZeroWasteMessTracker.entities.ApprovalRequest;
import com.example.ZeroWasteMessTracker.entities.User;
import com.example.ZeroWasteMessTracker.enums.ApprovalStatus;
import com.example.ZeroWasteMessTracker.enums.Role;
import com.example.ZeroWasteMessTracker.exceptions.ApprovalRequestNotFoundException;
import com.example.ZeroWasteMessTracker.exceptions.InvalidCredentialsException;
import com.example.ZeroWasteMessTracker.exceptions.UserNotFoundException;
import com.example.ZeroWasteMessTracker.repositories.ApprovalRepository;
import com.example.ZeroWasteMessTracker.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ApprovalRepository approvalRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, ApprovalRepository approvalRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.approvalRepository = approvalRepository;
        this.passwordEncoder = passwordEncoder;

    }


    public User getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    public boolean usernameAvailable(String username){
        return !userRepository.existsByUsername(username);
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }
    public User updateProfile(Long id, UpdateUserRequest updatedUser){
        User user=userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User not found"));


        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());

        return userRepository.save(user);
    }

    public List<UserProfileResponse> getAllUsers(Long hostelId){

        return userRepository.findByHostel_Id(hostelId).stream().map(UserProfileResponse::new).toList();
    }

    public void assignRole(Long userId, Role role){
        User user=userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found"));
        user.setRole(role);
        userRepository.save(user);
    }

    public void approve(String username){
        User user=userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
        ApprovalRequest request=approvalRepository.findByUser_Id(user.getId())
                .orElseThrow(ApprovalRequestNotFoundException::new);
        request.setApprovalStatus(ApprovalStatus.APPROVED);
        user.setActive(true);
//        request.setApprovalStatus(ApprovalStatus.APPROVED);
        approvalRepository.save(request);
        userRepository.save(user);
    }

    public void reject(String username){

        User user=userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
        ApprovalRequest request=approvalRepository.findByUser_Id(user.getId())
                .orElseThrow(ApprovalRequestNotFoundException::new);
        request.setApprovalStatus(ApprovalStatus.REJECTED);
        user.setActive(false);
        approvalRepository.save(request);
        userRepository.save(user);
    }

    public ApprovalResponse getRequest(String username){
        User user = getUserByUsername(username);
        return new ApprovalResponse(
                approvalRepository.findByUser_Id(user.getId())
                .orElseThrow(ApprovalRequestNotFoundException::new)
        );
    }

    public List<ApprovalResponse> getRequestSortedByAsc(Long hostelId,ApprovalStatus status){

        return approvalRepository.findByUser_Hostel_IdAndApprovalStatusOrderByCreatedAtAsc(hostelId,status)
                .stream().map(ApprovalResponse::new).toList();
//        requests.sort(Comparator.comparing(ApprovalRequest::getCreatedAt));

    }

    public List<ApprovalResponse> getRequestSortedByDesc(Long hostelId,ApprovalStatus status){

        return approvalRepository.findByUser_Hostel_IdAndApprovalStatusOrderByCreatedAtDesc(hostelId,status)
                .stream().map(ApprovalResponse::new).toList();
//        requests.sort(Comparator.comparing(ApprovalRequest::getCreatedAt));

    }

    public void changePassword(String username, ChangePasswordRequest request){
        User user=userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
        if(!passwordEncoder.matches(
                request.getOldPassword(),
                user.getPassword()
        )){
            throw new InvalidCredentialsException();
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

}
