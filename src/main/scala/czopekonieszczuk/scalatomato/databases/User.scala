import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "user")
class User(@DatabaseField(generatedId = true) private val id: Int,
           @DatabaseField private val name: String,
           @DatabaseField private val surname: String,
           @DatabaseField private var password: String,
           @DatabaseField private var lastLogin:String) {
}





