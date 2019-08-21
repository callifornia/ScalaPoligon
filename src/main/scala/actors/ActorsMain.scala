package actors

import akka.actor.{Actor, ActorLogging, ActorSystem, PoisonPill, Props}
/*

    Akka enforces that all actors are organized into a tree-like hierarchy,
  i.e. an actor that creates another actor becomes the parent of that new actor
  Just like with processes, when an actor fails, its parent actor is notified and it can react to the failure.
  Also, if the parent actor is stopped, all of its children are recursively stopped, too.
  This service is called supervision and it is central to Akka.

    A supervisor (parent) can decide to restart its child actors on certain types of failures or stop them completely on others.
  Children never go silently dead (with the notable exception of entering an infinite loop) instead they are either
  failing and their parent can react to the fault, or they are stopped (in which case interested parties are automatically notified).
  There is always a responsible entity for managing an actor: its parent. Restarts are not visible from the outside:
  collaborating actors can keep continuing sending messages while the target actor restarts

  Unlike objects, actors encapsulate not only their state but their execution.


  Challenges that actors solve include the following:
    How to build and design high-performance, concurrent applications.
    How to handle errors in a multi-threaded environment.
    How to protect my project from the pitfalls of concurrency.

  Remoting enables actors that live on different computers to seamlessly exchange messages

  Challenges Remoting solves include the following:
    How to address actor systems living on remote hosts.
    How to address individual actors on remote actor systems.
    How to turn messages to bytes on the wire.
    How to manage low-level, network connections (and reconnections) between hosts, detect crashed actor systems and hosts, all transparently.
    How to multiplex communications from an unrelated set of actors on the same network connection, all transparently.

  In most cases, you want to use the Cluster module instead of using Remoting directly.
  Clustering provides an additional set of services on top of Remoting that most real world applications need.

  Challenges the Cluster module solves include the following:
    How to maintain a set of actor systems (a cluster) that can communicate with each other and consider each other as part of the cluster.
    How to introduce a new system safely to the set of already existing members.
    How to reliably detect systems that are temporarily unreachable.
    How to remove failed hosts/systems (or scale down the system) so that all remaining members agree on the remaining subset of the cluster.
    How to distribute computations among the current set of members.
    How to designate members of the cluster to a certain role, in other words, to provide certain services and not others.

  Sharding helps to solve the problem of distributing a set of actors among members of an Akka cluster. Sharding is a pattern that mostly used together with Persistence to balance a large set of persistent entities (backed by actors) to members of a cluster and also migrate them to other nodes when members crash or leave.

  Challenges that Sharding solves include the following:
    How to model and scale out a large set of stateful entities on a set of systems.
    How to ensure that entities in the cluster are distributed properly so that load is properly balanced across the machines.
    How to ensure migrating entities from a crashed system without losing the state.
    How to ensure that an entity does not exist on multiple systems at the same time and hence keeps consistent

  A common (in fact, a bit too common) use case in distributed systems is to have a single entity responsible for a given task which is shared among other members of the cluster and migrated if the host system fails. While this undeniably introduces a common bottleneck for the whole cluster that limits scaling, there are scenarios where the use of this pattern is unavoidable. Cluster singleton allows a cluster to select an actor system which will host a particular actor while other systems can always access said service independently from where it is.

  The Singleton module can be used to solve these challenges:
    How to ensure that only one instance of a service is running in the whole cluster.
    How to ensure that the service is up even if the system hosting it currently crashes or shuts down during the process of scaling down.
    How to reach this instance from any member of the cluster assuming that it can migrate to other systems over time.

  For coordination among systems, it is often necessary to distribute messages to all, or one system of a set of interested systems in a cluster. This pattern is usually called publish-subscribe and this module solves this exact problem. It is possible to broadcast messages to all subscribers of a topic or send a message to an arbitrary actor that has expressed interest.

  Cluster Publish-Subscribe is intended to solve the following challenges:
    How to broadcast messages to an interested set of parties in a cluster.
    How to send a message to a member from an interested set of parties in a cluster.
    How to subscribe and unsubscribe for events of a certain topic in the cluster.


  Just like objects in OOP, actors keep their state in volatile memory.
  Once the system is shut down, gracefully or because of a crash, all data that was in memory is lost.
  Persistence provides patterns to enable actors to persist events that lead to their current state.
  Upon startup, events can be replayed to restore the state of the entity hosted by the actor.
  The event stream can be queried and fed into additional processing pipelines (an external Big Data cluster for example) or alternate views (like reports).

  Persistence tackles the following challenges:
    How to restore the state of an entity/actor when system restarts or crashes.
    How to implement a CQRS system.
    How to ensure reliable delivery of messages in face of network errors and system crashes.
    How to introspect domain events that have led an entity to its current state.
    How to leverage Event Sourcing in your application to support long-running processes while the project continues to evolve.
    "com.typesafe.akka" %% "akka-persistence" % "2.5.25"


  Actors are a fundamental model for concurrency, but there are common patterns where their use requires the user to implement the same pattern over and over. Very common is the scenario where a chain, or graph, of actors, need to process a potentially large, or infinite, stream of sequential events and properly coordinate resource usage so that faster processing stages do not overwhelm slower ones in the chain or graph. Streams provide a higher-level abstraction on top of actors that simplifies writing such processing networks, handling all the fine details in the background and providing a safe, typed, composable programming model. Streams is also an implementation of the Reactive Streams standard which enables integration with all third party implementations of that standard.

  Streams solve the following challenges:
    How to handle streams of events or large datasets with high performance, exploiting concurrency and keeping resource usage tight.
    How to assemble reusable pieces of event/data processing into flexible pipelines.
    How to connect asynchronous services in a flexible way to each other with high performance.
    How to provide or consume Reactive Streams compliant interfaces to interface with a third party library.


  The de facto standard for providing APIs remotely, internal or external, is HTTP. Akka provides a library to construct or consume such HTTP services by giving a set of tools to create HTTP services (and serve them) and a client that can be used to consume other services. These tools are particularly suited to streaming in and out a large set of data or real-time events by leveraging the underlying model of Akka Streams.

  Some of the challenges that HTTP tackles:
    How to expose services of a system or cluster to the external world via an HTTP API in a performant way.
    How to stream large datasets in and out of a system using HTTP.
    How to stream live events in and out of a system using HTTP.

  Example of module use
    Akka modules integrate together seamlessly. For example, think of a large set of stateful business objects, such as documents or shopping carts,
    that website users access. If you model these as sharded entities, using Sharding and Persistence, they will be balanced across a cluster
    that you can scale out on-demand. They will be available during spikes that come from advertising campaigns or before holidays will be handled,
    even if some systems crash. You can also take the real-time stream of domain events with Persistence Query and use Streams to pipe
    them into a streaming Fast Data engine. Then, take the output of that engine as a Stream, manipulate it using Akka Streams operators and expose
    it as web socket connections served by a load balanced set of HTTP servers hosted by your cluster to power your real-time business analytics tool.



  AKKA Architecture:
    - An actor in Akka always belongs to a parent

    Whenever an actor is stopped, all of its children are recursively stopped too.
  To stop an actor, the recommended pattern is to call context.stop(self) inside the actor to stop itself,
  usually as a response to some user defined stop message or when the actor is done with its job.
    Stopping another actor is technically possible by calling context.stop(actorRef),
  but It is considered a bad practice to stop arbitrary actors this way: try sending them a PoisonPill or
  custom stop message instead.

    - preStart() is invoked after the actor has started but before it processes its first message.
    - postStop() is invoked just before the actor stops. No messages are processed after this point.

  When we stopped actor first, it stopped its child actor, second, before stopping itself.
  This ordering is strict, all postStop() hooks of the children are called before the postStop() hook of the parent is called.

  The default supervisor strategy is to stop and restart the child

  Message delivery:
    The delivery semantics provided by messaging subsystems typically fall into the following categories:

    1. At-most-once delivery — each message is delivered zero or one time; in more causal terms it means that messages can be lost, but are never duplicated.
    2. At-least-once delivery — potentially multiple attempts are made to deliver each message, until at least one succeeds; again, in more causal terms this means that messages can be duplicated but are never lost.
    3. Exactly-once delivery — each message is delivered exactly once to the recipient; the message can neither be lost nor be duplicated

    Akka use 1-st -> FIRE AND FORGET

    The first behavior, the one used by Akka, is the cheapest and results in the highest performance.
  It has the least implementation overhead because it can be done in a fire-and-forget fashion without keeping the
  state at the sending end or in the transport mechanism. The second, at-least-once, requires retries to counter
  transport losses. This adds the overhead of keeping the state at the sending end and having an acknowledgment
  mechanism at the receiving end. Exactly-once delivery is most expensive, and results in the worst performance:
  in addition to the overhead added by at-least-once delivery, it requires the state to be kept at the receiving
  end in order to filter out duplicate deliveries.

 */

object ActorsMain {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("Some-system")
    val firstActor = system.actorOf(Props(new FirstActor(1, "b")), "FirstActor")
    firstActor ! "start"
    firstActor ! Foo(1, "bc")
  }
}

class FirstActor(a: Int, b: String) extends Actor with ActorLogging{
  override def preStart(): Unit = {
    log.info(s"First started... going to create second")
    context.actorOf(Props(new SecondActor()))
  }

  override def postStop(): Unit = {
    Thread.sleep(5000)
    log.info(s"First stopped... going to stop second")
  }

  val child = context.actorOf(Props(new SecondActor()), "SecondActor")

  def receive: Receive = {
    case "stop" => child ! "exception"
    case "start" => println("piu piu")
    case Foo(`a`, `b`) => log.info("aaaaaaa8888888 Recieved some msg")
    case a: Any => log.info("<<<<<GOT SOME OTHER MSG>>>>>")
  }
}


trait ActorMsgs
case class Foo(a: Int, b: String) extends ActorMsgs


class SecondActor extends Actor with ActorLogging{
  override def preStart(): Unit = {
    log.info(s"Second started..")
  }

  override def postStop(): Unit = {
    println(s"Second stopped...")
  }

  def receive: Receive = {
    case "exception" => throw new Exception("Blo blo ble")
  }

}