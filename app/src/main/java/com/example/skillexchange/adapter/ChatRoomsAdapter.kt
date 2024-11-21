package com.example.skillexchange.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skillexchange.R
import com.example.skillexchange.models.Users
import de.hdodenhof.circleimageview.CircleImageView

class ChatRoomsAdapter: RecyclerView.Adapter<UserHolder>() {

    private var listOfUsers = listOf<Users>()
    private var listener: OnUserClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_rooms_list_item, parent, false)
        return UserHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfUsers.size
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {

        val users = listOfUsers[position]

        val name = users.name!!.split("\\s".toRegex())[0]
        holder.profileName.setText(name)

        if (users.status.equals("Online")){
            //change to online status (green circle)
            holder.statusImageView.setImageResource(R.drawable.arrow_icon_goo)
        }else{
            //change to offline status (gray circle)
            holder.statusImageView.setImageResource(R.drawable.arrow_icon_backk)
        }

        Glide.with(holder.itemView.context).load(users.photoUrl).into(holder.imageProfile)

        holder.itemView.setOnClickListener {
            listener?.onUserSelected(position, users)
        }

    }

    fun setUserList(list: List<Users>) {
        this.listOfUsers = list
        notifyDataSetChanged()
    }

    fun setOnUserClickListener(listener: OnUserClickListener) {
        this.listener = listener
    }
}


    class UserHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val profileName: TextView = itemView.findViewById(R.id.user_name_ChatRooms)
        val imageProfile: CircleImageView = itemView.findViewById(R.id.user_photo_ChatRooms)
        val statusImageView: CircleImageView =
            itemView.findViewById(R.id.status_online_iv_ChatRooms)

    }

    interface OnUserClickListener {
        fun onUserSelected(position: Int, users: Users)
    }


