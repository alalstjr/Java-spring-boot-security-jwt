package com.example.project.enums;

public enum UserRole implements EnumModel {
	ADMIN("ROLE_ADMIN"),
	USER("ROLE_USER");
	
	private String roleName;
	
	UserRole(String roleName) {
		this.roleName = roleName;
	}
	
	@Override
	public String getKey() {
		return name();
	}
	
	@Override
    public String getValue() {
        return roleName;
    }
}
