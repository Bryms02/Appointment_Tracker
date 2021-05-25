package com.example.mobileappdevpa.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileappdevpa.Entity.TermEntity;
import com.example.mobileappdevpa.R;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {

    class TermViewHolder extends RecyclerView.ViewHolder {

        private final TextView termItemView;

        private TermViewHolder(View itemView) {
            super(itemView);
            termItemView = itemView.findViewById(R.id.termTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final TermEntity current = mTerms.get(position);
                    Intent intent = new Intent(context, TermDetailsActivity.class);
                    intent.putExtra("termName", current.getTermName());
                    intent.putExtra("termStartDate", current.getTermStartDate());
                    intent.putExtra("termEndDate", current.getTermEndDate());
                    intent.putExtra("termId", current.getTermId());
                    context.startActivity(intent);

                }
            });


        }

    }

    private List<TermEntity> mTerms;

    private final Context context;

    private final LayoutInflater mInflater;


    public TermAdapter(Context context)
    {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }


    @Override
    public TermViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_list_item, parent, false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermViewHolder holder, int position) {

        if(mTerms != null){
            final TermEntity current = mTerms.get(position);
            holder.termItemView.setText(current.getTermName());
        }
        else{
            holder.termItemView.setText("No Word");
        }

    }

    @Override
    public int getItemCount() {
        if(mTerms != null)
            return mTerms.size();
        else return 0;
    }

    public void setTerms(List<TermEntity> terms)
    {
        mTerms = terms;
        notifyDataSetChanged();
    }



}


