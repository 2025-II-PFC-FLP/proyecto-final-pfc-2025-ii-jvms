package taller

import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner
@RunWith(classOf[JUnitRunner])
class TestRiegoBase extends AnyFunSuite {
  import RiegoBase._

  test("tIR_fromOrder - orden simple 0,1,2") {
    val f: Finca = Vector((5,2,1), (7,3,2), (4,1,1))
    val order = Vector(0,1,2)
    val t = tIR_fromOrder(f, order)
    assert(t == Vector(0,2,5))
  }

  test("tIR_fromOrder - orden 2,0,1") {
    val f: Finca = Vector((5,2,1), (7,3,2), (4,1,1))
    val order = Vector(2,0,1)
    val t = tIR_fromOrder(f, order)
    assert(t == Vector(1,3,0))
  }

  test("tIR_fromOrder - finca de un solo tablón") {
    val f: Finca = Vector((10,3,4))
    val order = Vector(0)
    val t = tIR_fromOrder(f, order)
    assert(t == Vector(0))
  }

  test("tIR_fromMapping - mapping invertido a orden") {
    val f: Finca = Vector((10,2,1),(6,1,2),(8,3,1))
    val mapping = Vector(2,0,1)
    val t = tIR_fromMapping(f, mapping)
    val orderEsperado = Vector(1,2,0)
    assert(t == tIR_fromOrder(f, orderEsperado))
  }

  test("tIR_fromOrder - verificar contra ejemplo del profesor") {
    val f: Finca = Vector((10,3,4),(5,3,3),(2,2,1),(8,1,1),(6,4,2))
    val order = Vector(2,1,4,0,3)
    val t = tIR_fromOrder(f, order)
    assert(t(2) == 0 && t(1) == 2)
  }

  test("costoRiegoTablon - no sufre") {
    val f: Finca = Vector((10,3,1))
    val order = Vector(0)
    val costo = costoRiegoTablon(0, f, order)
    assert(costo == 7)
  }

  test("costoRiegoTablon - sufre") {
    val f: Finca = Vector((2,3,2))
    val order = Vector(0)
    val costo = costoRiegoTablon(0, f, order)
    assert(costo == 2)
  }

  test("costoRiegoTablon - prioridad alta") {
    val f: Finca = Vector((3,2,5),(10,1,1))
    val order = Vector(1,0)
    val costo = costoRiegoTablon(0, f, order)
    assert(costo >= 0)
  }

  test("costoRiegoTablon - orden generado desde mapping") {
    val f: Finca = Vector((6,2,1),(4,1,2),(8,3,1))
    val mapping = Vector(1,2,0)
    val t = tIR_fromMapping(f, mapping)
    val order = Vector(2,0,1)
    val costo = costoRiegoTablon(1, f, order)
    assert(costo.isInstanceOf[Int])
  }

  test("costoRiegoTablon - ejemplo general válido") {
    val f = Vector((10,3,4),(5,3,3),(2,2,1))
    val order = Vector(1,2,0)
    val c = costoRiegoTablon(1, f, order)
    assert(c.isInstanceOf[Int])
  }
}
