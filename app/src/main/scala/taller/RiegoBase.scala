package taller

object RiegoBase {

  type Tablon     = (Int, Int, Int)
  type Finca      = Vector[Tablon]
  type Distancia  = Vector[Vector[Int]]
  type ProgRiego  = Vector[Int]
  type TiempoInicioRiego = Vector[Int]

  // Calcula el tiempo de inicio de riego siguiendo una permutación en orden.
  def tIR_fromOrder(f: Finca, order: ProgRiego): TiempoInicioRiego = {
    val n = f.length // Número de tablones

    // Recorremos la permutación acumulando el tiempo total.
    val (_, tiempos) =
      order.foldLeft((0, Vector.fill(n)(0))) {
        case ((tiempoActual, acc), idTablon) =>

          // Asignamos el tiempo de inicio para el tablón actual.
          val acc2 = acc.updated(idTablon, tiempoActual)

          // Tiempo de riego del tablón
          val tr = f(idTablon)._2

          // El siguiente tiempo acumulado suma el tiempo de riego del tablón.
          (tiempoActual + tr, acc2)
      }

    // Vector final con los tiempos de inicio
    tiempos
  }

  // Convierte un vector mapping (v(i) = turno) en una permutación en orden.
  def tIR_fromMapping(f: Finca, mapping: ProgRiego): TiempoInicioRiego = {
    val n = f.length
    val orderInit = Vector.fill(n)(-1)

    // Construimos la permutación real: order(turno) = idTablon
    val order =
      (0 until n).foldLeft(orderInit) { (acc, idTablon) =>
        val turno = mapping(idTablon)
        acc.updated(turno, idTablon)
      }

    // Reutilizamos la función principal
    tIR_fromOrder(f, order)
  }

  def tIR(f: Finca, order: ProgRiego): TiempoInicioRiego =
    tIR_fromOrder(f, order)

  // Calcula el costo de riego para un tablón según sufrimiento o no.
  def costoRiegoTablon(i: Int, f: Finca, order: ProgRiego): Int = {

    // Tiempo en que inicia el riego del tablón i.
    val tiempos = tIR_fromOrder(f, order)
    val t_i = tiempos(i)

    // Extraemos ts_i, tr_i, p_i
    val (ts_i, tr_i, p_i) = f(i)

    // Aplicamos la fórmula según si el cultivo sufre o no.
    if (ts_i - tr_i >= t_i)
      ts_i - (t_i + tr_i) // Caso donde NO sufre
    else
      p_i * ((t_i + tr_i) - ts_i) // Caso donde SÍ sufre
  }
}


