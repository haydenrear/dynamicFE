package com.app.backendforfrontend.repo;

import com.app.backendforfrontend.model.Post;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepo extends ReactiveMongoRepository<Post, String> {
}
