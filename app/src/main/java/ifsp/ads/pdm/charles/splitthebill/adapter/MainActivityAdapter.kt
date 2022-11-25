package ifsp.ads.pdm.charles.splitthebill.adapter

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import ifsp.ads.pdm.charles.splitthebill.R
import ifsp.ads.pdm.charles.splitthebill.model.Friend

class MainActivityAdapter (
    context : Context,
    private val friendList : MutableList<Friend>
        ) : ArrayAdapter<Friend>(context, R.layout.tile_friend, friendList) {
    private data class TileAppHolder(val nameTv: TextView, val valuePaidTv: TextView)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val friend = friendList[position]
        var friendTileView = convertView
        if (friendTileView == null) {
            friendTileView = (context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater)
                .inflate(R.layout.tile_friend, parent, false )
            val tileAppHolder = TileAppHolder(
                friendTileView.findViewById(R.id.nameTv),
                friendTileView.findViewById(R.id.paymentTv),
                )
            friendTileView.tag = tileAppHolder
        }
        with(friendTileView?.tag as TileAppHolder) {
            nameTv.text = friend.name
            valuePaidTv.text = friend.amountToPay.toString()
        }
        return friendTileView
    }
}