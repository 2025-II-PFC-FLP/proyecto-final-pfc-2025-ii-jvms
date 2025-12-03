package taller

import RiegoBase._
import RiegoPermutaciones._
import RiegoParalelo._

object RiegoIntegracion {


  //Generadores deterministicos


  def fincaDeterministica(n: Int, seed: Int = 0): Finca = {
    (0 until n).map { i =>
      val ts = 10 + (i * 3) % 20
      val tr = 1 + (i * 2) % 6
      val p  = 1 + (i % 4)
      (ts, tr, p)
    }.toVector
  }

  def distanciaDeterministica(n: Int, seed: Int = 0): Distancia = {
    (0 until n).map { i =>
      (0 until n).map { j =>
        if (i == j) 0 else ((i + j + seed) % 10) + 1
      }.toVector
    }.toVector
  }


  // Secuencias vs Paralelo


  def validarOptimoIgual(f: Finca, d: Distancia): Boolean = {
    val optSeq = ProgramacionRiegoOptimo(f, d)
    val optPar = ProgramacionRiegoOptimoPar(f, d)
    optSeq == optPar
  }


  //  Pruebas


  def ejecutarPruebasAleatorias(trials: Int, n: Int, seed: Int): Vector[Boolean] = {
    (0 until trials).toVector.map { t =>
      val f = fincaDeterministica(n, seed + t)
      val d = distanciaDeterministica(n, seed + t)
      validarOptimoIgual(f, d)
    }
  }
}
