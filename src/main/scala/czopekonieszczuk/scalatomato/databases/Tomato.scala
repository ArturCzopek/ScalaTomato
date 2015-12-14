import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

//pomidor do bazy
@DatabaseTable(tableName = "tomato")
class Tomato(_userId: Int, _startDate: String){

  @DatabaseField(generatedId = true)
  private val id = Tomato.inc

  @DatabaseField
  private val userId = _userId

  @DatabaseField
  private val startDate = _startDate
}

object Tomato{
  private var currentId = 0
  private def inc = {currentId += 1; currentId}
}
