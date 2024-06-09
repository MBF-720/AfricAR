package pfe.africar.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import pfe.africar.R;

public class adapter_prof_abscence extends BaseAdapter {

    private Context context;
    private List<Eleve> students;
    private LayoutInflater inflater;

    public adapter_prof_abscence(Context context, List<Eleve> students) {
        this.context = context;
        this.students = students;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Object getItem(int position) {
        return students.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_student_attendance, parent, false);
            holder = new ViewHolder();
            holder.studentName = convertView.findViewById(R.id.studentName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Eleve student = students.get(position);
        holder.studentName.setText(student.getNom());

        return convertView;
    }

    static class ViewHolder {
        TextView studentName;
    }
}

