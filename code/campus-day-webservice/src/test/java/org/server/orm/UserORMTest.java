package org.server.orm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.server.exceptions.DuplicateUserException;import org.server.exceptions.NoSuchUserException;
import org.server.exceptions.SaveObjectException;
import org.server.orm.classes.UserDAO;

public class UserORMTest {

    @Test
    public void testCreateUser_testusers(){

//        for (int i = 0; i < 10; i++) {
//
//            // User erstellen
//            UserDAO orm = new UserDAO();
//            orm.setUsername("testuser"+i);
//            orm.setPassword(i+"");
//            // ORM Objekt 2 um gespeicherten User wieder zu laden
//            UserDAO ormLoaded = new UserDAO();
//            try {
//                orm.insert();
//                ormLoaded.load("testuser"+i);
//            }
//            catch (SaveObjectException | DuplicateUserException e) {
//                Assertions.fail(e);
//            }
//            if ((!ormLoaded.getUsername().equals(orm.getUsername()))
//                    || (!ormLoaded.getPassword().equals(orm.getPassword()))){
//                Assertions.fail();
//            }
//            System.out.println(orm);
//        }
    }

    @Test
    public void testLoadUsers() {

        for (int i = 0; i < 10; i++) {

            UserDAO orm = new UserDAO();
            try {
                orm.load("testuser"+i);
                System.out.println(orm);
            }
            catch (Exception e) {
                Assertions.fail();
            }
        }
    }
}
