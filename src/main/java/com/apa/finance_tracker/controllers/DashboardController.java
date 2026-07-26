package com.apa.finance_tracker.controllers;

import com.apa.finance_tracker.constants.SuccessMessage;
import com.apa.finance_tracker.dtos.responses.ApiResponse;
import com.apa.finance_tracker.dtos.responses.DashboardResponse;
import com.apa.finance_tracker.services.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboards")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<ApiResponse<DashboardResponse>> getDashboard(
            @RequestParam Integer year) {
        return ResponseEntity.ok(ApiResponse.success(SuccessMessage.TRANSACTION_LIST_RETRIEVED, dashboardService.getDashboard(year)));
    }
}
