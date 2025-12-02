package taller

import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TestRiegoParalelo extends AnyFunSuite {

  import RiegoBase._
  import taller.RiegoParalelo._
  import taller.RiegoPermutaciones._

  // ============
  // TESTS costoRiegoFincaPar
  // ============
  test("costoRiegoFincaPar - coincide con versión secuencial en caso simple") {

    val f: Finca = Vector(
      (10, 2, 1),
      (14, 4, 3),
      (20, 3, 2)
    )

    val order: ProgRiego = Vector(0, 2, 1)
    val rc = new RiegoCostos()

    val esperado = rc.costoRiegoFinca(f, order)
    val paralelo = costoRiegoFincaPar(f, order)

    assert(paralelo == esperado)
  }


  // ============
  // TESTS costoMovilidadPar
  // ============
  test("costoMovilidadPar - coincide con versión secuencial") {

    val f: Finca = Vector((0,0,0),(0,0,0),(0,0,0))

    val order: ProgRiego = Vector(1, 0, 2)

    val d: Distancia = Vector(
      Vector(0, 5, 4),
      Vector(5, 0, 6),
      Vector(4, 6, 0)
    )

    val rc = new RiegoCostos()

    val esperado = rc.costoMovilidad(f, order, d)
    val paralelo = costoMovilidadPar(f, order, d)

    assert(paralelo == esperado)
  }


  // ============
  // TESTS generarProgramacionesRiegoPar
  // ============
  test("generarProgramacionesRiegoPar - mismas permutaciones que secuencial (n=3)") {

    val f: Finca = Vector(
      (5, 1, 1),
      (7, 1, 1),
      (6, 1, 1)
    )

    val sec = generarProgramacionesRiego(f).toSet
    val par = generarProgramacionesRiegoPar(f).toSet

    assert(sec == par)
  }


  // ============
  // TESTS ProgramacionRiegoOptimoPar
  // ============
  test("ProgramacionRiegoOptimoPar - mismo costo óptimo que el secuencial") {

    val f: Finca = Vector(
      (10, 3, 2),
      (8, 2, 1),
      (12, 4, 3)
    )

    val d: Distancia = Vector(
      Vector(0, 3, 5),
      Vector(3, 0, 4),
      Vector(5, 4, 0)
    )

    val (progSeq, costoSeq) = ProgramacionRiegoOptimo(f, d)
    val (progPar, costoPar) = ProgramacionRiegoOptimoPar(f, d)

    // Si hay empates, basta comparar costos
    assert(costoPar == costoSeq)
  }


  // ============
  // TESTS Casos borde
  // ============
  test("Funciones paralelas - casos borde: finca vacía y finca de un tablón") {

    // ------ Finca vacía ------
    val f0: Finca = Vector.empty
    val d0: Distancia = Vector.empty

    val (pSeq0, cSeq0) = ProgramacionRiegoOptimo(f0, d0)
    val (pPar0, cPar0) = ProgramacionRiegoOptimoPar(f0, d0)

    assert(pSeq0 == pPar0)
    assert(cSeq0 == cPar0)

    // ------ Finca de un tablón ------
    val f1: Finca = Vector((15, 3, 2))
    val d1: Distancia = Vector(Vector(0))

    val (pSeq1, cSeq1) = ProgramacionRiegoOptimo(f1, d1)
    val (pPar1, cPar1) = ProgramacionRiegoOptimoPar(f1, d1)

    assert(pSeq1 == pPar1)
    assert(cSeq1 == cPar1)
  }

}