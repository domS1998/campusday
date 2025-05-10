package org.server.orm;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateApi {

    public SessionFactory factory;

    public Session getSession() {
        // Session vorher schließen falls auf
        if (this.factory.getCurrentSession().isOpen()) {
            this.factory.getCurrentSession().close();
        }

        this.factory.openSession(); // jedes mal neue Session starten
        return factory.getCurrentSession();
    }

    // singleton
    private static HibernateApi uniqueInstance = null;

    public static HibernateApi getInstance() {
        if (uniqueInstance == null) uniqueInstance = new HibernateApi();
        return uniqueInstance;
    }

    private HibernateApi() {
        this.factory = new Configuration()
                .configure("hibernate/hibernate.cfg.xml")
                .buildSessionFactory();
    }

}