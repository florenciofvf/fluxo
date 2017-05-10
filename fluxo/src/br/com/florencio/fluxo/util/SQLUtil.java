package br.com.florencio.fluxo.util;

import br.com.florencio.fluxo.InstanciaRaiz;

public class SQLUtil {

	public void importar(String[] grafo) {
		// if (grafo == null) {
		// return;
		// }
		//
		// Instancia sel = this;
		//
		// for (int i = 0; i < grafo.length; i++) {
		// String string = grafo[i];
		//
		// if (string == null || string.trim().length() == 0) {
		// break;
		// }
		//
		// Instancia obj = sel.getFilho(string);
		//
		// if (obj == null) {
		// obj = new Instancia(string);
		// sel.adicionar(obj);
		// }
		//
		// sel = obj;
		// }
	}

	public static void importar(InstanciaRaiz raiz, String sql) {
		//
		// Class.forName(Config.get("driver"));
		// Connection conn = DriverManager.getConnection(Config.get("url"),
		// Config.get("usuario"), Config.get("senha"));
		// PreparedStatement ps = conn.prepareStatement(sql);
		// ResultSet rs = ps.executeQuery();
		// ResultSetMetaData meta = rs.getMetaData();
		//
		// int colunas = meta.getColumnCount();
		//
		// while (rs.next()) {
		// String[] grafo = new String[colunas];
		//
		// for (int i = 0; i < colunas; i++) {
		// grafo[i] = rs.getString(i + 1);
		// }
		//
		// raiz.importar(grafo);
		// }
		//
		// rs.close();
		// ps.close();
		// conn.close();
	}
}