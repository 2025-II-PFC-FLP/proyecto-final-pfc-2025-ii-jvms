package taller

import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TestRiegoIntegracion extends AnyFunSuite {

  import RiegoBase._
  import RiegoPermutaciones._
  import RiegoParalelo._
  import RiegoIntegracion._




  test("Programación óptima en finca vacía") {
    val f = Vector.empty[Tablon]
    val d = Vector.empty[Vector[Int]]

    val seq = ProgramacionRiegoOptimo(f, d)
    val par = ProgramacionRiegoOptimoPar(f, d)

    assert(seq == par)
  }


  test("Programación óptima con un solo tablón") {
    val f = fincaDeterministica(1, seed = 10)
    val d = distanciaDeterministica(1, seed = 10)

    val seq = ProgramacionRiegoOptimo(f, d)
    val par = ProgramacionRiegoOptimoPar(f, d)

    assert(seq == par)
  }


  test("Comparación determinística con n = 4") {
    val f = fincaDeterministica(4, seed = 20)
    val d = distanciaDeterministica(4, seed = 20)

    val seq = ProgramacionRiegoOptimo(f, d)
    val par = ProgramacionRiegoOptimoPar(f, d)

    assert(seq == par)
  }

  test("Pruebas aleatorias reproducibles") {
    val resultados = ejecutarPruebasAleatorias(trials = 5, n = 5, seed = 123)
    assert(resultados.forall(_ == true))
  }

  test("Comparación general para varios tamaños") {
    for (n <- 2 to 6) {
      val f = fincaDeterministica(n, seed = n * 11)
      val d = distanciaDeterministica(n, seed = n * 17)

      val seq = ProgramacionRiegoOptimo(f, d)
      val par = ProgramacionRiegoOptimoPar(f, d)

      assert(seq == par)
    }
  }
}
