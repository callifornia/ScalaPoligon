trait Events
case class AccommodationUpdatedEvent(values: List[Any]) extends Events {
  def isEmpty: Boolean = values.isEmpty
}

object AccommodationUpdatedEvent {
  implicit object accommodationMergedEvents extends EventsMergeMonoid[AccommodationUpdatedEvent] {
    def empty = AccommodationUpdatedEvent(List.empty)
    override def merge(target: AccommodationUpdatedEvent,
                       source: AccommodationUpdatedEvent): AccommodationUpdatedEvent = (target, source) match {

      case (target, source) if target.isEmpty && source.isEmpty => empty
      case (target, source) if target.isEmpty => source
      case (target, source) if source.isEmpty => target
      case (target, source) if source.isEmpty => target.values.zip(source.values).map{
        case (t, s) => t.toString + s.toString
      }.foldLeft(empty){ (acc, el) => acc.copy(acc.values :+ el) }
    }
  }
}

case class SellingCodeUpdatedEvent(values: List[Any]) extends Events {
  def isEmpty: Boolean = values.isEmpty
}
case class ReferenceCodeUpdatedEvent(values: List[Any]) extends Events {
  def isEmpty: Boolean = values.isEmpty
}

trait EventsMergeMonoid[A <: Events] {
  def empty: A
  def merge(a: A, b: A): A
}
case class Accommodation(name: String) {
  def asEvents: AccommodationUpdatedEvent = AccommodationUpdatedEvent(name :: Nil)
}


case class SellingCodes[A](values: List[A]) {
  def asEvents: Events = SellingCodeUpdatedEvent(values)
}

case object SellingCodes {
  implicit object accommodationMergedEvents extends EventsMergeMonoid[SellingCodeUpdatedEvent] {
    def empty = SellingCodeUpdatedEvent(List.empty)
    override def merge(target: SellingCodeUpdatedEvent,
                       source: SellingCodeUpdatedEvent): SellingCodeUpdatedEvent = (target, source) match {

      case (target, source) if target.isEmpty && source.isEmpty => empty
      case (target, source) if target.isEmpty => source
      case (target, source) if source.isEmpty => target
      case (target, source) if source.isEmpty => target.values.zip(source.values).map{
        case (t, s) => t.toString + s.toString
      }.foldLeft(empty){ (acc, el) => acc.copy(acc.values :+ el) }
    }
  }
}

case class ReferenceCodes[A](values: List[A]) {
  def asEvents: Events = ReferenceCodeUpdatedEvent(values)
}

case object ReferenceCodes {
  implicit object accommodationMergedEvents extends EventsMergeMonoid[ReferenceCodeUpdatedEvent] {
    def empty = ReferenceCodeUpdatedEvent(List.empty)
    override def merge(target: ReferenceCodeUpdatedEvent,
                       source: ReferenceCodeUpdatedEvent): ReferenceCodeUpdatedEvent = (target, source) match {

      case (target, source) if target.isEmpty && source.isEmpty => empty
      case (target, source) if target.isEmpty => source
      case (target, source) if source.isEmpty => target
      case (target, source) if source.isEmpty => target.values.zip(source.values).map{
        case (t, s) => t.toString + s.toString
      }.foldLeft(empty){ (acc, el) => acc.copy(acc.values :+ el) }
    }
  }
}

val a1 = Accommodation("acco_1")
val a2 = Accommodation("acco_2")

def merge[A <: Events](entities: List[A])(implicit monoid: EventsMergeMonoid[A]) =
  entities.foldLeft(monoid.empty)(monoid.merge)

val l1 = List(Accommodation("acco_1"), Accommodation("acco_2")).map(_.asEvents)
merge(l1)




