package com.ssafy.todaktodak.domain.user.repository;

import com.ssafy.todaktodak.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public Optional<User> findOneByEmail(String email){
        return em.createQuery("select u from User u where u.userEmail = :email", User.class)
                .setParameter("email", email)
                .getResultList()
                .stream().findAny();
    }

    public User save(User user) {
        if(user.getUserId() == null) {
            user.setProfile_yn("N");
            em.persist(user);
        } else {
            em.merge(user);
        }
        return user;
    }

}
