package com.example.ZeroWasteMessTracker.controllers;

import com.example.ZeroWasteMessTracker.dto.HostelAddRequest;
import com.example.ZeroWasteMessTracker.services.HostelService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/hostel")
public class HostelController {
    public HostelController(HostelService hostelService) {
        this.hostelService = hostelService;
    }

    private final HostelService hostelService;


}
