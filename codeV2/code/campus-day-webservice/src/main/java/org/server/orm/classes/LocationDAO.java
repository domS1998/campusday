package org.server.orm.classes;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// Objekt für Plazierung einer Station auf den Gebäudeplanbildern
// Jeder Station wird ein Gebäudeplanbild und Koordinaten zugeordnet,
// mit der die Stationen im UI beliebig plaziert werden können.
public class LocationDAO {

    // Labels für die verschiendenen Gebäudeplanbilder im UI
    public enum FloorPlanImage {
        G20, G21, G22
    }

    // x Koordinate der Station auf dem Gebäudeplanbild
    @Enumerated(EnumType.STRING)
    @Column(name = "floorplan_image", nullable = false)
    private FloorPlanImage image;

    // x Koordinate der Station auf dem Gebäudeplanbild
    @Column(name = "x_coordinate", nullable = false)
    private int xCoordinate = 0;

    // y Koordinate der Station auf dem Gebäudeplanbild
    @Column(name = "y_coordinate", nullable = false)
    private int yCoordinate = 0;
}
