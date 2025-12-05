package taller

class RiegoCostos {


  // 1. costoRiegoFinca
  // Calcula el costo total de riego de toda la finca.
  // Recorre cada tablón por índice y suma el costo individual
  // llamando a RiegoBase.costoRiegoTablon.
  def costoRiegoFinca(f: RiegoBase.Finca, order: RiegoBase.ProgRiego): Int = {

    // Genera un vector con los índices de todos los tablones (0,1,2,...)
    val indices = (0 until f.length).toVector

    // Acumula los costos de riego por cada tablón
    indices.foldLeft(0) { (acc, i) =>
      // Suma el costo del tablón i, según el orden de riego
      acc + RiegoBase.costoRiegoTablon(i, f, order)
    }
  }



  // 2. costoMovilidad

  // Calcula el costo de movilidad entre tablones, siguiendo el orden de riego.
  // Suma distancias entre cada par consecutivo de tablones:
  //   order(j) -> order(j+1)
  def costoMovilidad(
                      f: RiegoBase.Finca,
                      order: RiegoBase.ProgRiego,
                      d: RiegoBase.Distancia
                    ): Int = {

    // Función recursiva que va sumando distancias entre pares consecutivos
    def recorrer(j: Int): Int = {

      // Caso base: cuando ya no hay un siguiente elemento
      if (j >= order.length - 1) 0

      else {
        // Tablón actual
        val actual = order(j)

        // Tablón siguiente
        val sig = order(j + 1)

        // Distancia entre ellos + llamada recursiva avanzando uno
        d(actual)(sig) + recorrer(j + 1)
      }
    }

    // Llamada inicial comenzando desde el primer elemento del orden
    recorrer(0)
  }

}