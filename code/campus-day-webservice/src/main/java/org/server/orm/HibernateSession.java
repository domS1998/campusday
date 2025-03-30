package org.server.orm;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSession {

    private final SessionFactory factory;

    public Session getSession() {
        return factory.getCurrentSession();
    }

    // singleton
    private static HibernateSession uniqueInstance = null;

    public static HibernateSession getInstance() {
        if (uniqueInstance == null) uniqueInstance = new HibernateSession();
        return uniqueInstance;
    }

    private HibernateSession() {

        this.factory = new Configuration()
                .configure("hibernate/hibernate.cfg.xml")
                .buildSessionFactory();

    }

}