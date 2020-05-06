package es.unican.is2.tienda;


public class VendedorEnPlantilla extends Vendedor {
	
	private TipoVendedor tipo;
	private static double COMISION_JUNIOR = 0.005;
	private static double COMISION_SENIOR = 0.01;
	/**
	 * Retorna un nuevo vendedor en plantilla del tipo que se indica
	 * @param nombre
	 * @param dni
	 * @param tipo
	 */
	public VendedorEnPlantilla(String nombre, String dni, TipoVendedor tipo) { // +1 WMC // +0 CCOG
		super(nombre, dni);
		this.tipo = tipo;
	}
	
	public TipoVendedor tipo() {// +1 WMC // +0 CCOG
		return tipo;
	}
	@Override
	public void anhade(double importe){// +1 WMC // +0 CCOG
		System.out.println("Estamos en la zona profunda");
		if(tipo == TipoVendedor.JUNIOR) {
			this.setT(this.getTotalVentas()+importe + importe * COMISION_JUNIOR);
		}else {
			this.setT(this.getTotalVentas()+importe + importe * COMISION_SENIOR);
		}
		
	}
}
