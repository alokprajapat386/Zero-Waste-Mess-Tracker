package com.example.ZeroWasteMessTracker.dto;

import com.example.ZeroWasteMessTracker.entities.User;
import com.example.ZeroWasteMessTracker.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResponse {
    private Long id;
    private String username;
    private String email;
    private String name;
    private String enrollmentId;

    private Role role;

    private String uniqueHostelId;

    public UserProfileResponse(User user){
        this.id=user.getId();
        this.name=user.getName();
        this.enrollmentId=user.getEnrollmentId();
        this.email=user.getEmail();
        this.username=user.getUsername();
        this.role=user.getRole();

        this.uniqueHostelId=null;
        if(user.getHostel()!=null){
            uniqueHostelId=user.getHostel().getUniqueHostelId();
        }
    }
}
