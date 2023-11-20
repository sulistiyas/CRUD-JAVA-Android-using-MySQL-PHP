package com.example.testcrudmysql.Class;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testcrudmysql.Activity.UpdateActivity;
import com.example.testcrudmysql.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<Result> results;

    public RecyclerViewAdapter(Context context, List<Result> results) {
        this.context = context;
        this.results = results;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent, false);
        ViewHolder holder = new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Result result = results.get(position);
        holder.textViewNPM.setText(result.getNpm());
        holder.textViewNama.setText(result.getNama());
        holder.textViewKelas.setText(result.getKelas());
        holder.textViewSesi.setText(result.getSesi());
    }


    @Override
    public int getItemCount() {
        return results.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final  TextView textViewNPM;
        private final  TextView textViewNama;
        private final  TextView textViewKelas;
        private final  TextView textViewSesi;
        public ViewHolder(View itemView) {
            super(itemView);
            textViewNPM = itemView.findViewById(R.id.textNPM);
            textViewNama = itemView.findViewById(R.id.textNama);
            textViewKelas = itemView.findViewById(R.id.textKelas);
            textViewSesi = itemView.findViewById(R.id.textSesi);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String npm = textViewNPM.getText().toString();
            String nama = textViewNama.getText().toString();
            String kelas = textViewKelas.getText().toString();
            String sesi = textViewSesi.getText().toString();

            Intent i = new Intent(context, UpdateActivity.class);
            i.putExtra("npm", npm);
            i.putExtra("nama", nama);
            i.putExtra("kelas", kelas);
            i.putExtra("sesi", sesi);
            context.startActivity(i);
        }

    }


}
