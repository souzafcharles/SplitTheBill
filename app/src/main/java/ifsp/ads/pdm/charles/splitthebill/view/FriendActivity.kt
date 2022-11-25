package ifsp.ads.pdm.charles.splitthebill.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import ifsp.ads.pdm.charles.splitthebill.databinding.ActivityFriendBinding
import ifsp.ads.pdm.charles.splitthebill.model.Constant.INCLUDE_FRIEND
import ifsp.ads.pdm.charles.splitthebill.model.Constant.VIEW_FRIEND
import ifsp.ads.pdm.charles.splitthebill.model.Friend
import kotlin.random.Random

class FriendActivity : AppCompatActivity() {

    private val afb : ActivityFriendBinding by lazy{
        ActivityFriendBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(afb.root)

        val receivedFriend = intent.getParcelableExtra<Friend>(INCLUDE_FRIEND)
        receivedFriend?.let { _receivedParticipant ->
            with(afb) {
                with(_receivedParticipant) {
                    nameEt.setText(name)
                    valuetoPayEt.setText(amountToPay.toString())
                    itemsEt.setText(items)
                }
            }
        }

        val friendView = intent.getBooleanExtra(VIEW_FRIEND, false)
        if(friendView) {
            afb.nameEt.isEnabled = false
            afb.valuetoPayEt .isEnabled = false
            afb.itemsEt.isEnabled = false
            afb.saveBtn.visibility = View.GONE
        }

        afb.saveBtn.setOnClickListener {
            val friend = Friend(
                id = receivedFriend?.id?: Random(System.currentTimeMillis()).nextInt(),
                name = afb.nameEt.text.toString(),
                amountToPay =  (afb.valuetoPayEt.text.toString()).toDouble(),
                items = afb.itemsEt.text.toString()

            )

            val intentResult = Intent()
            intentResult.putExtra(INCLUDE_FRIEND, friend)
            setResult(RESULT_OK, intentResult)
            finish()
        }
    }
}