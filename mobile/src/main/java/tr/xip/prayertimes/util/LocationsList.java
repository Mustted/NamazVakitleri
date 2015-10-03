package tr.xip.prayertimes.util;

import java.util.ArrayList;

import tr.xip.prayertimes.api.objects.Location;

public class LocationsList extends ArrayList<Location> {

    public Location findById(int id) {
        if (size() == 0) {
            return null;
        }

        if (id == -1) {
            return get(0);
        }

        Location result = null;
        for (Location l : this) {
            if (l.getDatabaseId() == id) {
                result = l;
                break;
            }
        }
        return result;
    }
}
