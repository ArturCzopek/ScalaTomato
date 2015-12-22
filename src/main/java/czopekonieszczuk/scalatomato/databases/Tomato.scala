package czopekonieszczuk.scalatomato.databases

import java.text.SimpleDateFormat
import java.util.Date
import scala.beans.BeanProperty

class Tomato(@BeanProperty var userId: Long) {

  @BeanProperty
  var id: Long = -1

  @BeanProperty
  var date: String = new SimpleDateFormat("yyyy/MM/dd hh:mm").format(new Date())

  def this(id: Long, userId: Long) {
    this(userId)
    this.id = id
//    this.date = new SimpleDateFormat("yyyy/MM/dd hh:mm").format(new Date())
  }

}
