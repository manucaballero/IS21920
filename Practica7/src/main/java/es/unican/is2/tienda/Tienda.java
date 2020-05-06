package es.unican.is2.tienda;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Clase que representa una tienda con un conjunto de vendedores y que permite
 * llevar la gesti�n de las ventas realizadas y las comisiones asignadas a cada
 * vendedor. Los datos de la tienda se almacenan en un fichero de texto
 * que se pasa como par�metro al crear la tienda
 */
public class Tienda {

	private LinkedList<Vendedor> lista = new LinkedList<Vendedor>();
	private String direccion;
	private String nombre;

	private String datos;

	/**
	 * Crea la tienda cargando los datos desde el fichero indicado
	 * 
	 * @param datos Path absoluto del fichero de datos
	 */
	public Tienda(String datos) {// +1 WMC // +0 CCOG
		this.datos = datos;
	}

	/**
	 * Retorna la direcci�n de la tienda
	 * @return Direcci�n de la tienda
	 */
	public String direccion() {// +1 WMC // +0 CCOG
		return direccion;
	}

	/**
	 * Retorna el nombre de la tienda
	 * @return Nombre de la tienda
	 */
	public String nombre() {// +1 WMC // +0 CCOG
		return nombre;
	}

	/**
	 * A�ade un nuevo vendedor a la tienda
	 * 
	 * @param nuevoVendedor
	 * @return true si el vendedor se ha anhadido 
	 *         false si ya hab�a un vendedor con el mismo id
	 */
	public boolean anhade(Vendedor nuevoVendedor) throws IOException {// +1 WMC // +0 CCOG
		Vendedor v = buscaVendedor(nuevoVendedor.getId());
		if (v != null) {// +1 WMC // +1 CCOG
			return false;
		}
		lista.add(nuevoVendedor);
		vuelcaDatos();
		return true;
	}

	/**
	 * Elimina el vendedor cuyo dni se pasa como par�metro
	 * 
	 * @param id
	 * @return true si se elimina el vendedor 
	 *         false si no existe ning�n vendedor con el id indicado
	 */
	public boolean eliminaVendedor(String id) throws IOException {// +1 WMC // +0 CCOG
		Vendedor v = buscaVendedor(id);
		if (v == null) {// +1 WMC // +1 CCOG
			return false;
		}
		lista.remove(v);
		vuelcaDatos();
		return true;
	}

	/**
	 * A�ade una venta a un vendedor
	 * 
	 * @param id
	 *            Id del vendedor
	 * @param importe
	 *            Importe de la venta
	 * @return true si se a�ade la venta false si no se encuentra el vendedor
	 */
	public boolean anhadeVenta(String id, double importe) throws IOException {// +1 WMC // +0 CCOG
		Vendedor v = buscaVendedor(id);
		if (v == null) {// +1 WMC // +1 CCOG
			return false;
		}
		if (v instanceof VendedorEnPlantilla) {// +1 WMC // +1 CCOG
			VendedorEnPlantilla v1 = (VendedorEnPlantilla) v;	
			v1.anhade(importe);
		}else {
			v.anhade(importe);
		}
		vuelcaDatos();
		return true;
	}

	/**
	 * Retorna el vendedor con el id indicado
	 * 
	 * @param id
	 *            Id del vendedor
	 * @return vendedor con ese id o null si no existe ninguno
	 */
	public Vendedor buscaVendedor(String id) {// +1 WMC // +0 CCOG

		lista = new LinkedList<Vendedor>();
		lista = (LinkedList<Vendedor>) vendedores();

		for (Vendedor v : lista) {// +1 WMC // +1 CCOG
			if (v.getId().equals(id)) {// +1 WMC // +2 CCOG
				return v;
			}
		}
		return null;
	}

	

	/**
	 * Retorna la lista de vendedores actuales de la tienda
	 * 
	 * @return La lista de vendedores
	 */
	public List<Vendedor> vendedores() {// +1 WMC // +0 CCOG
		lista = new LinkedList<Vendedor>();

		Scanner in = null;
		try {
			// abre el fichero
			in = new Scanner(new FileReader(datos));
			// configura el formato de n�meros
			in.useLocale(Locale.ENGLISH);
			nombre = in.nextLine();
			direccion = in.nextLine();
			in.next();
			Vendedor ven = null;
			// lee los vendedores senior
			while (in.hasNext() && !in.next().equals("Junior")) {// +1 WMC // +1 CCOG

				String nombre = in.next();
				in.next();
				String id = in.next();
				in.next();
				double totalVentas = in.nextDouble();
				ven = new VendedorEnPlantilla(nombre, id, TipoVendedor.SENIOR);
				ven.setT(totalVentas);
				lista.add(ven);
			}
			// lee los vendedores junior
			while (in.hasNext() && !in.next().equals("Practicas")) {// +1 WMC // +1 CCOG
				String nombre = in.next();
				in.next();
				String id = in.next();
				in.next();
				double totalVentas = in.nextDouble();
				ven = new VendedorEnPlantilla(nombre, id, TipoVendedor.JUNIOR);
				ven.setT(totalVentas);
				lista.add(ven);
			}
			while (in.hasNext()) {// +1 WMC // +1 CCOG
				in.next();
				String nombre = in.next();
				in.next();
				String id = in.next();
				in.next();
				double totalVentas = in.nextDouble();
				ven = new vendedorEnPracticas(nombre, id);
				ven.setT(totalVentas);
				lista.add(ven);
			}
		} catch (FileNotFoundException e) {// +1 WMC // +1 CCOG

		} finally {
			if (in != null) {// +1 WMC // +1 CCOG
				in.close();
			}
		} // try

		return lista;

	}

	/**
	 * Metodo que genera el fichero datosTienda.txt con los datos actualizados de
	 * los vendedores
	 */
	private void vuelcaDatos() throws IOException {// +1 WMC // +0 CCOG
		PrintWriter out = null;
		List<Vendedor> senior = new LinkedList<Vendedor>();
		List<Vendedor> junior = new LinkedList<Vendedor>();
		List<Vendedor> practicas = new LinkedList<Vendedor>();

		for (Vendedor v : lista) {// +1 WMC // +1 CCOG
			if (v instanceof vendedorEnPracticas) {// +1 WMC // +2 CCOG
				practicas.add(v);
			} else if (v instanceof VendedorEnPlantilla) {// +1 WMC // +3 CCOG
				VendedorEnPlantilla vp = (VendedorEnPlantilla) v;
				if (vp.tipo().equals(TipoVendedor.JUNIOR))// +1 WMC // +4 CCOG
					junior.add(vp);
				else
					senior.add(vp);
			}
		}

		try {

			out = new PrintWriter(new FileWriter(datos));

			out.println(nombre);
			out.println(direccion);
			out.println();
			out.println("    Senior");
			for (Vendedor v1 : senior)// +1 WMC // +1 CCOG
				out.println("      Nombre: " + v1.getNombre() + "   Id: " + v1.getId() + "   TotalVentasMes: "
						+ v1.getTotalVentas());
			out.println();
			out.println("    Junior");
			for (Vendedor v2 : junior)// +1 WMC // +1 CCOG
				out.println("      Nombre: " + v2.getNombre() + "   Id: " + v2.getId() + "   TotalVentasMes: "
						+ v2.getTotalVentas());
			out.println();
			out.println("    Practicas");
			for (Vendedor v3 : practicas)// +1 WMC // +1 CCOG
				out.println("      Nombre: " + v3.getNombre() + "   Id: " + v3.getId() + "   TotalVentasMes: "
						+ v3.getTotalVentas());

		} finally {
			if (out != null)// +1 WMC // +1 CCOG
				out.close();
		}
	}

}
