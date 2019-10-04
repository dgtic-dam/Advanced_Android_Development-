package mx.unam.tic.docencia.webserviceexample.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_movie.view.*
import mx.unam.tic.docencia.webserviceexample.R
import mx.unam.tic.docencia.webserviceexample.models.Search


class MovieAdapter (val context: Context,val search:ArrayList<Search>):
    RecyclerView.Adapter<MovieViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(LayoutInflater.from(
            context
        ).inflate(R.layout.item_movie,parent,false))
    }

    override fun getItemCount(): Int {
        return search.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.posterImageView.setImageResource(R.mipmap.ic_launcher)
        holder.titleTextView.text=search[position].title
        holder.yearTextView.text=search[position].year
    }
}

class MovieViewHolder(view: View):RecyclerView.ViewHolder(view){
    val posterImageView=view.posterImageView
    val titleTextView=view.titleTextView
    val yearTextView=view.yearTextView
}