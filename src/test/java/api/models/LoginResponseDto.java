package api.models;

import lombok.Data;

@Data
public class LoginResponseDto {
    private String userId, username, password, token, expires, created_date, isActive;
}
