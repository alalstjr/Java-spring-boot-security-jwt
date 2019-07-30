package com.example.project.controller;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.enums.EnumMapper;
import com.example.project.enums.EnumModel;
import com.example.project.enums.EnumValue;
import com.example.project.enums.UserRole;

@RestController
public class AuthController {

	@GetMapping("/enum")
	public Map<String, Object> getEnum() {
		Map<String, Object> enums = new LinkedHashMap<String, Object>();
		
		Class userRole = UserRole.class;
		
		enums.put("userRole", userRole.getEnumConstants());
		
		return enums;
	}
	
	@GetMapping("/value")
	public Map<String, List<EnumValue>> getEnumValue() {
	    Map<String, List<EnumValue>> enumValues = new LinkedHashMap<>();

	    enumValues.put("userRole", toEnumValues(UserRole.class));

	    return enumValues;
	}
	
	private List<EnumValue> toEnumValues(Class<? extends EnumModel> e){
	    /*
	        // Java8이 아닐경우
	        List<EnumValue> enumValues = new ArrayList<>();
	        for (EnumModel enumType : e.getEnumConstants()) {
	            enumValues.add(new EnumValue(enumType));
	        }
	        return enumValues;
	     */
	    return Arrays
	            .stream(e.getEnumConstants())
	            .map(EnumValue::new)
	            .collect(Collectors.toList());
	}
	
    private EnumMapper enumMapper;

    public AuthController(EnumMapper enumMapper) {
        this.enumMapper = enumMapper;
    }

    @GetMapping("/mapper")
    public Map<String, List<EnumValue>> getMapper() {
        return enumMapper.getAll();
    }
}
