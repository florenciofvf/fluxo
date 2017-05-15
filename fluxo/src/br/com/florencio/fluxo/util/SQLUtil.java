package br.com.florencio.fluxo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import br.com.florencio.fluxo.InstanciaRaiz;

public class SQLUtil {

	public static void importar(InstanciaRaiz raiz, String sql) throws Exception {
		raiz.limpar();
		Class.forName(Config.get("driver"));
		Connection conn = DriverManager.getConnection(Config.get("url"), Config.get("usuario"), Config.get("senha"));
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		ResultSetMetaData meta = rs.getMetaData();

		int colunas = meta.getColumnCount();

		while (rs.next()) {
			String[] grafo = new String[colunas];

			for (int i = 0; i < colunas; i++) {
				grafo[i] = rs.getString(i + 1);
			}

			raiz.getRaizDireita().importar(grafo);
		}

		rs.close();
		ps.close();
		conn.close();
	}

}