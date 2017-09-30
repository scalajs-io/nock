package io.scalajs.npm.nock

import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined
import scala.scalajs.js.|

/**
  * Nock Recorder
  * @author lawrence.daniels@gmail.com
  */
@js.native
trait NockRecorder extends js.Object {

  def play(): js.Array[NockPlayBack] = js.native

  def rec(options: NockRecorderOptions | js.Any = js.native): Unit = js.native

}

/**
  * Nock Play Back object
  * @author lawrence.daniels@gmail.com
  */
@js.native
trait NockPlayBack extends js.Object {

  /**
    * @return the scope of the call including the protocol and non-standard ports (e.g. 'https://github.com:12345')
    */
  def scope: String = js.native

  /**
    * @return the HTTP verb of the call (e.g. 'GET')
    */
  def method: String = js.native

  /**
    * @return the path of the call (e.g. '/pgte/nock')
    */
  def path: String = js.native

  /**
    * @return the body of the call, if any
    */
  def body: String = js.native

  /**
    * @return the HTTP status of the reply (e.g. 200)
    */
  def status: Int = js.native

  /**
    * @return the body of the reply which can be a JSON, string, hex string representing binary buffers or an array
    *         of such hex strings (when handling content-encoded in reply header)
    */
  def response: js.Any = js.native

  /**
    * @return the headers of the reply
    */
  def headers: js.Dictionary[js.Any] = js.native

  /**
    * @return the headers of the request
    */
  def reqheader: js.Dictionary[js.Any] = js.native

}

/**
  * Nock Recorder Options
  * @author lawrence.daniels@gmail.com
  */
@ScalaJSDefined
class NockRecorderOptions(val dont_print: js.UndefOr[Boolean] = js.undefined,
                          val enable_reqheaders_recording: js.UndefOr[Boolean] = js.undefined,
                          val logging: js.UndefOr[String] = js.undefined,
                          val output_objects: js.UndefOr[Boolean] = js.undefined,
                          val use_separator: js.UndefOr[Boolean] = js.undefined)
  extends js.Object