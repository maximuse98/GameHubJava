package com.gamehub.dao;

import com.gamehub.entity.UserEntity;
import com.gamehub.entity.UserRoles;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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

    @Override
    @Transactional
    public void addUser(UserEntity user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(user);

        //Commit the transaction
        session.getTransaction().commit();
    }

    @Override
    public UserEntity getUser(String username) {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("example");
        userEntity.setPassword("040f44c1d9a5160f0f87a9bd6dcf3ee54e715739");
        userEntity.setUserRole(UserRoles.USER);

        Session session = this.sessionFactory.getCurrentSession();

        List query = session.createQuery("from UserEntity where login ='" + username+"'").list();
        return (UserEntity) query.get(0);
    }

    @Override
    public void deleteUser(UserEntity user) {

    }
}
