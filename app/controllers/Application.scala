package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models.{Category, Task}

object Application extends Controller {

  val taskForm = Form(
    "label" -> nonEmptyText
  )
  val categories = List(Category("Genesis", "url://genesis", 5),
    Category("Exodus", "url://exodus", 6))

  def index = Action {
    Redirect(routes.Application.tasks)
  }


  def tasks = Action {
    Ok(views.html.index(Task.all(), taskForm, categories))
  }

  def newTask = Action { implicit request =>
    taskForm.bindFromRequest.fold(
      errors => BadRequest(views.html.index(Task.all(), errors, categories)),
      label => {
        Task.create(label)
        Redirect(routes.Application.tasks)
      }
    )
  }

  def deleteTask(id: Long) = Action {
    Task.delete(id)
    Redirect(routes.Application.tasks)
  }
}