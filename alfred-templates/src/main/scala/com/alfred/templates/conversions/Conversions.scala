package com.alfred.templates.conversions

import java.util.{List => JList}
import java.util.concurrent.{CompletableFuture, ExecutionException, Future}
import com.twitter.bijection.{AbstractBijection, Bijection}
import reactor.core.publisher.{Flux, Mono}

/**
  * Created by renanpalmeira on 21/01/18.
  */
object Conversions {

  def futureToMono[T]: Bijection[Future[T], Mono[T]] =
    new AbstractBijection[Future[T], Mono[T]] {
      override def apply(f: Future[T]): Mono[T] = completableFutureToMono[T].apply(futureToCompletableFuture[T].apply(f))
      override def invert(m: Mono[T]): Future[T] = m.toFuture
    }

  def completableFutureToFlux[T]: Bijection[CompletableFuture[JList[T]], Flux[T]] =
    new AbstractBijection[CompletableFuture[JList[T]], Flux[T]] {
      override def apply(f: CompletableFuture[JList[T]]): Flux[T] = Flux.create(subscriber => {
        f
          .thenAcceptAsync(_.stream().forEach(b => subscriber.next(b)))
          .whenComplete((_, _) => subscriber.complete())
      })
      override def invert(f: Flux[T]): CompletableFuture[JList[T]] = CompletableFuture.supplyAsync(() => f.collectList().block())
    }

  def completableFutureToMono[T]: Bijection[CompletableFuture[T], Mono[T]] =
    new AbstractBijection[CompletableFuture[T], Mono[T]] {
      override def apply(f: CompletableFuture[T]): Mono[T] = Mono.fromFuture(f)
      override def invert(m: Mono[T]): CompletableFuture[T] = m.toFuture
    }

  def futureToCompletableFuture[T]: Bijection[Future[T], CompletableFuture[T]] =
    new AbstractBijection[Future[T], CompletableFuture[T]] {
      override def apply(f: Future[T]): CompletableFuture[T] = CompletableFuture.supplyAsync(() => {
        def uglyThinkBecauseInteropWithJava() = {
          try
            f.get
          catch {
            case e@(_: InterruptedException | _: ExecutionException) =>
              throw new RuntimeException(e);
          }
        }

        uglyThinkBecauseInteropWithJava()
      })
      override def invert(c: CompletableFuture[T]): Future[T] = c
    }
}
