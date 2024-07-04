package com.example.business.application.services;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.business.data.repositories.implement.IRoleRepository;

@Service
public class RoleService {
    //#region repositories
    @Autowired
    private IRoleRepository roleRepository;
    //#endregion
    
    //#region business
    public List<HashMap<String, String>> GetAllRole(){
        // declare 'result'
        var result = new ArrayList<HashMap<String, String>>();
        // process
        var listRole = roleRepository.findAll();
        for (var item : listRole) {
            var roleFormat = new HashMap<String, String>();
            roleFormat.put("name", item.getName());
            roleFormat.put("normalize", item.getNormalizeName());
            result.add(roleFormat);
        }
        return result;
    }
    //#endregion
}
