package com.example.skillexchange.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skillexchange.R
import com.example.skillexchange.models.FirebaseUtils
import com.example.skillexchange.models.Messages

class MessageAdapter: RecyclerView.Adapter<MessageAdapter.MessageHolder>() {

    private var listOfMessage = listOf<Messages>()
    private val LEFT = 0
    private val RIGHT = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageHolder {

        val inflater = LayoutInflater.from(parent.context)

        return if (viewType == RIGHT) {

            val view = inflater.inflate(R.layout.chatitemright, parent, false)
            MessageHolder(view)

        } else {

            val view = inflater.inflate(R.layout.chatitemleft, parent, false)
            MessageHolder(view)
        }
    }

    override fun onBindViewHolder(holder: MessageHolder, position: Int) {
        val message = listOfMessage[position]

        holder.messageText.visibility = View.VISIBLE
        holder.messageText.visibility = View.VISIBLE

        holder.messageText.setText(message.message)
        holder.timeOfSent.text = message.time?.substring(0, 5) ?: ""
    }

    override fun getItemCount(): Int {
        return listOfMessage.size
    }

    override fun getItemViewType(position: Int): Int =
        if (listOfMessage[position].sender == FirebaseUtils.getUiLoggedIn()) RIGHT else LEFT

    fun setMessageList(newList: List<Messages>) {
        this.listOfMessage = newList
    }

    class MessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val messageText: TextView = itemView.findViewById(R.id.showMessage)
        val timeOfSent: TextView = itemView.findViewById(R.id.timeView)
    }
}
