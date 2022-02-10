package com.airtouch.atnm.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airtouch.atnm.R
import com.airtouch.atnm.models.Pairs
import com.airtouch.atnm.models.PairsWitheRate
import com.airtouch.atnm.models.Rates


class HomeFragment : Fragment() {

    private lateinit var rvNotes: RecyclerView
    private val viewModel: HomeViewModel by activityViewModels()
    private var homeAdapter: HomeAdapter? = null
    private val listNewPairs: MutableList<PairsWitheRate> = arrayListOf()
    private val listPairsWitheOutRate: MutableList<Pairs> = arrayListOf()
    private val listRates: MutableList<Rates> = arrayListOf()

    private var foundedRate: Float = 0f
    private var foundedRateList: MutableList<Float> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvNotes = view.findViewById(R.id.rv_pairs)
        initRecyclerView()
    }

    private fun checkIfRouteExists(from: String, to: String): Boolean {
        foundedRate = listRates.find { it.from == from && it.to == to }?.rate ?: 0f
        return foundedRate != 0f
    }

    private fun verification(from: String, to: String, path: Set<String> = setOf()) {
        if (path.contains(from)) {
            return
        }

        val verDoubleCheck = checkIfRouteExists(from, to)
        for (i in 0 until listRates.size) {
            if (!verDoubleCheck) {
                if (listRates[i].from == from) {
                    foundedRateList.add(listRates[i].rate)
                    verification(listRates[i].to, to, path + from)
                }
            } else if (verDoubleCheck) {
                val newPairs = PairsWitheRate(
                    path.first(),
                    to,
                    foundedRate * foundedRateList.reduce { acc, fl -> acc * fl })
                listNewPairs.add(newPairs)
                foundedRateList.clear()
                break
            }
        }
    }

    private fun initRecyclerView() {
        viewModel.exchangeInfoErrorLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
        }
        viewModel.loadExchangeInfo()
        viewModel.exchangeInfoLiveData.observe(viewLifecycleOwner) { exchangeInfo ->
            exchangeInfo.pairs.forEach { pair ->
                val list = exchangeInfo.rates.filter { it.from == pair.from }
                val rate = list.firstOrNull { it.to == pair.to }

                if (rate != null) {
                    val newPairs = PairsWitheRate(rate.from, rate.to, rate.rate)
                    listNewPairs.add(newPairs)
                } else {
                    listPairsWitheOutRate.add(Pairs(pair.from, pair.to))
                }
            }
            listRates.addAll(exchangeInfo.rates.map { Rates(it.from, it.to, it.rate) })
            Log.d("listPairsWitheOutRate", listPairsWitheOutRate.toString())
            listPairsWitheOutRate.forEach {
                verification(it.from, it.to)
            }
            homeAdapter?.submitList(listNewPairs.distinct().toList())
        }

        homeAdapter = HomeAdapter()

        rvNotes.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = homeAdapter
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }
}