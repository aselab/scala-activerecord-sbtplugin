package models

import com.github.aselab.activerecord._
import com.github.aselab.activerecord.dsl._

/**
 * Post model.
 *
 * @param title 
 * @param body 
 */
case class Post (
  @Required title: String,
  body: Option[String]
) extends ActiveRecord

object Post extends ActiveRecordCompanion[Post]
