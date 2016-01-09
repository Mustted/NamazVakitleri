package tr.xip.prayertimes.util

import java.util.ArrayList

import tr.xip.prayertimes.model.Location

class LocationsList : ArrayList<Location>() {

    fun findById(id: Int): Location? {
        if (size == 0) {
            return null
        }

        if (id == -1) {
            return get(0)
        }

        var result: Location? = null
        for (item in this) {
            if (item.databaseId == id) {
                result = item
                break
            }
        }

        return result
    }
}
