package com.example.services.interfaces;

import com.example.Dto.FinishRegistrationRequestDto;

public interface CalculateScoringService {
    void scoringCalculateOffer(FinishRegistrationRequestDto requestDto, Long statementId);
}
