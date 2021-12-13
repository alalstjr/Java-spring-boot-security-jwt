package com.example.demo.controller;

import com.example.demo.constant.EnumMapper;
import com.example.demo.constant.EnumValue;
import com.example.demo.constant.UserRole;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EnumController {

    private final EnumMapper enumMapper;

    @GetMapping("/enum")
    public Map<String, Object> getEnum() {
        Map<String, Object> enums    = new LinkedHashMap<>();
        Class               userRole = UserRole.class;
        enums.put("userRole", userRole.getEnumConstants());

        return enums;
    }

    @GetMapping("/mapper")
    public Map<String, List<EnumValue>> getMapper() {

        return enumMapper.getAll();
    }
}
