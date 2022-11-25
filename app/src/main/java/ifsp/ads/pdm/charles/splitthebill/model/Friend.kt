package ifsp.ads.pdm.charles.splitthebill.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Friend(
    var id: Int,
    var name: String,
    var amountToPay: Double,
    var items: String
) : Parcelable
