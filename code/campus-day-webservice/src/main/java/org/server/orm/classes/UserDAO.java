package org.server.orm.classes;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.server.api.JsonSerializable;
import org.server.api.restcontroller.user.RegisterUserController;
import org.server.exceptions.*;
import org.server.orm.AbstractDAO;
import org.server.orm.HibernateSession;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table (name = "users")
@Entity
public class UserDAO extends AbstractDAO {

    private @Column(name = "id", nullable = false) @Id String id = UUID.randomUUID().toString();
    private @Column(name = "username", nullable = false, unique = true) String username;
    private @Column(name = "card_rfid", nullable = false, unique = true) String cardRfid;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserStationDao> userStations = new HashSet<>();

    public UserDAO(String username) {
        this.username = username;
    }


    public UserDAO(String username, String cardRfid) {
        this.username = username;
        this.cardRfid = cardRfid;
    }


}