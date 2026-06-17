package queueManagementSystem.service;

import queueManagementSystem.dto.resppnse.KioskResponse;
import queueManagementSystem.dto.resppnse.StatisticsResponse;

import java.util.List;

public interface StatisticsService {

    StatisticsResponse getStatistics();

    List<KioskResponse> getKioskData();

}
