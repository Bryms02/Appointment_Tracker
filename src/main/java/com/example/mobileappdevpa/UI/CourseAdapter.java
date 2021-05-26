package com.example.mobileappdevpa.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileappdevpa.Entity.CourseEntity;
import com.example.mobileappdevpa.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {



    class CourseViewHolder extends RecyclerView.ViewHolder{

        private final TextView courseItemView;

        private CourseViewHolder(View itemView){
            super(itemView);
            courseItemView = itemView.findViewById(R.id.courseTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final CourseEntity current = mCourses.get(position);
                    Intent intent = new Intent(context, CourseActivity.class);
                    intent.putExtra("courseId", current.getCourseId());
                    intent.putExtra("courseTitle", current.getCourseTitle());
                    intent.putExtra("courseStartDate", current.getCourseStartDate());
                    intent.putExtra("courseEndDate", current.getCourseEndDate());
                    intent.putExtra("instructorName", current.getInstructorName());
                    intent.putExtra("instructorPhone", current.getInstructorPhone());
                    intent.putExtra("instructorEmail", current.getInstructorEmail());
                    intent.putExtra("optionalNote", current.getOptionalNote());
                    intent.putExtra("status", current.getStatus());
                    intent.putExtra("termId", current.getTermId());
                    context.startActivity(intent);

                }
            });

        }



    }

    private List<CourseEntity> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;

    public CourseAdapter(Context context)
    {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }


    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_list_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        if(mCourses != null){
            final CourseEntity current = mCourses.get(position);
            holder.courseItemView.setText(current.getCourseTitle());
        }
        else{
            holder.courseItemView.setText("No Word");
        }

    }

    @Override
    public int getItemCount() {
        if(mCourses != null)
            return mCourses.size();

        else return 0;
    }

    public void setCourse(List<CourseEntity> courses)
    {
        mCourses = courses;
        notifyDataSetChanged();
    }



}
