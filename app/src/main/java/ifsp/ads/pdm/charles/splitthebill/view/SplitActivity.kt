package ifsp.ads.pdm.charles.splitthebill.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ifsp.ads.pdm.charles.splitthebill.adapter.SplitActivityAdapter
import ifsp.ads.pdm.charles.splitthebill.databinding.ActivitySplitBinding
import ifsp.ads.pdm.charles.splitthebill.model.Constant
import ifsp.ads.pdm.charles.splitthebill.model.Friend

class SplitActivity : AppCompatActivity() {
    private val sab : ActivitySplitBinding by lazy {
       ActivitySplitBinding.inflate(layoutInflater)
    }
    private lateinit var adapter : SplitActivityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(sab.root)
        val friendArrayList = intent.getParcelableArrayListExtra<Friend>(Constant.FRIEND_LIST)
        var splittedValue = 0.0
        if(!friendArrayList.isNullOrEmpty()) {
            friendArrayList.forEach { participant ->
                splittedValue += participant.amountToPay
            }
            adapter = SplitActivityAdapter(this, friendArrayList, splittedValue / friendArrayList.size)
            sab.splitLv.adapter = adapter
        }
    }
}