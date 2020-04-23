package es.unican.is2.tienda;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import es.unican.is2.tienda.vendedorEnPracticas;


public class VendedorEnPlantillaTest {
	
	private static vendedorEnPracticas v;
	

	
	@Before
	public void setUp(){
		v = new vendedorEnPracticas("Ana", "12345678A");
	}
	
	@Test
	public void testConstructor() {
		assertEquals(v.getDni(), "12345678A");
		assertEquals(v.getNombre(), "Ana");
	}

	@Test
	public void testAnhadeVenta() {
		
		v.anhadeVenta(200);
		assertEquals(v.getTotalVentas(), 200, 0);
		assertEquals(v.getCom(), 0, 0.1);
		
		v.anhadeVenta(300);
		assertEquals(v.getTotalVentas(), 500, 0);
		assertEquals(v.getCom(), 0, 0.1);
		
	
	}
	
	@Test
	public void testSetTotalVentas() {
		
		v.setTotalVentas(2000);
		assertEquals(v.getTotalVentas(), 2000, 0);
		assertEquals(v.getCom(), 0, 0.1);
		
	}

}
