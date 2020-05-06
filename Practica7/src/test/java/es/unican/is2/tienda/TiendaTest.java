package es.unican.is2.tienda;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class TiendaTest {
	private static Tienda tienda;
	@Before
	public void setUp(){
		tienda = new Tienda("datosTienda.txt");
	}
	@Test
	public void testConstructor() {
	}
	@Test
	public void testLeedatos() {
		Vendedor v = tienda.buscaVendedor("1");
		assertEquals(v.getNombre(),"Juan");
		assertEquals(v.getId(),"1");
		Vendedor v4 = tienda.buscaVendedor("4");
		assertEquals(v4.getId(),"4");
	}
	@Test
	public void testAnhadeventa() throws IOException {
		
		tienda.anhadeVenta("1", 100);
		Vendedor v = tienda.buscaVendedor("1");
		System.out.println(v.getTotalVentas());
		assertTrue(v.getTotalVentas()==1101);
		
		

	}
}
