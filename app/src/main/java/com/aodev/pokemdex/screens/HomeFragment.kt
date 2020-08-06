package com.aodev.pokemdex.screens

import android.animation.Animator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.aodev.pokemdex.R
import com.aodev.pokemdex.data.PokemonItemsAdapter
import com.aodev.pokemdex.listener.HidingScrollListener
import com.aodev.pokemdex.listener.HomeListener
import com.aodev.pokemdex.listener.OnItemClickListener
import com.aodev.pokemdex.network.data.Pokemon
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_row.view.*
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment(), OnItemClickListener, HomeListener {

    val listItem:ArrayList<Pokemon> = ArrayList()
    val tempItem:ArrayList<Pokemon> = ArrayList()
    lateinit var imageView2:ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageView2 = view.findViewById(R.id.imageView2)
        val viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.homeListener = this
        if(tempItem.size==0) {
            refreshLayout.isRefreshing = true
        }
        showData(tempItem)
        refreshLayout.setOnRefreshListener {
            listItem.clear()
            tempItem.clear()
            viewModel.fetchAllPokemon()
        }
        viewModel._pokemonList.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

            if(it.size>=151){
                viewModel.LoadPokemonFinish()
                listItem.clear()
                tempItem.clear()
                listItem.addAll(it)
                tempItem.addAll(it)
                recyclerview.adapter?.notifyDataSetChanged()
            }
        })
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText : String?): Boolean {

                if(newText!!.isNotEmpty()){
                    tempItem.clear()
                    val search = newText.toLowerCase(Locale.getDefault())

                    listItem.forEach{
                        if(it.name?.toLowerCase(Locale.getDefault())?.contains(search)!!){
                            tempItem.add(it)
                        }
                    }
                    recyclerview.adapter?.notifyDataSetChanged()
                }else{
                    tempItem.clear()
                    tempItem.addAll(listItem)
                    recyclerview.adapter?.notifyDataSetChanged()
                }
                return true
            }

        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }


    private fun showData(item:List<Pokemon>){
        val gridLayoutManager = GridLayoutManager(requireContext(),2)
        recyclerview.layoutManager = gridLayoutManager
        recyclerview.adapter = PokemonItemsAdapter(item,requireContext(),this)
        recyclerview.apply {
            postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
        }
        recyclerview.addOnScrollListener(object : HidingScrollListener() {
            override fun onHide() {
                hideViews()
            }

            override fun onShow() {

            }

            override fun onFirstItem() {
                showViews()
            }
        })
    }

    private fun hideViews() {

        val removeheight = imageView2.height+50
        imageView2.animate().translationY((-removeheight).toFloat())
            .setDuration(500)
            .setInterpolator(AccelerateInterpolator(2F))
            .setListener(object: Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    imageView2.visibility = View.GONE
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }

            }).start()


    }

    private fun showViews() {

        imageView2.animate().translationY(0F)
            .setDuration(1000)
            .setInterpolator(DecelerateInterpolator(2F))
            .setListener(object: Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    imageView2.visibility = View.VISIBLE
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                    imageView2.visibility = View.VISIBLE
                }

            }).start()
        searchView.animate().translationY(0F).setDuration(1000).setInterpolator(DecelerateInterpolator(2F)).start()

    }

    override fun onClickedItem(view: View,id:Int,position: Int) {
        var item_args = tempItem[position]
        val number = tempItem[position].id
        val bundle = bundleOf("item_args" to item_args)
//        Log.e("HomeFragment","image_$number")
        val extras = FragmentNavigatorExtras(
            view.imageView to "image_$number"
        )
        findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle,null,extras)
    }

    override fun fetchAllPokemon() {
        refreshLayout.isRefreshing = true
//        progressBar.show()
    }

    override fun onSuccess() {
//        progressBar.hide()
        refreshLayout.isRefreshing = false
    }

    override fun onFailure(message: String) {
//        progressBar.hide()
        refreshLayout.isRefreshing = false
        Toast.makeText(requireContext(),message,Toast.LENGTH_LONG).show()
    }


}