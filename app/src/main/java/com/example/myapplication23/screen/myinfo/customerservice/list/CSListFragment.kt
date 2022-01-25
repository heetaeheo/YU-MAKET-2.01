package com.example.myapplication23.screen.myinfo.customerservice.list

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication23.R
import com.example.myapplication23.databinding.FragmentCsListBinding
import com.example.myapplication23.model.customerservicelist.CSModel
import com.example.myapplication23.screen.base.BaseFragment
import com.example.myapplication23.screen.myinfo.MyInfoFragment
import com.example.myapplication23.widget.adapter.ModelRecyclerAdapter
import com.example.myapplication23.widget.adapter.listener.customerservice.CSModelListener
import com.example.myapplication23.widget.adapter.viewholder.CSModelRecyclerAdapter
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * @author HeeTae Heo(main),
 * Geonwoo Kim, Doyeop Kim, Namjin Jeong, Eunho Bae (sub)
 * @since
 * @throws
 * @description
 */

class CSListFragment : BaseFragment<CSListViewModel, FragmentCsListBinding>() {


    override val viewModel by viewModel<CSListViewModel> {
        parametersOf(csCategory)
    }
    private val csCategory by lazy {
        arguments?.getSerializable(CS_CATEGORY_KEY) as CSCategory
    }

    override fun getViewBinding(): FragmentCsListBinding =
        FragmentCsListBinding.inflate(layoutInflater)

    override fun observeData() = with(viewModel) {
        csListData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

    }
    private fun showMessage(message: String) =
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

    private val adapter by lazy {
        CSModelRecyclerAdapter<CSModel, CSListViewModel>(
           listOf(),
            viewModel,
            object : CSModelListener{
                override fun onClickItem(listModel: CSModel) {
                    showMessage(listModel.toString())
                }
            }
        )
    }

    override fun initViews() = with(binding){
        csRecyclerView.adapter = adapter
        csRecyclerView.layoutManager = LinearLayoutManager(this@CSListFragment.context)





    }
    companion object {
        const val CS_CATEGORY_KEY = "CSCategoryKey"


        fun newInstance(csCategory: CSCategory): CSListFragment {
            val bundle = Bundle().apply {
                putSerializable(CS_CATEGORY_KEY, csCategory)
            }

            return CSListFragment().apply {
                arguments = bundle
            }
        }
    }


}