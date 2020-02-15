package com.gamehub.dao;

import java.util.List;

import com.gamehub.model.Choice;
import com.gamehub.model.Game;
import com.gamehub.model.MatrixVariant;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GameDAOImpl implements GameDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(GameDAOImpl.class);

	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	public void addGame(Game p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(p);
		logger.info("Game saved successfully, Game Details="+p);
	}

	public void updateGame(Game p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(p);
		logger.info("Game updated successfully, Game Details="+p);
	}

	@SuppressWarnings("unchecked")
	public List<Game> listGames() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Game> personsList = session.createQuery("from Game").list();
		for(Game p : personsList){
			logger.info("Person List::"+p);
		}
		return personsList;
	}

	public Game getGameById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Game p = (Game) session.load(Game.class, id);
		logger.info("Game loaded successfully, Game details="+p);
		return p;
	}

	public void removeGame(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Game p = (Game) session.load(Game.class, id);
		if(null != p){
			session.delete(p);
		}
		logger.info("Game deleted successfully, game details="+p);
	}

    public void removeGame(Game game) {
        Session session = this.sessionFactory.getCurrentSession();
        session.delete(game);
    }

	public Choice getChoiceById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		return (Choice) session.load(Choice.class, id);
	}

	public MatrixVariant getVariantByPosition(String position) {
		Session session = this.sessionFactory.getCurrentSession();
		return (MatrixVariant) session.createQuery("from MatrixVariant where matrixPosition=position").list();
	}
}
