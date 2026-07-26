package com.apa.finance_tracker.services;

import com.apa.finance_tracker.dtos.responses.DashboardResponse;

public interface DashboardService {
    DashboardResponse getDashboard(Integer year);
}
