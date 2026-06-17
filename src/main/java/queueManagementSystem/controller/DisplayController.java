package queueManagementSystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import queueManagementSystem.dto.resppnse.DisplayResponse;
import queueManagementSystem.service.DisplayService;

import java.util.List;

@RestController
@RequestMapping("/api/display")
@RequiredArgsConstructor
public class DisplayController {

    private final DisplayService displayService;

    @GetMapping
    public List<DisplayResponse> getDisplay() {

        return displayService.getDisplay();
    }
}
