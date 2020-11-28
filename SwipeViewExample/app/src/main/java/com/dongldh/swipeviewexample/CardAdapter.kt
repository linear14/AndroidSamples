package com.dongldh.swipeviewexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_borrow_card.view.*

class CardAdapter(val list: MutableList<Donate>): RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_borrow_card, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    inner class CardViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val card = view.card
        val userName = view.user_name
        val userJob = view.user_job
        val userProfile = view.user_profile
        val bookTitle = view.book_title
        val bookAuthor = view.book_author
        val bookPublisher = view.book_publisher

        fun onBind(item: Donate) {
            userName.text = "유저이름"
            userJob.text = "직업"
            userProfile.circleColor = R.color.colorAccent
            bookTitle.text = "책 제목"
            bookAuthor.text = "저자"
            bookPublisher.text = "출판사"
        }
    }
}