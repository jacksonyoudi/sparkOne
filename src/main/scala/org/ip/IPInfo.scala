package org.ip

/**
 * type IpInfo struct {
 * Ip      string
 * Country string
 * Region  string
 * City    string
 * Isp     string
 * Cdn     int
 * Lng     float64
 * Lat     float64
 * }
 */
case class IPInfo(inIp: String, inCountry: String, inRegion: String, inCity: String, inIsp: String, inIdc: String) {
  val ip: String = inIp
  val country: String = inCountry
  val region: String = inRegion
  val city: String = inCity
  val isp: String = inIsp
  var idc: String = inIdc
}
