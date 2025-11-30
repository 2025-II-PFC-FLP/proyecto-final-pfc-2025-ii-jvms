package taller
import RiegoBase._

object RiegoPermutaciones {

  // Generador recursivo de permutaciones (orden = turno -> idTablon).
  def generarProgramacionesRiego(f: Finca): Vector[ProgRiego] = {
    val n = f.length
    // Si la finca está vacía devolvemos un vector vacío de programaciones
    if (n == 0) Vector(Vector.empty[Int])
    else {
      // lista inicial de IDs
      val ids = (0 until n).toVector

      // función recursiva: prefix = perm parcial (turnos ya asignados),
      // rem = ids restantes por asignar
      def perms(prefix: ProgRiego, rem: Vector[Int]): Vector[ProgRiego] = {
        if (rem.isEmpty) Vector(prefix)
        else {
          // para cada elemento en rem, lo añadimos al prefijo y continuamos
          // usamos recursion explícita y operaciones funcionales
          rem.zipWithIndex.foldLeft(Vector.empty[ProgRiego]) {
            case (acc, (id, idx)) =>
              val remSin = rem.patch(idx, Nil, 1) // rem sin el id seleccionado
              val nuevas = perms(prefix :+ id, remSin)
              acc ++ nuevas
          }
        }
      }

      perms(Vector.empty[Int], ids)
    }
  }

  // Dada la finca y la matriz de distancias devuelve (programación óptima, costo)
  def ProgramacionRiegoOptimo(f: Finca, d: Distancia): (ProgRiego, Int) = {
    // Caso vacio
    if (f.isEmpty) return (Vector.empty[Int], 0)

    // generamos programaciones (recursivas)
    val programaciones = generarProgramacionesRiego(f)

    // instancia para usar los métodos de costos ya implementados
    val rc = new RiegoCostos()

    // recorrer todas las programaciones y elegir la de menor costo total
    // (costo riego finca + costo movilidad)
    val (mejorProg, mejorCosto) = programaciones.foldLeft((Vector.empty[Int], Int.MaxValue)) {
      case ((bestProg, bestCost), prog) =>
        val cr = rc.costoRiegoFinca(f, prog)
        val cm = rc.costoMovilidad(f, prog, d)
        val total = cr + cm
        if (total < bestCost) (prog, total) else (bestProg, bestCost)
    }

    // si mejorCosto quedó Int.MaxValue (no debería si f != empty) devolvemos (vacio,0)
    if (mejorCosto == Int.MaxValue) (Vector.empty[Int], 0)
    else (mejorProg, mejorCosto)

  }

}
