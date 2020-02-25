package com.gamehub.dao;

import java.util.List;

import com.gamehub.entity.ChoiceEntity;
import com.gamehub.entity.GameEntity;
import com.gamehub.entity.MatrixVariantEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameDAOImpl implements GameDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(GameDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	public void addGame(GameEntity p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(p);
		logger.info("Game saved successfully, Game Details="+p);
	}

	public void updateGame(GameEntity p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(p);
		logger.info("Game updated successfully, Game Details="+p);
	}

	@SuppressWarnings("unchecked")
	public List<GameEntity> listGames() {
		Session session = this.sessionFactory.getCurrentSession();
		List<GameEntity> personsList = session.createQuery("from GameEntity").list();
		for(GameEntity p : personsList){
			logger.info("Person List::"+p);
		}
		return personsList;
	}

	public GameEntity getGameById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		GameEntity p = (GameEntity) session.load(GameEntity.class, id);
		logger.info("Game loaded successfully, Game details="+p);
		return p;
	}

	public void removeGame(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		GameEntity p = (GameEntity) session.load(GameEntity.class, id);
		if(null != p){
			session.delete(p);
		}
		logger.info("Game deleted successfully, game details="+p);
	}

    public void removeGame(GameEntity game) {
        Session session = this.sessionFactory.getCurrentSession();
        session.delete(game);
    }

	public ChoiceEntity getChoiceById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		return (ChoiceEntity) session.load(ChoiceEntity.class, id);
	}

	public MatrixVariantEntity getVariantByPosition(String position) {
		Session session = this.sessionFactory.getCurrentSession();
		return (MatrixVariantEntity) session.createQuery("from MatrixVariantEntity where matrixPosition=position").list();
	}
}
