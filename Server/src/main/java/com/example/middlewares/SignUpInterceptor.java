package com.example.middlewares;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.business.application.services.AuthnService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SignUpInterceptor implements HandlerInterceptor {

    @Autowired
    private AuthnService authnService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String method = request.getMethod();
        if ("POST".equals(method) || "PUT".equals(method)) {
            Map<String, String> requestBody = getRequestBody(request);
            var checkingResult = authnService.IsExistByEmail(requestBody.get("email"));
            if(checkingResult){
                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("mess", "Email already exists");
                resultMap.put("state", 0);
                sendResponse(response, resultMap);
                return false;
            }
        }
        return true;
    }

    private Map<String, String> getRequestBody(HttpServletRequest request) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            String requestBody = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            return objectMapper.readValue(requestBody, HashMap.class);
        }
    }

    private void sendResponse(HttpServletResponse response, Map<String, Object> resultMap) throws IOException {
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String json = objectMapper.writeValueAsString(resultMap);

        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }
}
