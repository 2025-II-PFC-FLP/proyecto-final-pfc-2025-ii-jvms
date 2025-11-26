package taller

class RiegoCostos {


  // 1. costoRiegoFinca

  def costoRiegoFinca(f: RiegoBase.Finca, order: RiegoBase.ProgRiego): Int = {

    val indices = (0 until f.length).toVector

    indices.foldLeft(0) { (acc, i) =>
      acc + RiegoBase.costoRiegoTablon(i, f, order)
    }
  }


  // 2. costoMovilidad

  def costoMovilidad(
                      f: RiegoBase.Finca,
                      order: RiegoBase.ProgRiego,
                      d: RiegoBase.Distancia
                    ): Int = {

    // Recorrido recursivo j → j+1
    def recorrer(j: Int): Int = {
      if (j >= order.length - 1) 0
      else {
        val actual = order(j)
        val sig = order(j + 1)
        d(actual)(sig) + recorrer(j + 1)
      }
    }

    recorrer(0)
  }

}
