package com.example.ZeroWasteMessTracker.services;

import com.example.ZeroWasteMessTracker.dto.HostelAddRequest;
import com.example.ZeroWasteMessTracker.dto.RegisterRequest;
import com.example.ZeroWasteMessTracker.entities.Hostel;
import com.example.ZeroWasteMessTracker.entities.User;
import com.example.ZeroWasteMessTracker.enums.Role;
import com.example.ZeroWasteMessTracker.exceptions.HostelAlreadyExistsException;
import com.example.ZeroWasteMessTracker.exceptions.HostelNotFoundException;
import com.example.ZeroWasteMessTracker.exceptions.UnauthorizedException;
import com.example.ZeroWasteMessTracker.repositories.HostelRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class HostelService {
    private final AuthService authService;
    private final HostelRepository hostelRepository;
    private final UserService userService;

    public HostelService(HostelRepository hostelRepository, AuthService authService, UserService userService) {
        this.hostelRepository = hostelRepository;
        this.authService=authService;
        this.userService=userService;
    }
    @Transactional
    public void addHostel(HostelAddRequest request){
        if(request.getRole()!= Role.ADMIN){
            throw new UnauthorizedException();
        }
        if(hostelRepository.existsByUniqueHostelId(request.getUniqueHostelId())){
            throw new HostelAlreadyExistsException();
        }
        Hostel hostel=new Hostel();
        hostel.setHostelName(request.getHostelName());
        hostel.setUniqueHostelId(request.getUniqueHostelId());



        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName(request.getName());
        registerRequest.setEmail(request.getEmail());
        registerRequest.setPassword(request.getPassword());
        registerRequest.setUsername(request.getUsername());
        registerRequest.setEnrollmentId(request.getEnrollmentId());
        registerRequest.setRole(request.getRole());
        registerRequest.setUniqueHostelId(request.getUniqueHostelId());
         hostel = hostelRepository.saveAndFlush(hostel);
         authService.registerHostelAdmin(registerRequest);
         hostelRepository.save(hostel);
    }

    public Hostel getHostel(String uniqueHostelId){
        return hostelRepository.findByUniqueHostelId(uniqueHostelId)
                .orElseThrow(HostelNotFoundException::new);
    }

    public boolean existsHostel(String uniqueHostelId){
        return hostelRepository.existsByUniqueHostelId(uniqueHostelId);
    }
}
