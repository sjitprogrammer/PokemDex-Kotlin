package com.aodev.pokemdex.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aodev.pokemdex.R
import com.aodev.pokemdex.listener.OnItemClickListener
import com.aodev.pokemdex.network.data.Pokemon
import com.bumptech.glide.Glide

class PokemonItemsAdapter(
    val items: List<Pokemon>,
    val context: Context,
    val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<PokemonItemsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.imageView.transitionName = "image_${item.id}"
        val imageUrl: String = "https://pokeres.bastionbot.org/images/pokemon/${item.id}.png"
        Glide.with(context)
            .load(imageUrl)
            .placeholder(R.drawable.placeholder_pokeball)
            .into(holder.imageView);
        holder.text_title.text = item.name
//        holder.text_number.text = "# " + item.id.toString()

        holder.itemView.setOnClickListener {
            onItemClickListener.onClickedItem(it,item.id,position)
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val text_title: TextView = view.findViewById(R.id.textView_title)
//        val text_number: TextView = view.findViewById(R.id.textView_number)


    }
}