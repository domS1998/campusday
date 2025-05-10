package org.server.orm;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.server.api.JsonSerializable;
import org.server.orm.classes.UserDAO;

public abstract class AbstractDAO extends JsonSerializable implements DAO {

    public void insert(){
        Session session = HibernateApi.getInstance().getSession();

        if (session == null || !session.isOpen()) {
            session = HibernateApi.getInstance().factory.openSession(); // implement this
        }
        Transaction tx = session.getTransaction();
        if (tx == null || !tx.isActive()) {
            tx = session.beginTransaction();
        }

        session.save(this); // in diesem objekt speichern

        tx.commit();
//        session.close();
    }

    // neueres Speichern
    public void persist() {
        Session session = HibernateApi.getInstance().getSession();

        if (session == null || !session.isOpen()) {
            session = HibernateApi.getInstance().factory.openSession(); // implement this
        }

        Transaction tx = session.getTransaction();
        if (tx == null || !tx.isActive()) {
            tx = session.beginTransaction();
        }

        session.persist(this);

        tx.commit();
//        session.close();
    }

    public void saveOrUpdate() {

        Session session = HibernateApi.getInstance().getSession();

        if (session == null || !session.isOpen()) {
            session = HibernateApi.getInstance().factory.openSession(); // implement this
        }

        Transaction tx = session.getTransaction();
        if (tx == null || !tx.isActive()) {
            tx = session.beginTransaction();
        }

        session.saveOrUpdate(this);

        tx.commit();
//        session.close();
    }

    // lädt Objekt nicht direkt, kann lazy loading nutzten
    //  Fehler, falls Objekt nicht in DB
    //  sinnvoll, wenn Objekt garantiert in DB
    public void load(Object primaryKey) {
//        try {

        Session session = HibernateApi.getInstance().getSession();

        if (session == null || !session.isOpen()) {
            session = HibernateApi.getInstance().factory.openSession(); // implement this
        }

        Transaction tx = session.getTransaction();
        if (tx == null || !tx.isActive()) {
            tx = session.beginTransaction();
        }

        session.load(this, primaryKey); // in diesem objekt speichern
        tx.commit();

//        session.close();

//        }
//        catch (Exception e) {
//            System.out.println("Rolling back transaction due to: " + e.getMessage());
//            tx.rollback();  // ✅ Ensure rollback is handled
//        }
    }

    // Sucht Objekt direkt in DB
    //  null, falls Objekt nicht gefunden, kein Fehler
    //  sinvoll, falls nicht sicher, ob Objekt in DB
    public static Object get(Class obj_class, Object primaryKey) {
        Session session = HibernateApi.getInstance().getSession();

        if (session == null || !session.isOpen()) {
            session = HibernateApi.getInstance().factory.openSession(); // implement this
        }

        Transaction tx = session.getTransaction();
        if (tx == null || !tx.isActive()) {
            tx = session.beginTransaction();
        }

        Object result = session.get(obj_class.getClass(), primaryKey); // in diesem objekt speichern

        tx.commit();
//        session.close();

        return result;
    }

    // Objekt in Cache / Persistence Context suchen anstatt aus der DB laden
    public static Object find(Class objClass, Object primaryKey) {
        Session session = HibernateApi.getInstance().getSession();

        if (session == null || !session.isOpen()) {
            session = HibernateApi.getInstance().factory.openSession(); // implement this
        }

        Transaction tx = session.getTransaction();
        if (tx == null || !tx.isActive()) {
            tx = session.beginTransaction();
        }

        Object result = session.find(objClass, primaryKey); // in diesem objekt speichern

        tx.commit();
//        session.close();

        return result;
    }

    // für assozierte, noch nicht gespeicherte (transient) Objekte
    public void update(){
        Session session = HibernateApi.getInstance().getSession();

        if (session == null || !session.isOpen()) {
            session = HibernateApi.getInstance().factory.openSession(); // implement this
        }

        Transaction tx = session.getTransaction();
        if (tx == null || !tx.isActive()) {
            tx = session.beginTransaction();
        }

        session.update(this);

        tx.commit();
//        session.close();
    }

    // für assozierte, bereits gepspeicherte Objekte zu updaten (persistente Objekte)
    public void merge() {
//        try {

        Session session = HibernateApi.getInstance().getSession();

        if (session == null || !session.isOpen()) {
            session = HibernateApi.getInstance().factory.openSession(); // implement this
        }

        Transaction tx = session.getTransaction();
        if (tx == null || !tx.isActive()) {
            tx = session.beginTransaction();
        }

        session.merge(this);

        tx.commit();
//        session.close();

//        }
//        catch (Exception e) {
//            System.out.println("Rolling back transaction due to: " + e.getMessage());
//            tx.rollback();  // ✅ Ensure rollback is handled
//        }
    }

    public void delete() {
//        try {

            Session session = HibernateApi.getInstance().getSession();

            if (session == null || !session.isOpen()) {
                session = HibernateApi.getInstance().factory.openSession(); // implement this
            }

            Transaction tx = session.getTransaction();
            if (tx == null || !tx.isActive()) {
                tx = session.beginTransaction();
            }

            session.remove(this);

            tx.commit();
//            session.close();
//        }
//        catch (Exception e) {
//            System.out.println("Rolling back transaction due to: " + e.getMessage());
//            tx.rollback();  // ✅ Ensure rollback is handled
//        }
    }
}



