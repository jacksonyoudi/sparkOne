package org.ip

import java.io.File
import java.net.InetAddress

import com.maxmind.db.CHMCache
import com.maxmind.geoip2.DatabaseReader
import com.maxmind.geoip2.model.CityResponse
import net.ipip.ipdb.{City, CityInfo}

import scala.collection.mutable.ArrayBuffer


/**
 * 使用静态方法和静态属性
 */
object IP extends Serialiszable {

  val ipDB = new City("db/ipdata.ipdb")
  val file: File = new File("db/GeoLite2-City.mmdb")
  val geoReader = new DatabaseReader.Builder(file).withCache(new CHMCache()).build()

  def findIp(ip: String): IPInfo = {

    // TODO 判断ip合法性


    try {
      val cityInfo: CityInfo = ipDB.findInfo(ip, "CN")
      if ("CN".equals(cityInfo.getCountryCode)) {
        return new IPInfo(ip, cityInfo.getCountryName, cityInfo.getRegionName, cityInfo.getCityName, cityInfo.getIspDomain, cityInfo.getIDC)
      }

      val address: InetAddress = InetAddress.getByName(ip)
      val cityResponse: CityResponse = geoReader.city(address)
      if (cityResponse.getCountry.getNames.getOrDefault("zh-CN", "").length == 0) {
        return new IPInfo(ip, cityInfo.getCountryName, cityInfo.getRegionName, cityInfo.getCityName, cityInfo.getIspDomain, cityInfo.getIDC)
      }
      IPInfo(
        ip,
        cityResponse.getCountry.getNames.getOrDefault("zh-CN", ""),
        cityResponse.getContinent.getNames.getOrDefault("zh-CN", ""),
        cityResponse.getCity.getNames.getOrDefault("zh-CN", ""),
        "", "")
    } catch {
      // 解析出现异常，就返回空
      case ex: Exception => {
        IPInfo("", "", "", "", "", "")
      }
    }
  }

  def findIntIp(IntIp: BigInt): IPInfo = {
    findIp(IPInt2Str(IntIp))
  }

  // 转换ip
  def IPInt2Str(ip: BigInt): String = {
    // TODO 合法性
    var n = ip
    val arr = ArrayBuffer[BigInt]()
    for (i <- 1 to 4) {
      val ni = n & 0xFF
      arr += ni
      n >>= 8
    }
    val sb = new StringBuilder
    for (s <- arr.reverse) {
      sb.append(s).append(".")
    }
    sb.deleteCharAt(sb.length - 1)
    sb.toString
  }

}