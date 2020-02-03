import scala.math.BigDecimal.RoundingMode

val d = BigDecimal(123.12355)
d.setScale(3, RoundingMode.HALF_UP)