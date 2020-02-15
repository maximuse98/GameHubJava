package com.gamehub.dao;

import com.gamehub.entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {
    //private static final Logger logger = LoggerFactory.getLogger(GameDAOImpl.class);

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }

    @Transactional
    public void addUser(UserEntity user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
    }

    public UserEntity getUser(String username) throws NullPointerException {
        Session session = this.sessionFactory.getCurrentSession();

        List query = session.createQuery("from UserEntity where login ='" + username+"'").list();
        UserEntity userEntity;
        try{
            userEntity = (UserEntity) query.get(0);
        }
        catch (IndexOutOfBoundsException e){
            throw new UsernameNotFoundException("User not found");
        }
        return userEntity;
    }
}
