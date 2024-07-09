package com.example.middlewares;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.business.application.libs.JWTlib.JWTMethod;
import com.example.business.application.services.AuthnService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenInterceptor implements HandlerInterceptor {
    @Autowired
    private AuthnService authnService;
    @Autowired
    private JWTMethod jwtMethod;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String tokenIdentify = request.getHeader("Authorization");
        if (tokenIdentify != null && tokenIdentify.startsWith("Bearer ")) {
            tokenIdentify = tokenIdentify.substring(7).trim();
            Map<String, Object> resultMap = new HashMap<>();
            if (authnService.JWTIsLogout(tokenIdentify)) {
                resultMap.put("mess", "Token is logout, please login again!");
                resultMap.put("state", 0);
                sendResponse(response, resultMap);
                return false;
            }
            var information = jwtMethod.decodeAndVerifyJWT(tokenIdentify);
            if (information != null) {
                request.setAttribute("userInformation", information);
                return true;
            }
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("mess", "Login expired, please login again!");
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
