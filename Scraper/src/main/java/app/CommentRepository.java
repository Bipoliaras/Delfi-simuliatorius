package app;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import entities.Comment;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends MongoRepository<Comment, ObjectId> { }
