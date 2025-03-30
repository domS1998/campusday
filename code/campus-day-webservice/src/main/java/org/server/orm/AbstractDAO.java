package org.server.orm;

import org.hibernate.Session;
import org.server.api.JsonSerializable;
import org.server.exceptions.*;

public abstract class AbstractDAO extends JsonSerializable implements DAO {

    public void insert(){
        if ( ! HibernateSession.getInstance().getSession().getTransaction().isActive()) {
            HibernateSession.getInstance().getSession().beginTransaction();
        }
        Session session = HibernateSession.getInstance().getSession();
        session.save(this); // in diesem objekt speichern
        session.flush();
        session.getTransaction().commit();
//        session.flush();
    }

    // neueres Speichern
    public void persist() {
        if ( ! HibernateSession.getInstance().getSession().getTransaction().isActive()) {
            HibernateSession.getInstance().getSession().beginTransaction();
        }
        Session session = HibernateSession.getInstance().getSession();
        session.persist(this);
        session.getTransaction().commit();
//        session.flush();
    }

    public void saveOrUpdate() {
        if ( ! HibernateSession.getInstance().getSession().getTransaction().isActive()) {
            HibernateSession.getInstance().getSession().beginTransaction();
        }
        Session session = HibernateSession.getInstance().getSession();
        session.saveOrUpdate(this);
        session.getTransaction().commit();
//        session.flush();
    }

    // lädt Objekt nicht direkt, kann lazy loading nutzten
    //  Fehler, falls Objekt nicht in DB
    //  sinnvoll, wenn Objekt garantiert in DB
    public void load(Object primaryKey) {
        if ( ! HibernateSession.getInstance().getSession().getTransaction().isActive()) {
            HibernateSession.getInstance().getSession().beginTransaction();
        }
        Session session = HibernateSession.getInstance().getSession();
        session.load(this, primaryKey); // in diesem objekt speichern
        session.getTransaction().commit();
//        session.flush();
    }

    // Sucht Objekt direkt in DB
    //  null, falls Objekt nicht gefunden, kein Fehler
    //  sinvoll, falls nicht sicher, ob Objekt in DB
    public static Object get(Class obj_class, Object primaryKey) {
        if ( ! HibernateSession.getInstance().getSession().getTransaction().isActive()) {
            HibernateSession.getInstance().getSession().beginTransaction();
        }
        Session session = HibernateSession.getInstance().getSession();
        Object result = session.get(obj_class.getClass(), primaryKey); // in diesem objekt speichern
        session.getTransaction().commit();
//        session.flush();
        return result;
    }

    public static Object find(Class obj_class, Object primaryKey) {
        if ( ! HibernateSession.getInstance().getSession().getTransaction().isActive()) {
            HibernateSession.getInstance().getSession().beginTransaction();
        }
        Session session = HibernateSession.getInstance().getSession();
        Object result = session.find(obj_class.getClass(), primaryKey); // in diesem objekt speichern
        session.getTransaction().commit();
//        session.flush();
        return result;
    }

    // für assozierte, noch nicht gespeicherte (transient) Objekte
    public void update(){
        if ( ! HibernateSession.getInstance().getSession().getTransaction().isActive()) {
            HibernateSession.getInstance().getSession().beginTransaction();
        }
        Session session = HibernateSession.getInstance().getSession();
        session.update(this);
        session.flush();
        session.getTransaction().commit();
//        session.flush();
    }

    // für assozierte, bereits gepspeicherte Objekte zu updaten (persistente Objekte)
    public void merge() {
        if ( ! HibernateSession.getInstance().getSession().getTransaction().isActive()) {
            HibernateSession.getInstance().getSession().beginTransaction();
        }
        Session session = HibernateSession.getInstance().getSession();
        session.merge(this);
        session.getTransaction().commit();
//        session.flush();
    }

    public void delete(){
        if ( ! HibernateSession.getInstance().getSession().getTransaction().isActive()) {
            HibernateSession.getInstance().getSession().beginTransaction();
        }
        Session session = HibernateSession.getInstance().getSession();
        session.remove(this);
        session.getTransaction().commit();
//        session.flush();
    }
}



