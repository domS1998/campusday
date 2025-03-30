package org.server.orm.classes;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.server.api.JsonSerializable;
import org.server.orm.AbstractDAO;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table (name = "station")
@Entity
public class StationDAO extends AbstractDAO {

    @Id
    @Column(name = "id", nullable = false)
    private String station_id = UUID.randomUUID().toString();

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "room", nullable = false)
    private String room = "";

    @JsonBackReference
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserStationDao> userStations = new HashSet<>();

    public StationDAO(String name) {
        this.name = name;
    }
}