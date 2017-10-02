package org.effervescence.app17.recyclerview.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.effervescence.app17.R;
import org.effervescence.app17.models.Person;

import java.util.List;

/**
 * Created by odin on 2/10/17.
 */

public class CreditsRVAdapter extends RecyclerView.Adapter<CreditsRVAdapter.AdapterViewHolder>{

    private static final String TAG = CreditsRVAdapter.class.getSimpleName();
    private List<Person> eventsList;
    private Context context;


    public CreditsRVAdapter(Context context, List<Person> eventsList){
        this.eventsList = eventsList;
        this.context = context;
    }

     Person class_person = new Person();

    public class AdapterViewHolder extends RecyclerView.ViewHolder{

        public TextView personName;
        public TextView personContact;

        public AdapterViewHolder(View itemView) {
            super(itemView);

            personName = (TextView) itemView.findViewById(R.id.person_name);
            personContact = (TextView) itemView.findViewById(R.id.person_contact);
        }
    }

    @Override
    public AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        if(viewType == 0){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_item, parent, false);
        }
        return new AdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdapterViewHolder holder, int position) {
        Log.d(TAG, "#" + position);
        Event event = eventsList.get(position);
        holder.listItemEventView.setText(class_event.getEventName());
        holder.eventStartTime.setText(class_event.getStartTime());
        holder.hyphen.setText("-");
        holder.eventEndTime.setText(class_event.getEndTime());

    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }


}
