package com

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer

import scala.concurrent.Future
import scala.io.Source
import scala.util.Failure

object Main {
  implicit val ac = ActorSystem("foo")
  implicit val ex = ac.dispatcher
  val first_file = "NurvisCodesNotInHub.txt"
  val second_file = "NurvisCodesNotInHub_other.txt"
  val coeFile = "body.json"

  def main(args: Array[String]) = {










    //    println(None.isEmpty)
//    readForCoe()
//    check()
  }


//  def sendRequest(body: String, num: Int): Unit = {
//
//    val httpResponse = Http().singleRequest(HttpRequest(
//      uri = Uri.apply("http://10.181.195.89/export/list"),
//      method = HttpMethods.POST,
//      entity = HttpEntity(ContentTypes.`application/json`, body)))(ActorMaterializer())
//
//    httpResponse.onComplete{
//      case Failure(ex) => println(s"""
//           | ${Console.RED}
//           | num: $num
//           | failed: $ex
//           | body: $body
//           |${Console.RESET}
//         """.stripMargin)
//      case scala.util.Success(value) => {
//        println(s"""
//                   | ${Console.GREEN}
//                   | num: $num
//                   | success: $value
//                   | body: $body
//                   |${Console.RESET}
//         """.stripMargin)
//      }
//    }
//  }

//  def readForCoe() = {
//    val allList = Source.fromResource(coeFile).getLines.toSeq
//    val grouped = allList.grouped(250).toList.drop(6).map{list =>
//      val lastElemWithoutComma = list(list.size - 1).dropRight(1)
//      val correctBody = list.dropRight(1) :+ lastElemWithoutComma
//      s""" | {"consumer": "GIATA", "accommodations": [${correctBody.mkString(" ")}]} """.stripMargin}
//
//    var i = 1
//
//    grouped.foreach{ el =>
//      sendRequest(el, i)
//      i = i + 1
//      Thread.sleep(160000)
//    }
//    Thread.sleep(3000)
//    println(s"AFTER LOOP")
//  }
//
//  def check() = {
//    val str = "{\"consumer\": \"GIATA\", \"accommodations\": [{ \"brandCode\": \"NER\", \"name\": \"\", \"season\": \"W19\", \"nurvis\": [ \"nurvis:/K6317X\" ], \"ekms\": \"ekms:/K6317\", \"brand\": \"ner.neckermann-reisen.de\",          \"language\": \"de-DE\", \"live\": true, \"catalogs\": [ \"BETN\" ], \"mhid\": \"mhct4Lt\" }]}"
//    val formatQueries = HttpEntity(ContentTypes.`application/json`,str)
//    val httpResponse = Http().singleRequest(HttpRequest(
//      uri = Uri.apply("http://10.21.8.119/export/list"),
//      method = HttpMethods.POST,
//      entity = formatQueries))(ActorMaterializer())
//
//    httpResponse.onComplete{
//      case Failure(ex) => println(s"failed: $ex")
//      case scala.util.Success(value) => println(s"success: $value")
//    }
//    Thread.sleep(10000)
//  }

  def toFile() = {
    val first_file = "NurvisCodesNotInHub.txt"
    val second_file = "NurvisCodesNotInHub_other.txt"

    val first = Source.fromResource(first_file).getLines.toSet
    val second = Source.fromResource(second_file).getLines.toSet

    val missed_in_second = first.diff(second)
    val missed_in_first = second.diff(first)

    println(s"${first.size}, ${second.size}")
    println(s"missed in first: $missed_in_first")
    println(s"missed in second: $missed_in_second")
  }
}
