package com.odev.sinemaprojesi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.odev.sinemaprojesi.Models.MoviePlayer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;



public class TicketMenuFragment extends Fragment {
   View view;
   Button buy_ticket_button;
   ImageView film_image_menu;
   TextView film_name_tv_menu, film_director_tv_menu, film_writer_tv_menu, film_release_date_tv_menu, genre_tv_menu, actors_tv_menu;
   String name, director, image_id, film_id, writer, releaseDate, genre_str;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ticket_menu, container, false);

        buy_ticket_button = view.findViewById(R.id.buy_ticket_button);
        film_name_tv_menu = view.findViewById(R.id.film_name_tv_menu);
        film_director_tv_menu = view.findViewById(R.id.film_director_tv_menu);
        film_image_menu = view.findViewById(R.id.film_image_menu);
        film_writer_tv_menu = view.findViewById(R.id.film_writer_tv_menu);
        film_release_date_tv_menu = view.findViewById(R.id.film_release_date_tv_menu);
        genre_tv_menu = view.findViewById(R.id.genre_tv_menu);
        actors_tv_menu = view.findViewById(R.id.actors_tv_menu);

        Bundle bundle = getActivity().getIntent().getExtras();

        name = bundle.getString("name");
        director = bundle.getString("director");
        image_id = bundle.getString("imageid");
        film_id = bundle.getString("film_id");
        writer = bundle.getString("writer");
        releaseDate = bundle.getString("release_date");
        genre_str = bundle.getString("genre");
        int k = 0;
        List<MoviePlayer> art_list = new ArrayList<>();
        while(bundle.getString("cast"+k+"0") != null){
            String name = bundle.getString("cast"+k+"0");
            String character = bundle.getString("cast"+k+"1");
            MoviePlayer m = new MoviePlayer(name, character);
            art_list.add(m);
            k++;
        }
        for(MoviePlayer mp : art_list){
            actors_tv_menu.setText(actors_tv_menu.getText()+String.format("%15s -> %15s\n",mp.getName(),mp.getCharacter()));
        }

        String genre = "";
        if(genre_str.equals("Action Movies")) genre = "Aksiyon Filmi";
        else if(genre_str.equals("Love Movies")) genre = "Aşk Filmi";
        else if(genre_str.equals("Child Movies")) genre = "Çocuk Filmi";
        else genre = "Gerilim Filmi";

        film_name_tv_menu.setText        (String.format("%25s : %15s","Filmin Adı",name));
        film_director_tv_menu.setText    (String.format("%25s : %15s","Yönetmen",director));
        film_writer_tv_menu.setText      (String.format("%25s : %15s","Yazar",writer));
        film_release_date_tv_menu.setText(String.format("%25s : %15s","Vizyona Çıkış Tarihi",releaseDate));
        genre_tv_menu.setText            (String.format("%25s : %15s","Tür",genre));

        Picasso.with(getActivity()).load(image_id).into(film_image_menu);

        buy_ticket_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentLayoutTicket,new ChooseSessionFragment())
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();

            }
        });

        return view;
    }
}
