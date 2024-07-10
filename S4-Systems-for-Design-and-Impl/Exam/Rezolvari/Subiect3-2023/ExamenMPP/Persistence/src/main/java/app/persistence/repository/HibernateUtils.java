package app.persistence.repository;

import app.model.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory(){
        if ((sessionFactory == null) || (sessionFactory.isClosed()))
            sessionFactory = createNewSessionFactory();
        return sessionFactory;
    }

    private static SessionFactory createNewSessionFactory() {
        sessionFactory = new Configuration()
                .addAnnotatedClass(Game.class)
                .addAnnotatedClass(app.model.Configuration.class)
                .addAnnotatedClass(Player.class)
                .addAnnotatedClass(Position.class)
                .addAnnotatedClass(Word.class)
                .addAnnotatedClass(ConfigurationWord.class)
                .buildSessionFactory();
        return sessionFactory;
    }

    public static void closeSessionFactory(){
        if (sessionFactory != null)
            sessionFactory.close();
    }
}
