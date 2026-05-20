package com.github.krishantx.RLaaS.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.github.krishantx.Model.RequestDTO;
import com.github.krishantx.RLaaS.Service.MainService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class Endpoints {
    @Autowired
    MainService mainService;

    @PostMapping("/check")
    public ResponseEntity<?> check(HttpServletRequest request, @RequestBody RequestDTO requestDTO) {
        // Call the check service function
        boolean isExhausted = mainService.check(requestDTO);
        if (isExhausted) return ResponseEntity.status(429).body("Too Many Requests");
        else return ResponseEntity.status(200).body("Allowed");
    }
}
