package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.model.entity.Comments;

import java.time.Instant;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, String> {

    void deleteByUpdatedOnBefore(Instant updatedOn);

}
