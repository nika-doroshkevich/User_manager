package by.nika_doroshkevich.userManagement.repository;

import by.nika_doroshkevich.userManagement.enums.UserStatus;
import by.nika_doroshkevich.userManagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByUsernameAndStatus(String username, UserStatus userStatus);
    List<User> findByStatusIsNot(UserStatus userStatus);
    List<User> findByUsernameAndStatusIsNot(String username, UserStatus userStatus);


    @Modifying
    @Query("update User u set u.status = :userStatus where u.id in :ids and u.status <> 'DELETED'")
    void updateStatusForUsers(@Param("userStatus") UserStatus userStatus, @Param("ids") List<Integer> ids);

    @Modifying
    @Query("update User u set u.status = 'DELETED' where u.id in :ids")
    void deleteUsers(@Param("ids") List<Integer> ids);
}
