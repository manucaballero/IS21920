package es.unican.is2.tienda;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import es.unican.is2.tienda.vendedorEnPracticas;


public class VendedorEnPlantillaTest {
	
	private static VendedorEnPlantilla sutJunior;
	private static VendedorEnPlantilla sutSenior;

	
	@Before
	public void setUp(){
		sutJunior = new VendedorEnPlantilla("Ana", "1", TipoVendedor.JUNIOR);
		sutSenior = new VendedorEnPlantilla("Pepe", "2", TipoVendedor.SENIOR);
	}
	
	@Test
	public void testConstructor() {
		assertEquals(sutJunior.getId(), "1");
		assertEquals(sutJunior.getNombre(), "Ana");
		assertEquals(sutJunior.tipo(), TipoVendedor.JUNIOR);
		assertEquals(sutSenior.tipo(), TipoVendedor.SENIOR);
		
	}

	@Test
	public void testAnhadeVenta() {
		
		sutJunior.anhade(200);
		assertEquals(sutJunior.getTotalVentas(), 200, 0);
		
		sutJunior.anhade(300);
		assertEquals(sutJunior.getTotalVentas(), 500, 0);
		
		sutSenior.anhade(300);
		assertEquals(sutSenior.getTotalVentas(), 300, 0);
		
		sutSenior.anhade(300);
		assertEquals(sutSenior.getTotalVentas(), 600, 0);
		
	}
	
	@Test
	public void testSetTotalVentas() {
		
		sutJunior.setT(2000);
		assertEquals(sutJunior.getTotalVentas(), 2000, 0);	
		sutJunior.setT(4000);
		assertEquals(sutJunior.getTotalVentas(), 4000, 0);	
		sutJunior.setT(0);
		assertEquals(sutJunior.getTotalVentas(), 0, 0);
		
		sutSenior.setT(4500);
		assertEquals(sutSenior.getTotalVentas(), 4500, 0);		
		sutSenior.setT(4000);
		assertEquals(sutSenior.getTotalVentas(), 4000, 0);
		sutJunior.setT(0);
		assertEquals(sutJunior.getTotalVentas(), 0, 0);	
		
	}

	
	@Test
	public void testEquals() {
		VendedorEnPlantilla igualJunior = new VendedorEnPlantilla("Ana", "1", TipoVendedor.JUNIOR);
		VendedorEnPlantilla distintoIdJunior = new VendedorEnPlantilla("Ana", "2", TipoVendedor.JUNIOR);
		VendedorEnPlantilla distintoNombreJunior = new VendedorEnPlantilla("Pepe", "1", TipoVendedor.JUNIOR);
		VendedorEnPlantilla distintoJunior = new VendedorEnPlantilla("Pepe", "6", TipoVendedor.JUNIOR);
		
		assertTrue(sutJunior.equals(igualJunior));
		assertFalse(sutJunior.equals(distintoIdJunior));
		assertFalse(sutJunior.equals(distintoNombreJunior));
		assertFalse(sutJunior.equals(distintoJunior));
		
		
		VendedorEnPlantilla igualSenior = new VendedorEnPlantilla("Pepe", "2", TipoVendedor.SENIOR);
		VendedorEnPlantilla distintoIdSenior = new VendedorEnPlantilla("Pepe", "3", TipoVendedor.SENIOR);
		VendedorEnPlantilla distintoNombreSenior = new VendedorEnPlantilla("Ana", "2", TipoVendedor.SENIOR);
		VendedorEnPlantilla distintoSenior = new VendedorEnPlantilla("Juan", "6", TipoVendedor.SENIOR);
		
		assertTrue(sutSenior.equals(igualSenior));
		assertFalse(sutSenior.equals(distintoIdSenior));
		assertFalse(sutSenior.equals(distintoNombreSenior));
		assertFalse(sutSenior.equals(distintoSenior));
		
		
		
		
	}
	
	
	
}
