import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

//pomidor do bazy
@DatabaseTable(tableName = "tomato")
class Tomato(@DatabaseField(generatedId = true) private val id: Int,
              @DatabaseField private val userId: Int,
              @DatabaseField private val startDate: String) {
}
