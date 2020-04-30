package com.odev.sinemaprojesi.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.odev.sinemaprojesi.AddNewFilmActivity;
import com.odev.sinemaprojesi.Models.Film;
import com.odev.sinemaprojesi.Models.MoviePlayer;
import com.odev.sinemaprojesi.R;
import com.odev.sinemaprojesi.TicketActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import static com.odev.sinemaprojesi.LauncherActivity.state;
import static com.odev.sinemaprojesi.MainActivity.film_selected;

import java.util.List;

public class FilmsAdapter extends RecyclerView.Adapter<FilmsAdapter.define> {

    Context context;
    List<Film> list;
    Activity activity;
    public static String selected_film_id;
    FirebaseDatabase database;
    DatabaseReference reference;

    public FilmsAdapter(List<Film> list, Context context, Activity activity) {
        this.context = context;
        this.list = list;
        this.activity = activity;
        database = FirebaseDatabase.getInstance();
    }


    @Override
    public define onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.filmsadapter_layout,parent,false);

        return new define(view);
    }

    @Override
    public void onBindViewHolder(define holder, final int position) {
        holder.name_tv.setText("Filmin Adı: " + list.get(position).getFilm_name());
        holder.director_tv.setText(list.get(position).getDirector_name());
        Picasso.with(activity).load(list.get(position).getFilmImage_id()).into(holder.img);
        if(state == 0){
            holder.edit_button.setVisibility(View.GONE);
            holder.delete_button.setVisibility(View.GONE);
        }
        holder.edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, AddNewFilmActivity.class);
                intent.putExtra("film_id",list.get(position).getFilm_id());
                intent.putExtra("name", list.get(position).getFilm_name());
                intent.putExtra("director", list.get(position).getDirector_name());
                intent.putExtra("genre",list.get(position).getGenre());
                intent.putExtra("writer", list.get(position).getWriter_name());
                intent.putExtra("release_date", list.get(position).getReleaseDate());
                intent.putExtra("imageid", list.get(position).getFilmImage_id());
                int k = 0;
                for(MoviePlayer moviePlayer : list.get(position).getMovie_cast()){
                    intent.putExtra("cast"+k+"0", moviePlayer.getName());  //TODO : Obje olarak yollanabilmeli.
                    intent.putExtra("cast"+k+"1", moviePlayer.getCharacter());
                    k++;
                }
                intent.putExtra("edit_or_add", "1");

                activity.startActivity(intent);
            }
        });
        holder.delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
                alertDialog.setTitle("Uyarı");
                alertDialog.setMessage("Bu filmi silmek istediğinizden emin misiniz?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Evet",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                reference = database.getReference("Films/"+list.get(position).getGenre()+"/"+list.get(position).getFilm_id());
                                reference.removeValue();

                                list.remove(list.get(position));
                                //Intent intent = new Intent(activity, MainActivity.class);

                                //activity.startActivity(intent); Realtime olduğu için bunun gereği yok...


                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Hayır",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();

            }
        });


        //holder.img.setImageResource(list.get(position).getFilmImage_id()); //TODO resim için bakılacak. Sunucudan aldığıma göre String vermeliyim.
        holder.film_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_film_id = list.get(position).getFilm_id();
                film_selected = list.get(position).getFilm_name();

                Intent intent = new Intent(activity, TicketActivity.class);
                intent.putExtra("film_id",list.get(position).getFilm_id());
                intent.putExtra("name", list.get(position).getFilm_name());
                intent.putExtra("director", list.get(position).getDirector_name());
                intent.putExtra("imageid", list.get(position).getFilmImage_id());
                intent.putExtra("genre",list.get(position).getGenre());
                intent.putExtra("writer", list.get(position).getWriter_name());
                intent.putExtra("release_date", list.get(position).getReleaseDate());
                int k = 0;
                for(MoviePlayer moviePlayer : list.get(position).getMovie_cast()){
                    intent.putExtra("cast"+k+"0", moviePlayer.getName());  //TODO : Obje olarak yollanabilmeli.
                    intent.putExtra("cast"+k+"1", moviePlayer.getCharacter());
                    k++;
                }
                activity.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class define extends RecyclerView.ViewHolder
    {
        ImageView img;
        TextView director_tv, name_tv;
        LinearLayout film_layout;
        Button edit_button, delete_button;

        public define(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.film_image);
            name_tv = itemView.findViewById(R.id.film_name);
            director_tv = (TextView) itemView.findViewById(R.id.film_director_tv);
            film_layout = itemView.findViewById(R.id.film_layout);
            edit_button = itemView.findViewById(R.id.edit_button);
            delete_button = itemView.findViewById(R.id.delete_button);
        }
    }

}
