package taller

import RiegoBase._

class RiegoCostos {

  // 1. costoRiegoFinca
  // Suma el costo de todos los tablones usando foldLeft
  def costoRiegoFinca(f: Finca, order: ProgRiego): Int = {
    val indices = (0 until f.length).toVector

    indices.foldLeft(0) { (acc, i) =>
      acc + costoRiegoTablon(i, f, order)
    }
  }


  // 2. costoMovilidad
  // Suma DF[π(j)][π(j+1)]
  def costoMovilidad(f: Finca, order: ProgRiego, d: Distancia): Int = {

    def recorrer(j: Int): Int = {
      if (j >= order.length - 1) 0
      else {
        val actual = order(j)
        val siguiente = order(j + 1)
        d(actual)(siguiente) + recorrer(j + 1)
      }
    }

    recorrer(0)
  }

}
