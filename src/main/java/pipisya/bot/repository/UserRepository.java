package pipisya.bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pipisya.bot.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
