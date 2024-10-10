package com.stormling.mongodemo.repository;

import com.stormling.mongodemo.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
}
