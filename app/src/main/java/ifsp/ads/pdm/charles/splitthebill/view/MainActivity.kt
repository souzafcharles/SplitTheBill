package ifsp.ads.pdm.charles.splitthebill.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.AdapterContextMenuInfo
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import ifsp.ads.pdm.charles.splitthebill.R
import ifsp.ads.pdm.charles.splitthebill.adapter.MainActivityAdapter
import ifsp.ads.pdm.charles.splitthebill.databinding.ActivityMainBinding
import ifsp.ads.pdm.charles.splitthebill.model.Constant.INCLUDE_FRIEND
import ifsp.ads.pdm.charles.splitthebill.model.Constant.FRIEND_LIST
import ifsp.ads.pdm.charles.splitthebill.model.Constant.VIEW_FRIEND
import ifsp.ads.pdm.charles.splitthebill.model.Friend

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val friendList = mutableListOf<Friend>()

    private lateinit var adapter: MainActivityAdapter

    private lateinit var carl : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)
        adapter = MainActivityAdapter(this, friendList)
        amb.mainLv.adapter = adapter
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(), ) {
                result ->
            if(result.resultCode == RESULT_OK) {
                val friend = result.data?.getParcelableExtra<Friend>(INCLUDE_FRIEND)
                friend?.let { _participant ->
                    val pos = friendList.indexOfFirst { it.id == _participant.id }
                    if (pos != -1) {
                        friendList[pos] = _participant
                    } else {
                        friendList.add(_participant)
                    }
                    adapter.notifyDataSetChanged()
                }
            }
        }.also { carl = it }
        registerForContextMenu(amb.mainLv)

        amb.mainLv.onItemClickListener = AdapterView.OnItemClickListener {_, _, pos, _ ->
            val friend = friendList[pos]
            val friendIntent = Intent(this@MainActivity, FriendActivity::class.java)
            friendIntent.putExtra(INCLUDE_FRIEND, friend)
            friendIntent.putExtra(VIEW_FRIEND, true)
            startActivity(friendIntent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.addFriendMi -> {
                carl.launch(Intent(this, FriendActivity::class.java))
                true
            }
            R.id.calculateAmountMi -> {
                val splitIntent = Intent(this, SplitActivity::class.java)
                val friendArrayList = ArrayList<Friend>()
                friendArrayList.addAll(friendList)
                splitIntent.putParcelableArrayListExtra(FRIEND_LIST, friendArrayList)
                startActivity(splitIntent)
                true
            }
            else -> {false}
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.context_menu_main, menu)
    }
    
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val pos = (item.menuInfo as AdapterContextMenuInfo).position
        return when (item.itemId) {
            R.id.removeFriendMi -> {
               friendList.removeAt(pos)
                adapter.notifyDataSetChanged()
                true
            }
            R.id.editFriendMi -> {
                val participant = friendList[pos]
                val participantIntent = Intent(this, FriendActivity::class.java)
                participantIntent.putExtra(INCLUDE_FRIEND, participant)
                participantIntent.putExtra(VIEW_FRIEND, false)
                carl.launch(participantIntent)
                true
            }
            else -> {false}
        }
    }
}
