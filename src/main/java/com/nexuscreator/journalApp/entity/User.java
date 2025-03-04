package com.nexuscreator.journalApp.entity;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
public class User {
    @Id
    private ObjectId id; // MongoDB ObjectId

    @Indexed(unique = true) // ✅ Ensures userName is unique
    @NonNull
    private String userName;

    @NonNull
    private String password;
}
