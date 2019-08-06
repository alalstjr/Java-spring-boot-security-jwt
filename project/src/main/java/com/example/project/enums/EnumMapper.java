package com.example.project.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class EnumMapper {

	private Map<String, List<EnumValue>> factory = new HashMap<String, List<EnumValue>>();
	
	// Role List 
	private List<EnumValue> toEnumValues(Class<? extends EnumModel> e){
	    return Arrays
	            .stream(e.getEnumConstants())
	            .map(EnumValue::new)
	            .collect(Collectors.toList());
	}
	
	// 특정 Role 값을 List 형태로 변환
	private List<SimpleGrantedAuthority> toEnumValuesList(UserRole userRole){
	    return Arrays
	    		.asList(userRole)
	            .stream()
	            .map(r -> new SimpleGrantedAuthority(r.getValue()))
				.collect(Collectors.toList()
				);
	}
	
    public void put(String key, Class<? extends EnumModel> e){
        factory.put(key, toEnumValues(e));
    }
    
    // 특정 Role 값을 List 형태로 변환하는 메서드
    public List<SimpleGrantedAuthority> userRoleList(UserRole userRole){
    	return toEnumValuesList(userRole);
    }

    public Map<String, List<EnumValue>> getAll(){
        return factory;
    }

    public Map<String, List<EnumValue>> get(String keys){
        return Arrays
                .stream(keys.split(","))
                .collect(Collectors.toMap(Function.identity(), key -> factory.get(key)));
    }
}
