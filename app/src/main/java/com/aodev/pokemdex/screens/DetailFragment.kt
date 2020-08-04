package com.aodev.pokemdex.screens

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.aodev.pokemdex.R
import com.aodev.pokemdex.data.TypeAdapter
import com.aodev.pokemdex.network.data.Ability
import com.aodev.pokemdex.network.data.Pokemon
import com.aodev.pokemdex.network.data.Stat
import com.aodev.pokemdex.network.data.Type
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {
    val listItems: ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val transition =
            TransitionInflater.from(activity).inflateTransition(R.transition.change_bounds)
        sharedElementEnterTransition = transition
        sharedElementReturnTransition = transition
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var item: Pokemon = arguments?.getParcelable("item_args")!!
        val pokemonType = item.types;
        val abilities = item.abilities
        val stats = item.stats

        setPokemonImage(item)
        setPokemonDetail(item)
        setPokemonAbilities(abilities)
        makePokemonType(pokemonType)
        setPokemonStat(stats)
        setType()
        imageView_back.setOnClickListener {
            getActivity()?.onBackPressed();
        }
    }

    private fun setPokemonAbilities(abilities: List<Ability>) {
        abilities.forEach {
            val row = it.ability.name
            text_abilities.append("${row} ")
        }
    }

    private fun setPokemonDetail(item: Pokemon) {
        textView_number.text = "Number : " + item.id.toString()
        textView_name.text = item.name
//        Log.e("test","height : ${item.height} = "+(item.height/10))
        text_height.text = (item.height * 0.1).toFloat().toString() + " m"
        text_weight.text = item.weight.toString() + " lbs"
        text_category.text = "-"

    }

    private fun setPokemonImage(item:Pokemon) {
        imageView_icon.transitionName = "image_${item.id}"
        val imageUrl: String = "https://pokeres.bastionbot.org/images/pokemon/${item.id}.png"
        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.placeholder_pokeball)
            .into(imageView_icon);
    }

    private fun makePokemonType(pokemonType: List<Type>) {
        pokemonType.forEach {
            val slot = it.type;
            listItems.add(slot.name)
            Log.e("Detailfragment", "" + listItems.size)
            recyclerview_type.adapter?.notifyDataSetChanged()
        }
    }

    private fun setPokemonStat(stats: List<Stat>) {
        var total = 0;
        stats.forEach {
            val stat = it.stat.name
            val value = it.baseStat
            total += value
            if (stat.equals("hp")) {
                text_hp.text = value.toString()
            } else if (stat.equals("attack")) {
                text_attack.text = value.toString()
            } else if (stat.equals("attack")) {
                text_attack.text = value.toString()
            } else if (stat.equals("defense")) {
                text_defense.text = value.toString()
            } else if (stat.equals("special-attack")) {
                text_sp_atk.text = value.toString()
            } else if (stat.equals("special-defense")) {
                text_sp_def.text = value.toString()
            } else if (stat.equals("speed")) {
                text_speed.text = value.toString()
            }
        }
        text_total.text = total.toString()
    }

    private fun setType() {
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerview_type.layoutManager = linearLayoutManager
        val adapter = TypeAdapter(requireContext(), listItems)
        recyclerview_type.adapter = adapter
    }
}