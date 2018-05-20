        package com.sons_of_interaction.docall;

        import android.app.Activity;
        import android.content.Context;
        import android.graphics.Color;
        import android.graphics.PorterDuff;
        import android.support.constraint.ConstraintLayout;
        import android.support.v4.content.ContextCompat;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.TextView;

        import java.text.DateFormat;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.Date;

        import static com.sons_of_interaction.docall.activity.HomeActivity.orarioVisita;


        public class BooksAdapter extends ArrayAdapter {

    private ArrayList<Visit> visits;
    private Context context;

    public BooksAdapter(Activity context, ArrayList<Visit> visits){
        super(context,R.layout.fragment_list);
        this.visits=visits;
        this.context=context;
    }

    @Override
    public int getCount(){
        return visits.size();
    }

    @Override
    public Object getItem(int position){
        return visits.get(position);
    }

    @Override
    public long getItemId(int position){
        return getItem(position).hashCode();
    }

    @Override
    public View getView(int position,View v,ViewGroup vg){

        if(v == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.item, vg,false);
        }
        TextView mtextView = (TextView) v.findViewById(R.id.dataVisita);
        ConstraintLayout itemVisit = (ConstraintLayout) v.findViewById(R.id.itemVisit);
        itemVisit.setBackgroundColor(0x1100FF00);

        Date orarioCorrente = Calendar.getInstance().getTime();
        DateFormat format = new SimpleDateFormat("HH:mm");
        String orarioCurr = format.format(orarioCorrente);
        Date today = Calendar.getInstance().getTime();
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String oggi = f.format(today);
        if(orarioVisita != null){
            if(orarioCurr.compareTo(orarioVisita) <= 0 && oggi.equals(visits.get(position).getDate())){
                itemVisit.setBackgroundColor(0x44FF0000);
            }
        }
        mtextView.setText(visits.get(position).getDate());
        return v;
    }

}