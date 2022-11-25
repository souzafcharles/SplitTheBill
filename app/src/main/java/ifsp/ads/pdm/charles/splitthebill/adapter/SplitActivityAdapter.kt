package ifsp.ads.pdm.charles.splitthebill.adapter

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import ifsp.ads.pdm.charles.splitthebill.R
import ifsp.ads.pdm.charles.splitthebill.model.Constant.TITLE_TOPAY
import ifsp.ads.pdm.charles.splitthebill.model.Constant.TITLE_TORECEIVED
import ifsp.ads.pdm.charles.splitthebill.model.Friend
import kotlin.math.abs
import kotlin.math.roundToInt

class SplitActivityAdapter (
    context : Context,
    private val friendList : ArrayList<Friend>,
    private val splittedValue : Double
) : ArrayAdapter<Friend>(context, R.layout.tile_friend, friendList) {
    private data class TileAppHolder(val nameTv: TextView, val paymentTitleTV: TextView, val paymentTv: TextView)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val friend = friendList[position]
        var friendTileView = convertView
        if (friendTileView == null) {
            friendTileView = (context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater)
                .inflate(R.layout.tile_friend, parent, false )
            val tileAppHolder = TileAppHolder(
                friendTileView.findViewById(R.id.nameTv),
                friendTileView.findViewById(R.id.paymentTitleTv),
                friendTileView.findViewById(R.id.paymentTv),
            )
            friendTileView.tag = tileAppHolder
        }
        with(friendTileView?.tag as TileAppHolder) {
            nameTv.text = friend.name
            paymentTitleTV.text = if (friend.amountToPay <= splittedValue) TITLE_TOPAY else TITLE_TORECEIVED
            paymentTv.text = ((abs(friend.amountToPay - splittedValue)*100.0).roundToInt()/100.0).toString()
        }
        return friendTileView
    }
}