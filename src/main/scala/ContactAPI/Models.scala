package ContactAPI

import play.api.libs.json.OFormat
import play.api.libs.json.Json


case class Company(id: String,
                   additional_name: Option[String],
                   version: Int) {

  def toPutch(): CompanyPatchBody = CompanyPatchBody(
    additional_name.map {
      case a: String => a
    }.getOrElse(null),
    version)
}

case class CompanyPatchBody(additional_name: String,
                            version: Int)

object CompanyPatchBody {
  implicit val rw: OFormat[CompanyPatchBody] = Json.format[CompanyPatchBody]
}


case class CompanyItemDto(id: String,
                          name: String,
                          additional_name: Option[String],
                          external_id: String,
                          comment: String,
                          blacklisted: String,
                          active: String,
                          favorite: String,
                          version: String,
                          tags: String,
                          business_sector: String,
                          trade: String,
                          vat_id_numbers: Seq[String] = Seq.empty,
                          addresses: Seq[String] = Seq.empty,
                          customer: Seq[String] = Seq.empty,
                          supplier: Option[String],
                          responsible_users: Option[String]
                          /*responsible_users: String*/)

object CompanyItemDto {
  implicit val rw: OFormat[CompanyItemDto] = Json.format[CompanyItemDto]
}

case class MetaInfo(result_count: Option[Int],
                    total_count: Option[Int])

object MetaInfo {
  implicit val rw: OFormat[MetaInfo] = Json.format[MetaInfo]
}

case class CompanyDto(meta: MetaInfo,
                      result: Seq[CompanyItemDto])

object CompanyDto {
  implicit val rw: OFormat[CompanyDto] = Json.format[CompanyDto]
}





