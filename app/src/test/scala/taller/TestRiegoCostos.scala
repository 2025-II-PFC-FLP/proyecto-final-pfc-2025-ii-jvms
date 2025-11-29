package taller

import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TestRiegoCostos extends AnyFunSuite {

  import RiegoBase._

  val rc = new RiegoCostos()

  //  Tests costoRiegoFinca

  test("costoRiegoFinca - finca de un solo tablón") {
    val f: Finca = Vector((10, 3, 2))
    val order = Vector(0)
    val c = rc.costoRiegoFinca(f, order)
    assert(c == costoRiegoTablon(0, f, order))
  }

  test("costoRiegoFinca - dos tablones sin sufrimiento") {
    val f: Finca = Vector((10, 2, 1), (8, 2, 1))
    val order = Vector(0, 1)
    val c = rc.costoRiegoFinca(f, order)
    assert(c.isInstanceOf[Int])
  }

  test("costoRiegoFinca - mezcla de sufrimiento") {
    val f: Finca = Vector(
      (5, 3, 2),
      (2, 1, 3),
      (6, 4, 1)
    )
    val order = Vector(1, 0, 2)
    val c = rc.costoRiegoFinca(f, order)
    assert(c.isInstanceOf[Int])
  }

  test("costoRiegoFinca") {
    val f = Vector(
      (10, 3, 4),
      (5, 3, 3),
      (2, 2, 1),
      (8, 1, 1),
      (6, 4, 2)
    )
    val order = Vector(2, 1, 4, 0, 3)
    val c = rc.costoRiegoFinca(f, order)
    assert(c.isInstanceOf[Int])
  }

  test("costoRiegoFinca - permutación invertida") {
    val f = Vector(
      (7, 2, 1),
      (4, 1, 2),
      (9, 3, 3)
    )
    val order = Vector(2, 1, 0)
    val c = rc.costoRiegoFinca(f, order)
    assert(c >= 0)
  }



  //  Tests costoMovilidad


  test("costoMovilidad - finca de 1 tablón (costo 0)") {
    val f: Finca = Vector((5, 2, 1))
    val d: Distancia = Vector(Vector(0))
    val order = Vector(0)
    val c = rc.costoMovilidad(f, order, d)
    assert(c == 0)
  }
  test("costoMovilidad - dos tablones distancia básica") {
    val f: Finca = Vector((5, 2, 1), (7, 3, 2))
    val d: Distancia = Vector(
      Vector(0, 4),
      Vector(4, 0)
    )
    val order = Vector(0, 1)
    val c = rc.costoMovilidad(f, order, d)
    assert(c == 4)
  }

  test("costoMovilidad - tres tablones con matriz simple") {
    val f = Vector((6,2,1),(4,1,2),(8,3,1))
    val d = Vector(
      Vector(0,2,3),
      Vector(2,0,1),
      Vector(3,1,0)
    )
    val order = Vector(0,2,1)
    val c = rc.costoMovilidad(f, order, d)
    assert(c.isInstanceOf[Int])
  }

  test("costoMovilidad - distancias variables") {
    val f = Vector((3,1,1),(6,2,2),(2,1,1),(7,3,3))
    val d = Vector(
      Vector(0,5,2,4),
      Vector(5,0,3,1),
      Vector(2,3,0,6),
      Vector(4,1,6,0)
    )
    val order = Vector(3,1,0,2)
    val c = rc.costoMovilidad(f, order, d)
    assert(c.isInstanceOf[Int])
  }

  test("costoMovilidad") {
    val f = Vector(
      (10, 3, 4),
      (5, 3, 3),
      (2, 2, 1),
      (8, 1, 1),
      (6, 4, 2)
    )
    val d = Vector(
      Vector(0, 2, 2, 4, 4),
      Vector(2, 0, 4, 2, 6),
      Vector(2, 4, 0, 2, 2),
      Vector(4, 2, 2, 0, 4),
      Vector(4, 6, 2, 4, 0)
    )
    val order = Vector(0,1,4,2,3)
    val c = rc.costoMovilidad(f, order, d)
    assert(c.isInstanceOf[Int])
  }

}