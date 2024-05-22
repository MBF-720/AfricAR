package pfe.africar.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

public class AbsenceAdapter extends ArrayAdapter<DocumentSnapshot> {
    private Context context;
    private List<DocumentSnapshot> absences;

    public AbsenceAdapter(Context context, List<DocumentSnapshot> absences) {
        super(context, 0, absences);
        this.context = context;
        this.absences = absences;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        DocumentSnapshot document = absences.get(position);
        String name = document.getString("nom");
        String prenom = document.getString("prenom");
        String classe = document.getString("classe");
        String date = document.getString("date");

        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(name + " " + prenom + " - " + classe + " - " + date);

        // Set the document as the tag for this view
        convertView.setTag(document);

        return convertView;
    }
}
