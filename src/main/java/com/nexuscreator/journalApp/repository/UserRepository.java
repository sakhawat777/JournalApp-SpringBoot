package com.nexuscreator.journalApp.repository;

import com.nexuscreator.journalApp.entity.JournalEntry;
import com.nexuscreator.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {

}