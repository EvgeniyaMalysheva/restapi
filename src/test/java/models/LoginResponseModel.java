package models;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class LoginResponseModel {
    private String userId, username, password, token, expires, isActive;

    @JsonProperty("created_date")
    private String createdDate;

}
