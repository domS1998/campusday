package org.server.orm;

import org.hibernate.Session;
import org.server.api.JsonSerializable;
import org.server.exceptions.*;

public abstract class AbstractDAO extends JsonSerializable implements DAO {

    public void insert() throws ChatWithUserException, DuplicateChatExeption, SaveObjectException, NoSuchUserException {
        Session session = HibernateSession.getInstance().getSession();
        session.beginTransaction();
        session.save(this); // in diesem objekt speichern
        session.flush();
        session.getTransaction().commit();
    }

    // neueres Speichern
    public void persist() {
        Session session = HibernateSession.getInstance().getSession();
        session.beginTransaction();
        session.persist(this);
        session.getTransaction().commit();
    }

    public void saveOrUpdate() {
        Session session = HibernateSession.getInstance().getSession();
        session.beginTransaction();
        session.saveOrUpdate(this);
        session.getTransaction().commit();
    }

    // lädt Objekt nicht direkt, kann lazy loading nutzten
    //  Fehler, falls Objekt nicht in DB
    //  sinnvoll, wenn Objekt garantiert in DB
    public void load(Object primaryKey) {
        Session session = HibernateSession.getInstance().getSession();
        session.beginTransaction();
        session.load(this, primaryKey); // in diesem objekt speichern
        session.getTransaction().commit();
    }

    // Sucht Objekt direkt in DB
    //  null, falls Objekt nicht gefunden, kein Fehler
    //  sinvoll, falls nicht sicher, ob Objekt in DB
    public static Object get(Class obj_class, Object primaryKey) {
        Session session = HibernateSession.getInstance().getSession();
        session.beginTransaction();
        Object result = session.get(obj_class.getClass(), primaryKey); // in diesem objekt speichern
        session.getTransaction().commit();
        return result;
    }

    public static Object find(Class obj_class, Object primaryKey) {
        Session session = HibernateSession.getInstance().getSession();
        session.beginTransaction();
        Object result = session.find(obj_class.getClass(), primaryKey); // in diesem objekt speichern
        session.getTransaction().commit();
        return result;
    }

    // für assozierte, noch nicht gespeicherte (transient) Objekte
    public void update() throws NoSuchChatException {
        Session session = HibernateSession.getInstance().getSession();
        session.beginTransaction();
        session.update(this);
        session.flush();
        session.getTransaction().commit();
    }

    // für assozierte, bereits gepspeicherte Objekte zu updaten (persistente Objekte)
    public void merge() {
        Session session = HibernateSession.getInstance().getSession();
        session.beginTransaction();
        session.merge(this);
        session.getTransaction().commit();
    }

    public void delete() throws NoSuchChatException {
        Session session = HibernateSession.getInstance().getSession();
        session.beginTransaction();
        session.remove(this);
        session.getTransaction().commit();
    }
}



