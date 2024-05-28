package pfe.africar.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import pfe.africar.R;

public class GradeAdapter extends ArrayAdapter<GradeInfo> {
    private Context context;
    private List<GradeInfo> gradeInfoList;

    public GradeAdapter(Context context, List<GradeInfo> gradeInfoList) {
        super(context, 0, gradeInfoList);
        this.context = context;
        this.gradeInfoList = gradeInfoList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grade_list_item, parent, false);
        }

        GradeInfo gradeInfo = getItem(position);

        TextView subjectNameTextView = convertView.findViewById(R.id.subjectName);
        TextView averageGradeTextView = convertView.findViewById(R.id.averageGrade);

        subjectNameTextView.setText(gradeInfo.getSubjectName());
        averageGradeTextView.setText(String.format("%.2f", gradeInfo.getAverageGrade()));

        return convertView;
    }
}
