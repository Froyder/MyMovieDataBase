package com.example.mymoviedatabase.view.adapters

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviedatabase.R
import com.example.mymoviedatabase.view.MainActivity
import com.example.mymoviedatabase.view.fragments.MovieFragment
import com.example.mymoviedatabase.view.fragments.NoteFragment
import com.example.retrofittest2.Result
import kotlinx.android.synthetic.main.history_fragment_recycler_item.view.*

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.RecyclerItemViewHolder>() {

    private var data: List<Result> = arrayListOf()
    private var activity : MainActivity? = null

    fun setData(data: List<Result>, activity: MainActivity) {
        this.data = data
        this.activity = activity
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.history_fragment_recycler_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: Result) {
            if (layoutPosition != RecyclerView.NO_POSITION) {

                itemView.historyRecyclerViewItem.text =
                        String.format("\"%s\", rating: %s, realised: %s", data.title,  data.vote_average, data.release_date)
                itemView.setOnClickListener {
                    Toast.makeText(itemView.context,  "on click: ${data.title}", Toast.LENGTH_SHORT).show()

                    activity?.supportFragmentManager?.apply {
                        beginTransaction()
                                .replace(R.id.container, NoteFragment.newInstance(Bundle().apply {
                                    putParcelable(NoteFragment.BUNDLE_EXTRA, data)
                                }))
                                .addToBackStack("")
                                .commitAllowingStateLoss()
                    }

                }
            }
        }
    }
}