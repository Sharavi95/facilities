package info.androidhive.loginandregistration.sch;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import info.androidhive.loginandregistration.R;

/**
 * Created by shara on 4/18/2017.
 */

public class ScheduleAdapter extends ArrayAdapter<Schedule> {

    private static final String LOG_TAG = ScheduleAdapter.class.getSimpleName();


    public ScheduleAdapter(Activity context, ArrayList<Schedule> schedule) {
        super(context, 0, schedule);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link Schedule} object located at this position in the list
        Schedule currentAndroidFlavor = getItem(position);

        TextView nameTextView = (TextView) listItemView.findViewById(R.id.session_name);
        nameTextView.setText(currentAndroidFlavor.getSessionName());

        TextView numberTextView = (TextView) listItemView.findViewById(R.id.session_time);
        numberTextView.setText(currentAndroidFlavor.getSessionTime());

        // Return the whole list item layout (containing 2 TextViews)
        // so that it can be shown in the ListView
        return listItemView;
    }

}
