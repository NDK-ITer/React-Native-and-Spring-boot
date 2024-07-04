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

import com.example.business.application.libs.JWTMethod;
import com.example.business.data.repositories.implement.IUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenInterceptor implements HandlerInterceptor {
    @Autowired
    private JWTMethod jwtMethod;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String tokenIdentify = request.getHeader("Authorization").substring(7).trim();
        var information = jwtMethod.decodeAndVerifyJWT(tokenIdentify);
        if(information != null){
            request.setAttribute("userIdentify", information);
            return true;
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("mess", "login expired, login again!");
        resultMap.put("state", 0);
        sendResponse(response, resultMap);
        return false;
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
