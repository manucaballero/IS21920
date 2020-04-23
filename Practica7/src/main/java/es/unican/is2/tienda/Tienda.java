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
 * llevar la gestión de las ventas realizadas y las comisiones asignadas a cada
 * vendedor. Los datos de la tienda se almacenan en un fichero de texto
 * que se pasa como parámetro al crear la tienda
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
	public Tienda(String datos) {
		this.datos = datos;
	}

	/**
	 * Retorna la dirección de la tienda
	 * @return Dirección de la tienda
	 */
	public String direccion() {
		return direccion;
	}

	/**
	 * Retorna el nombre de la tienda
	 * @return Nombre de la tienda
	 */
	public String nombre() {
		return nombre;
	}

	/**
	 * Añade un nuevo vendedor a la tienda
	 * 
	 * @param nuevoVendedor
	 * @return true si el vendedor se ha anhadido 
	 *         false si ya había un vendedor con el mismo id
	 */
	public boolean anhade(Vendedor nuevoVendedor) throws IOException {
		Vendedor v = buscaVendedor(nuevoVendedor.getId());
		if (v != null) {
			return false;
		}
		lista.add(nuevoVendedor);
		vuelcaDatos();
		return true;
	}

	/**
	 * Elimina el vendedor cuyo dni se pasa como parámetro
	 * 
	 * @param id
	 * @return true si se elimina el vendedor 
	 *         false si no existe ningún vendedor con el id indicado
	 */
	public boolean eliminaVendedor(String id) throws IOException {
		Vendedor v = buscaVendedor(id);
		if (v == null) {
			return false;
		}
		lista.remove(v);
		vuelcaDatos();
		return true;
	}

	/**
	 * Añade una venta a un vendedor
	 * 
	 * @param id
	 *            Id del vendedor
	 * @param importe
	 *            Importe de la venta
	 * @return true si se añade la venta false si no se encuentra el vendedor
	 */
	public boolean anhadeVenta(String id, double importe) throws IOException {
		Vendedor v = buscaVendedor(id);
		if (v == null) {
			return false;
		}
		double importeFinal = importe;
		if (v instanceof VendedorEnPlantilla) {
			switch (((VendedorEnPlantilla) v).tipo()) {
			case JUNIOR:
				importeFinal += importeFinal * 0.005;
				break;
			case SENIOR:
				importeFinal += importeFinal * 0.01;
				break;
			}
		}
		v.anhade(importeFinal);
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
	public Vendedor buscaVendedor(String id) {

		lista = new LinkedList<Vendedor>();
		Scanner in = null;
		try {
			// abre el fichero
			in = new Scanner(new FileReader(datos));
			// configura el formato de números
			in.useLocale(Locale.ENGLISH);
			nombre = in.nextLine();
			direccion = in.nextLine();
			in.next();
			Vendedor ven = null;
			// lee los vendedores senior
			while (in.hasNext() && !in.next().equals("Junior")) {

				String nombre = in.next();
				in.next();
				String idIn = in.next();
				in.next();
				double totalVentas = in.nextDouble();
				ven = new VendedorEnPlantilla(nombre, idIn, TipoVendedor.SENIOR);
				ven.setT(totalVentas);
				lista.add(ven);
			}
			// lee los vendedores junior
			while (in.hasNext() && !in.next().equals("Prácticas")) {
				String nombre = in.next();
				in.next();
				String idIn = in.next();
				in.next();
				double totalVentas = in.nextDouble();
				ven = new VendedorEnPlantilla(nombre, idIn, TipoVendedor.JUNIOR);
				ven.setT(totalVentas);
				lista.add(ven);
			}
			while (in.hasNext()) {
				in.next();
				String nombre = in.next();
				in.next();
				String idIn = in.next();
				in.next();
				double totalVentas = in.nextDouble();
				ven = new vendedorEnPracticas(nombre, idIn);
				ven.setT(totalVentas);
				lista.add(ven);
			}
		} catch (FileNotFoundException e) {
		} finally {
			if (in != null) {
				in.close();
			}
		} // try

		for (Vendedor v : lista) {
			if (v.getId().equals(id)) {
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
	public List<Vendedor> vendedores() {
		lista = new LinkedList<Vendedor>();

		Scanner in = null;
		try {
			// abre el fichero
			in = new Scanner(new FileReader(datos));
			// configura el formato de números
			in.useLocale(Locale.ENGLISH);
			nombre = in.nextLine();
			direccion = in.nextLine();
			in.next();
			Vendedor ven = null;
			// lee los vendedores senior
			while (in.hasNext() && !in.next().equals("Junior")) {

				String nombre = in.next();
				in.next();
				String id = in.next();
				in.next();
				double totalVentas = in.nextDouble();
				ven = new VendedorEnPlantilla(nombre, id, TipoVendedor.JUNIOR);
				ven.setT(totalVentas);
				lista.add(ven);
			}
			// lee los vendedores junior
			while (in.hasNext() && !in.next().equals("Prácticas")) {
				String nombre = in.next();
				in.next();
				String id = in.next();
				in.next();
				double totalVentas = in.nextDouble();
				ven = new vendedorEnPracticas(nombre, id);
				ven.setT(totalVentas);
				lista.add(ven);
			}
			while (in.hasNext()) {
				in.next();
				String nombre = in.next();
				in.next();
				String id = in.next();
				in.next();
				double totalVentas = in.nextDouble();
				ven = new VendedorEnPlantilla(nombre, id, TipoVendedor.SENIOR);
				ven.setT(totalVentas);
				lista.add(ven);
			}
		} catch (FileNotFoundException e) {

		} finally {
			if (in != null) {
				in.close();
			}
		} // try

		return lista;

	}

	/**
	 * Método que genera el fichero datosTienda.txt con los datos actualizados de
	 * los vendedores
	 */
	private void vuelcaDatos() throws IOException {
		PrintWriter out = null;
		List<Vendedor> senior = new LinkedList<Vendedor>();
		List<Vendedor> junior = new LinkedList<Vendedor>();
		List<Vendedor> practicas = new LinkedList<Vendedor>();

		for (Vendedor v : lista) {
			if (v instanceof vendedorEnPracticas) {
				practicas.add(v);
			} else if (v instanceof VendedorEnPlantilla) {
				VendedorEnPlantilla vp = (VendedorEnPlantilla) v;
				if (vp.tipo().equals(TipoVendedor.JUNIOR))
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
			for (Vendedor v1 : senior)
				out.println("      Nombre: " + v1.getNombre() + "   Id: " + v1.getId() + "   TotalVentasMes: "
						+ v1.getTotalVentas());
			out.println();
			out.println("    Junior");
			for (Vendedor v2 : junior)
				out.println("      Nombre: " + v2.getNombre() + "   Id: " + v2.getId() + "   TotalVentasMes: "
						+ v2.getTotalVentas());
			out.println();
			out.println("    Prácticas");
			for (Vendedor v3 : practicas)
				out.println("      Nombre: " + v3.getNombre() + "   Id: " + v3.getId() + "   TotalVentasMes: "
						+ v3.getTotalVentas());

		} finally {
			if (out != null)
				out.close();
		}
	}

}
