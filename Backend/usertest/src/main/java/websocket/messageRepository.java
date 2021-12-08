package websocket;

import org.springframework.data.jpa.repository.JpaRepository;

public interface messageRepository extends JpaRepository<message, Long> {
   
}