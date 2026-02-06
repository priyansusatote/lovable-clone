package com.priyansu.project.lovable_clone.service.impl;

import com.priyansu.project.lovable_clone.dto.PlanResponse;
import com.priyansu.project.lovable_clone.dto.SubscriptionResponse;
import com.priyansu.project.lovable_clone.dto.subscription.PlanLimitsResponse;
import com.priyansu.project.lovable_clone.dto.subscription.UsageTodayResponse;
import com.priyansu.project.lovable_clone.entity.UsageLog;
import com.priyansu.project.lovable_clone.repository.UsageLogRepository;
import com.priyansu.project.lovable_clone.security.AuthUtil;
import com.priyansu.project.lovable_clone.service.SubscriptionService;
import com.priyansu.project.lovable_clone.service.UsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UsageServiceImpl implements UsageService {

    private final UsageLogRepository usageLogRepository;
    private final AuthUtil authUtil;
    private final SubscriptionService subscriptionService;

    @Override
    public void recordTokenUsage(Long userId, int actualTokens) {
        LocalDate today = LocalDate.now();
        UsageLog todayLog = usageLogRepository.findByUserIdAndDate(userId, today)
                .orElseGet(() -> createNewDailyLog(userId, today));

        todayLog.setTokensUsed(todayLog.getTokensUsed() + actualTokens);
        usageLogRepository.save(todayLog);
    }

    @Override
    public void checkDailyTokensUsage() {
        Long userId = authUtil.getCurrentUserId();
        SubscriptionResponse subscriptionResponse = subscriptionService.getCurrentSubscription();
        PlanResponse plan = subscriptionResponse.plan();
        if (plan == null) { //temp
            plan = new PlanResponse(
                    0L,
                    "FREE",
                    100,        // max projects
                    10000,     // tokens per day
                    false,
                    null
            );
        }


        LocalDate today = LocalDate.now();

        UsageLog todayLog = usageLogRepository.findByUserIdAndDate(userId, today)
                .orElseGet(() -> createNewDailyLog(userId, today));

        if(plan.unlimitedAi()){
            return;
        }

        int currentUsage = todayLog.getTokensUsed();
        int limit = plan.maxTokensPerDay();

        if(currentUsage >= limit) {
           throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS,
                   "Daily tokens limit reached, Upgrade Now");
        }
    }


    private UsageLog createNewDailyLog(Long userId, LocalDate date) {
        UsageLog newLog = UsageLog.builder()
                .userId(userId)
                .date(date)
                .tokensUsed(0)
                .build();
        return usageLogRepository.save(newLog);
    }
}
