package tr.xip.prayertimes.ui.apdater;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import tr.xip.prayertimes.R;
import tr.xip.prayertimes.api.objects.Location;
import tr.xip.prayertimes.util.LocationsList;

public class LocationsSpinnerAdapter extends ArrayAdapter<Location> {

    private LocationsList mDataset = new LocationsList();

    public LocationsSpinnerAdapter(Context context, int resource, LocationsList objects) {
        super(context, resource, objects);
        mDataset = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = super.getView(position, convertView, parent);
        }

        TextView text = (TextView) convertView.findViewById(R.id.item_spinner_toolbar_selected_text);
        text.setText(mDataset.get(position).getName());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = super.getDropDownView(position, convertView, parent);
        }

        TextView text = (TextView) convertView.findViewById(R.id.item_spinner_toolbar_text);
        text.setText(mDataset.get(position).getName());
        return convertView;
    }
}
