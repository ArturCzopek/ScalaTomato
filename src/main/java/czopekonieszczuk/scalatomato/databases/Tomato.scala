package czopekonieszczuk.scalatomato.databases

import java.text.SimpleDateFormat
import java.util.Date
import scala.beans.BeanProperty

class Tomato(@BeanProperty var userId: Int) {

  @BeanProperty
  var id: Int = 0

  @BeanProperty
  var date: String = dateFormat.format(today)

  val dateFormat = new SimpleDateFormat("yyyy/MM/dd")

  val today = new Date()

  def this(id: Int, userId: Int) {
    this()
    this.id = id
    this.userId = userId
    val dateFormat = new SimpleDateFormat("yyyy/MM/dd")
    val today = new Date()
    this.date = dateFormat.format(today)
  }
}
