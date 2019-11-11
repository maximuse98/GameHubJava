package com.journaldev.spring.dao;

import java.util.List;

import com.journaldev.spring.model.Choice;
import com.journaldev.spring.model.Game;
import com.journaldev.spring.model.MatrixVariant;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class GameDAOImpl implements GameDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(GameDAOImpl.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@Override
	public void addGame(Game p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(p);
		logger.info("Game saved successfully, Game Details="+p);
	}

	@Override
	public void updateGame(Game p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(p);
		logger.info("Game updated successfully, Game Details="+p);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Game> listGames() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Game> personsList = session.createQuery("from Game").list();
		for(Game p : personsList){
			logger.info("Person List::"+p);
		}
		return personsList;
	}

	@Override
	public Game getGameById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Game p = (Game) session.load(Game.class, id);
		logger.info("Game loaded successfully, Game details="+p);
		return p;
	}

	@Override
	public void removeGame(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Game p = (Game) session.load(Game.class, id);
		if(null != p){
			session.delete(p);
		}
		logger.info("Game deleted successfully, game details="+p);
	}

    @Override
    public void removeGame(Game game) {
        Session session = this.sessionFactory.getCurrentSession();
        session.delete(game);
    }

    @Override
	public Choice getChoiceById(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		return (Choice) session.load(Choice.class, id);
	}

	@Override
	public MatrixVariant getVariantByPosition(String position) {
		Session session = this.sessionFactory.getCurrentSession();
		return (MatrixVariant) session.createQuery("from MatrixVariant where matrixPosition=position").list();
	}
}
