package websocket;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface messageRepository extends JpaRepository<message, Long> {
    message findById(int id);
    @Transactional
    void deleteById(int id);
}