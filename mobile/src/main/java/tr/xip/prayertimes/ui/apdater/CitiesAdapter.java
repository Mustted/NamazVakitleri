package tr.xip.prayertimes.ui.apdater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import tr.xip.prayertimes.R;
import tr.xip.prayertimes.api.objects.City;

public class CitiesAdapter extends ArrayAdapter<City> {
    private Context mContext;

    private List<City> mItems;

    private int mSelectedPosition = 0;

    public CitiesAdapter(Context context, int resource, List<City> items) {
        super(context, resource, items);
        this.mContext = context;
        this.mItems = items;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_radio, parent, false);
        }

        City item = mItems.get(position);

        TextView mName = (TextView) convertView.findViewById(R.id.item_radio_country_name);
        RadioButton mRadioButton = (RadioButton) convertView.findViewById(R.id.item_radio_country_radio);

        mName.setText(item.getName());

        mRadioButton.setChecked(position == mSelectedPosition);
        mRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItem(position);
            }
        });

        return convertView;
    }

    public void selectItem(int position) {
        mSelectedPosition = position;
        notifyDataSetInvalidated();
    }

    public City getSelectedCity() {
        return mItems.get(mSelectedPosition);
    }
}
