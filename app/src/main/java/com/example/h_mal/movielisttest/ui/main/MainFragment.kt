package com.example.h_mal.movielisttest.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.h_mal.movielisttest.R
import com.example.h_mal.movielisttest.utils.*
import kotlinx.android.synthetic.main.empty_view_item.view.*
import kotlinx.android.synthetic.main.main_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class MainFragment : Fragment(), KodeinAware {
    override val kodein by kodein()
    private val factory by instance<MainViewModelFactory>()

    private val viewModel by viewModels<MainViewModel> { factory }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.operationState.observe(viewLifecycleOwner, stateObserver)
        viewModel.operationError.observe(viewLifecycleOwner, errorObserver)

        val mAdapter = MoviesRecyclerViewAdapter(
            favouriteClickListener = {
                viewModel.setFavourite(it)
            }
        )
        mAdapter.setHasStableIds(true)


        recycler_view.apply {
            setHasFixedSize(true)
            adapter = mAdapter
            scrollBottomReachedListener{
                viewModel.loadMoreMovies()
            }
        }

        viewModel.moviesLiveData.observe(viewLifecycleOwner, Observer {
            empty_layout.hide()
            mAdapter.updateList(it)
        })

        empty_layout.refresh.setOnClickListener {
            viewModel.loadMovies()
        }
    }

    // toggle visibility of progress spinner while async operations are taking place
    private val stateObserver = Observer<Event<Boolean>> {
        when(it.getContentIfNotHandled()){
            true -> {
                progress_circular.show()
            }
            false -> {
                progress_circular.hide()
            }
        }
    }


    private val errorObserver = Observer<Event<String>> {
        it.getContentIfNotHandled()?.let { message ->
            requireContext().displayToast(message)
        }
    }
}