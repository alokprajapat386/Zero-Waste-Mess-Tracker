package com.example.ZeroWasteMessTracker.services;

import com.example.ZeroWasteMessTracker.dto.MealSessionRequest;
import com.example.ZeroWasteMessTracker.dto.MealSessionResponse;
import com.example.ZeroWasteMessTracker.entities.Hostel;
import com.example.ZeroWasteMessTracker.entities.MealSession;
import com.example.ZeroWasteMessTracker.exceptions.SessionNotFoundException;
import com.example.ZeroWasteMessTracker.repositories.MealSessionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MealSessionService {

    private final MealSessionRepository mealSessionRepository ;
    private final HostelService hostelService;
    public MealSessionService(MealSessionRepository mealSessionRepository, HostelService hostelService) {
        this.mealSessionRepository = mealSessionRepository;
        this.hostelService=hostelService;
    }
    @Transactional
    public MealSession createSession(MealSessionRequest request,String uniqueHostelId){

        MealSession session=new MealSession();
        session.setStartTime(request.getStartTime());
        session.setEndTime(request.getEndTime());
        String token;
        do {
            token = UUID.randomUUID().toString();
        } while (mealSessionRepository.existsByQrToken(token));

        session.setQrToken(token);
        session.setActive(true);
        session.setMealType(request.getMealType());
        session.setEnrollmentsCount(0);
        session.setVersion(0L);
        session.setMenu(request.getMenu());
        session.setTotalRatings(0d);
        session.setRatingsCount(0);
        session.setHostel(hostelService.getHostel(uniqueHostelId));
        return mealSessionRepository.save(session);
    }

    public MealSession getSession(Long id){
        return mealSessionRepository.findById(id)
                .orElseThrow(()->
                        new RuntimeException("Session not found"));
    }

    public int getHeadcount(Long sessionId){
        MealSession mealSession= mealSessionRepository.findById(sessionId)
                .orElseThrow(RuntimeException::new);
        return mealSession.getEnrollmentsCount();
    }

    public MealSession getByQrToken(String qrToken){
        return mealSessionRepository.findByQrToken(qrToken)
                .orElseThrow(SessionNotFoundException::new);
    }

    public void deactivateSession(Long id){
        MealSession session=getSession(id);
        session.setActive(false);

        mealSessionRepository.save(session);
    }

    public List<MealSessionResponse> getAllSessions(Long hostelId){
        return mealSessionRepository.findByHostel_Id(hostelId).stream().map(MealSessionResponse::new).toList();

    }

    public List<MealSessionResponse> getActiveSessions(Long hostelId){
        return mealSessionRepository.findByHostel_IdAndActive(hostelId, true).stream().map(MealSessionResponse::new).toList();
    }
}
