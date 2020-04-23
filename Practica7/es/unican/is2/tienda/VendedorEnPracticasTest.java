package es.unican.is2.tienda;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import es.unican.is2.tienda.TipoVendedor;
import es.unican.is2.tienda.VendedorEnPlantilla;


public class VendedorEnPracticasTest {
	
	private static VendedorEnPlantilla junior;
	private static VendedorEnPlantilla senior;

	
	@Before
	public void setUp(){
		junior = new VendedorEnPlantilla("Ana", "12345678A", TipoVendedor.JUNIOR);
		senior = new VendedorEnPlantilla("Pepe", "87654321A", TipoVendedor.SENIOR);
	}
	
	@Test
	public void testConstructor() {
		assertEquals(junior.getDni(), "12345678A");
		assertEquals(junior.getNombre(), "Ana");
	}

	@Test
	public void testAnhadeVenta() {
		
		junior.anhadeVenta(200);
		assertEquals(junior.getTotalVentas(), 200, 0);
		assertEquals(junior.getCom(), 1, 0.1);
		
		junior.anhadeVenta(300);
		assertEquals(junior.getTotalVentas(), 500, 0);
		assertEquals(junior.getCom(), 2.5, 0.1);
		
		senior.anhadeVenta(200);
		assertEquals(senior.getTotalVentas(), 200, 0);
		assertEquals(senior.getCom(), 2, 0.1);
		
	}
	
	@Test
	public void testSetTotalVentas() {
		
		junior.setTotalVentas(2000);
		assertEquals(junior.getTotalVentas(), 2000, 0);
		assertEquals(junior.getCom(), 10, 0.1);
		
		senior.setTotalVentas(4500);
		assertEquals(senior.getTotalVentas(), 4500, 0);
		assertEquals(senior.getCom(), 45, 0.1);
		
	}

}
