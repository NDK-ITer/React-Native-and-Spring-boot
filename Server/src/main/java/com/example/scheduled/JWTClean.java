package com.example.scheduled;

import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.example.business.data.entities.UserJWT;
import com.example.business.data.repositories.BaseSpecification;
import com.example.business.data.repositories.implement.IUserJWTRepository;

//active this task after a period 
@Component
public class JWTClean {

    @Autowired
    private IUserJWTRepository userJWTRepository;

    /*
     * fixedRate: Chạy task mỗi khoảng thời gian cố định tính từ khi task bắt đầu
     * chạy.
     * fixedDelay: Chạy task mỗi khoảng thời gian cố định tính từ khi task kết thúc.
     * cron: Sử dụng biểu thức cron để xác định lịch chạy chi tiết hơn.
     * 
     * @Scheduled(cron = "0 0 0 * * ?") Chạy vào nửa đêm mỗi ngày
     */
    @Scheduled(fixedRate = (10 * 1000)) /* one day */
    public void CleanJWTExpired() {
        Specification<UserJWT> userJWTSpecification = BaseSpecification.hasPropertyLessThan("expiredDate",
                LocalDateTime.now());
        var userJWTExpired = userJWTRepository.findAll(userJWTSpecification);
        userJWTRepository.deleteAll(userJWTExpired);
    }
}
