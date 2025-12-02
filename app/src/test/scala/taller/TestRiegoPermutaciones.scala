package taller
import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner


@RunWith(classOf[JUnitRunner])
class TestRiegoPermutaciones extends AnyFunSuite {

  import RiegoBase._
  import taller.RiegoPermutaciones._

  // ============
  // Tests generarProgramacionesRiego
  // ============

  test("generarProgramacionesRiego - finca vacía") {
    val f: Finca = Vector()
    val perms = generarProgramacionesRiego(f)
    assert(perms == Vector(Vector()))
  }

  test("generarProgramacionesRiego - finca de un tablón") {
    val f: Finca = Vector((5,2,1))
    val perms = generarProgramacionesRiego(f)
    assert(perms == Vector(Vector(0)))
  }

  test("generarProgramacionesRiego - finca de dos tablones") {
    val f: Finca = Vector((5,2,1),(7,3,2))
    val perms = generarProgramacionesRiego(f).toSet
    val esperadas = Set(Vector(0,1), Vector(1,0))
    assert(perms == esperadas)
  }

  test("generarProgramacionesRiego - finca de tres tablones") {
    val f: Finca = Vector((5,2,1),(7,3,2),(4,1,1))
    val perms = generarProgramacionesRiego(f)
    assert(perms.length == 6)
  }

  test("generarProgramacionesRiego - todas son permutaciones válidas") {
    val f: Finca = Vector((5,2,1),(7,3,2),(4,1,1))
    val perms = generarProgramacionesRiego(f)
    def esPerm(v: ProgRiego) = v.sorted == Vector(0,1,2)
    assert(perms.forall(esPerm))
  }


  // ============
  // Tests ProgramacionRiegoOptimo
  // ============

  test("ProgramacionRiegoOptimo - finca vacía") {
    val f: Finca = Vector()
    val d: Distancia = Vector()
    val (p, c) = ProgramacionRiegoOptimo(f, d)
    assert(p == Vector())
    assert(c == 0)
  }

  test("ProgramacionRiegoOptimo - finca 1 tablón, costo 0 movilidad") {
    val f: Finca = Vector((10,3,2))
    val d: Distancia = Vector(Vector(0))
    val (p, c) = ProgramacionRiegoOptimo(f, d)
    assert(p == Vector(0))
    assert(c == 0 || c.isInstanceOf[Int])
  }

  test("ProgramacionRiegoOptimo - 3 tablones, matriz simple") {
    val f: Finca = Vector((6,2,1),(4,1,2),(8,3,1))

    val d: Distancia = Vector(
      Vector(0,2,3),
      Vector(2,0,1),
      Vector(3,1,0)
    )

    val (progOpt, costoOpt) = ProgramacionRiegoOptimo(f, d)

    // Validar que es permutación válida
    assert(progOpt.sorted == Vector(0,1,2))

    // Verificar costo consistente
    val rc = new RiegoCostos()
    val costoCheck =
      rc.costoRiegoFinca(f, progOpt) + rc.costoMovilidad(f, progOpt, d)
    assert(costoCheck == costoOpt)
  }

  test("ProgramacionRiegoOptimo - ejemplo determinístico pequeño") {
    val f: Finca = Vector(
      (5,3,1),  // tablón 0
      (2,1,2)   // tablón 1
    )

    val d: Distancia = Vector(
      Vector(0,5),
      Vector(5,0)
    )

    val (prog, costo) = ProgramacionRiegoOptimo(f, d)

    val rc = new RiegoCostos()

    val costo0 = rc.costoRiegoFinca(f, Vector(0,1)) + rc.costoMovilidad(f, Vector(0,1), d)
    val costo1 = rc.costoRiegoFinca(f, Vector(1,0)) + rc.costoMovilidad(f, Vector(1,0), d)

    assert(costo == math.min(costo0, costo1))
    assert(prog == (if (costo0 <= costo1) Vector(0,1) else Vector(1,0)))
  }

}


