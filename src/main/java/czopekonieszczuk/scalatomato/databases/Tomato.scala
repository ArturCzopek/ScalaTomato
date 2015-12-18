package czopekonieszczuk.scalatomato.databases

import java.text.SimpleDateFormat
import java.util.Date
import scala.beans.BeanProperty

class Tomato(@BeanProperty var userId: Long) {

  val today = new Date()
  val dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm")

  @BeanProperty
  var id: Long = -1

  @BeanProperty
  var date: String = dateFormat.format(today)

  def this(id: Long, userId: Long) {
    this(userId)
    this.id = id
    this.date = dateFormat.format(today)
  }

}
