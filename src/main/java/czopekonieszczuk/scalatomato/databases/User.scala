package czopekonieszczuk.scalatomato.databases

import scala.beans.BeanProperty


class User(@BeanProperty var login: String, @BeanProperty var password: String)
{
  @BeanProperty
  var id: Long = -1

  def this(id: Long, login: String, password: String) {
    this(login, password)
    this.id = id
  }
}