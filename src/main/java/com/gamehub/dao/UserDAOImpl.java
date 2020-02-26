package com.gamehub.dao;

import com.gamehub.entity.UserEntity;
import com.gamehub.entity.UserRoles;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        session.flush();
        session.close();
    }

    @Transactional
    public void deleteUser(String username) {
        Session session = this.sessionFactory.openSession();
        UserEntity p = (UserEntity) session.load(UserEntity.class, username);
        System.out.println(p);

        session.delete(p);
        session.flush();
    }

    @Transactional
    public UserEntity getUser(String username) throws NullPointerException {
        Session session = this.sessionFactory.openSession();

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
