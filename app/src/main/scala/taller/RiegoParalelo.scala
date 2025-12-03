package taller

import scala.collection.parallel.CollectionConverters._
import RiegoBase._

/*
 * Implementación paralela de las funciones del sistema de riego.
 * Se busca acelerar la evaluación explotando paralelismo en listas,
 * especialmente en tareas independientes por tablón o por transición.
 */
object RiegoParalelo {

  // Se reutiliza la clase de costos ya existente.
  private val rc = new RiegoCostos()


  // 1. costoRiegoFincaPar
  /*
   * Calcula el costo de riego total en paralelo.
   * Cada tablón puede evaluarse independientemente y se paraleliza el map.
   */
  def costoRiegoFincaPar(f: Finca, order: ProgRiego): Int = {

    (0 until f.length).toVector.par
      .map(i => RiegoBase.costoRiegoTablon(i, f, order)) // una tarea por tablón
      .sum
  }


  // 2. costoMovilidadPar
  /*
   * Calcula la movilidad paralela dividiendo el recorrido en pares consecutivos.
   * Cada costo d(a)(b) es independiente del resto.
   */
  def costoMovilidadPar(f: Finca, order: ProgRiego, d: Distancia): Int = {

    if (order.length <= 1) 0
    else {
      // Convertimos el recorrido en pares (a,b).
      val pares =
        (0 until order.length - 1).toVector
          .map(j => (order(j), order(j + 1)))

      // Cada par es un cálculo independiente.
      pares.par
        .map { case (a, b) => d(a)(b) }
        .sum
    }
  }


  // 3. generarProgramacionesRiegoPar
  /*
   * Genera todas las permutaciones de tablones.
   * Se paraleliza solamente el nivel superior de decisiones,
   * ya que la recursión completa sería demasiado costosa.
   */
  def generarProgramacionesRiegoPar(f: Finca): Vector[ProgRiego] = {

    val n = f.length
    if (n == 0) Vector(Vector())
    else {

      val ids = (0 until n).toVector

      // Recursión estándar: 'prefix' va construyendo la permutación.
      def perms(prefix: ProgRiego, rem: Vector[Int]): Vector[ProgRiego] = {
        if (rem.isEmpty) Vector(prefix)
        else {
          // Paralelización: el branching principal se divide entre hilos.
          rem.zipWithIndex.par
            .flatMap { case (id, idx) =>
              val remSin = rem.patch(idx, Nil, 1)
              perms(prefix :+ id, remSin)
            }
            .toVector
        }
      }

      perms(Vector(), ids)
    }
  }



  // 4. ProgramacionRiegoOptimoPar
  /*
   * Evalúa todas las programaciones en paralelo y selecciona la mejor.
   * Cada permutación se trata como una tarea independiente.
   */
  def ProgramacionRiegoOptimoPar(f: Finca, d: Distancia): (ProgRiego, Int) = {

    if (f.isEmpty)
      (Vector.empty[Int], 0)

    else {
      val programaciones = generarProgramacionesRiegoPar(f)

      // Cada evaluación se hace en paralelo: riego + movilidad.
      val resultados =
        programaciones.par.map { prog =>
          val cr = costoRiegoFincaPar(f, prog)
          val cm = costoMovilidadPar(f, prog, d)
          (prog, cr + cm)
        }

      resultados.minBy(_._2) // se escoge la de menor costo total.
    }
  }

}

