import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "user")
class User(_name: String, _surname: String, _password: String, _lastLogin:String){

  @DatabaseField(generatedId = true)
  private val id = User.inc

  @DatabaseField
  private val name = _name

  @DatabaseField
  private val surname = _surname

  @DatabaseField
  private val password = _password

  @DatabaseField
  private var lastLogin = _lastLogin

}

object User{
  private var currentId = 0
  private def inc = {currentId += 1; currentId}
}

