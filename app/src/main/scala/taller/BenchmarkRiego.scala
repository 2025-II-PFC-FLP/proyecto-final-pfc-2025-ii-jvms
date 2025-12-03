package taller

import org.scalameter._
import RiegoBase._
import RiegoPermutaciones._
import RiegoParalelo._

/*
 * Pequeño benchmarking usando ScalaMeter.
 * Compara tiempos secuenciales vs paralelos para distintos tamaños n.
 */
object BenchmarkRiego {

  // Genera una finca aleatoria coherente con el modelo.
  def fincaAleatoria(n: Int): Finca =
    (0 until n).map { _ =>
      val ts = 30 + util.Random.nextInt(20)   // tiempo límite
      val tr = 5 + util.Random.nextInt(10)    // tiempo de riego
      val p  = 2 + util.Random.nextInt(5)     // penalización
      (ts, tr, p)
    }.toVector

  // Distancias aleatorias simétricas sin restricciones particulares.
  def distanciaAleatoria(n: Int): Distancia =
    (0 until n).map { _ =>
      (0 until n).map { _ =>
        util.Random.nextInt(15)
      }.toVector
    }.toVector

  /*
   * Ejecuta el benchmark variando n.
   * Se imprime el tiempo secuencial, paralelo y el speedup.
   */
  def main(args: Array[String]): Unit = {

    val sizes = List(6, 7, 8)  // tamaños manejables para permutaciones

    val rc = new RiegoCostos()

    for (n <- sizes) {
      val f = fincaAleatoria(n)
      val d = distanciaAleatoria(n)

      println(s"\n------- n = $n --------")

      val timeSeq = measure {
        ProgramacionRiegoOptimo(f, d)
      }

      val timePar = measure {
        ProgramacionRiegoOptimoPar(f, d)
      }

      println(s"Secuencial: $timeSeq")
      println(s"Paralelo:   $timePar")

      // Speedup aproximado basado en el valor medido
      println(s"Aceleración: ${(timeSeq.value / timePar.value)}x")
    }
  }

}

