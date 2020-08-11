package com.aodev.pokemdex.screens.detail

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.aodev.pokemdex.R
import com.aodev.pokemdex.data.TypeAdapter
import com.aodev.pokemdex.databinding.FragmentDetailBinding
import com.aodev.pokemdex.network.data.Ability
import com.aodev.pokemdex.network.data.Pokemon
import com.aodev.pokemdex.network.data.Stat
import com.aodev.pokemdex.network.data.Type
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {
    val listItems: ArrayList<String> = ArrayList()
    private lateinit var viewModel: DetailViewModel
    private lateinit var viewModelFactory: DetailViewModelFactory
    private lateinit var binding:FragmentDetailBinding

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

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_detail,
            container,
            false
        )
        val pokemon: Pokemon = arguments?.getParcelable("item_args")!!
        viewModelFactory = DetailViewModelFactory(pokemon)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(DetailViewModel::class.java)
        binding.detailViewModel = viewModel
        binding.setLifecycleOwner(this)

        setPokemonName()
        setPokemonImage()
        setPokemonAbilities()
        makePokemonType()
        setType()
        setPokemonStat()

        binding.imageViewBack.setOnClickListener {
            getActivity()?.onBackPressed();
        }
        return binding.root
    }
    private fun setPokemonName(){
        binding.textViewName.text = viewModel.pokemonName().capitalize()
    }
    private fun setPokemonAbilities() {
        val abilities = viewModel.pokemonAbility()
        abilities.forEach {
            val row = it.ability.name.capitalize()
            binding.textAbilities.append("${row} ")
        }
    }

    private fun setPokemonImage() {
        val pokemonID = viewModel.getPokemonId()
        binding.imageViewIcon.transitionName = "image_${pokemonID}"
        val imageUrl: String = "https://pokeres.bastionbot.org/images/pokemon/${pokemonID}.png"
        Glide.with(binding.root)
            .load(imageUrl)
            .placeholder(R.drawable.placeholder_pokeball)
            .into(binding.imageViewIcon);
    }

    private fun makePokemonType() {
        val pokemonType = viewModel.pokemonType()
        pokemonType.forEach {
            val slot = it.type;
            listItems.add(slot.name)
            binding.recyclerviewType.adapter?.notifyDataSetChanged()
        }
    }

    private fun setPokemonStat() {
        binding.textHp.text = viewModel.hp.toString()
        binding.textAttack.text = viewModel.attack.toString()
        binding.textDefense.text = viewModel.defense.toString()
        binding.textSpAtk.text = viewModel.specialAttack.toString()
        binding.textSpDef.text = viewModel.specialDefense.toString()
        binding.textSpeed.text = viewModel.speed.toString()
        binding.textTotal.text = viewModel.totalStat.toString()
    }

    private fun setType() {
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerviewType.layoutManager = linearLayoutManager
        val adapter = TypeAdapter(requireContext(), listItems)
        binding.recyclerviewType.adapter = adapter
    }
}