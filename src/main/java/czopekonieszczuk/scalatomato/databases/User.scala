package czopekonieszczuk.scalatomato.databases

import scala.beans.BeanProperty


class User(@BeanProperty var login: String, @BeanProperty var password: String)
{

  @BeanProperty
  var id: Int = 0

  def this(id: Int, login: String, password: String) {
    this()
    this.id = id
    this.login = login
    this.password = password
  }
}