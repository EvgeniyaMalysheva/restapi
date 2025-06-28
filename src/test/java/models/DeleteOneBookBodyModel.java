package models;

import lombok.Data;

@Data
public class DeleteOneBookBodyModel {
    private String userId, isbn;
}
