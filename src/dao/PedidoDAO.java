package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Pedido;

public class PedidoDAO {
	public int criar(Pedido pedido) {
		String sqlInsert = "INSERT INTO pedido(valorTotal) VALUES (?)";

		try (Connection conn = ConnectionFactory.obtemConexao();
				PreparedStatement stm = conn.prepareStatement(sqlInsert);) {
			stm.setFloat(1, pedido.getValorTotal());
			stm.execute();
			String sqlQuery = "SELECT LAST_INSERT_ID()";
			try (PreparedStatement stm2 = conn.prepareStatement(sqlQuery); ResultSet rs = stm2.executeQuery();) {
				if (rs.next()) {
					pedido.setIdPedido(rs.getInt(1));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pedido.getIdPedido();
	}

	public void atualizar(Pedido pedido) {
        String sqlUpdate = "UPDATE pedido SET ValorTotal=? idPedido=?";
        try (Connection conn = ConnectionFactory.obtemConexao();
                PreparedStatement stm = conn.prepareStatement(sqlUpdate);) {
            stm.setFloat(1, pedido.getValorTotal());
            stm.setInt(2, pedido.getIdPedido());
            stm.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public void excluir(int idPedido) {
		String sqlDelete = "DELETE FROM pedido WHERE idPedido = ?";
		try (Connection conn = ConnectionFactory.obtemConexao();
				PreparedStatement stm = conn.prepareStatement(sqlDelete);) {
			stm.setInt(1, idPedido);
			stm.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Pedido carregar(int idPedido) {
		Pedido pedido = new Pedido();
		pedido.setIdPedido(idPedido);
		String sqlSelect = "SELECT idPedido, ValorTotal FROM Pedido WHERE Pedido.idPedido = ?";
		try (Connection conn = ConnectionFactory.obtemConexao();
				PreparedStatement stm = conn.prepareStatement(sqlSelect);) {
			stm.setInt(1, pedido.getIdPedido());
			try (ResultSet rs = stm.executeQuery();) {
				if (rs.next()) {
					pedido.setValorTotal(rs.getFloat("valorTotal"));
				} else {
					pedido.setIdPedido(-1);
					pedido.setValorTotal(0);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			System.out.print(e1.getStackTrace());
		}
		return pedido;
	}

	public ArrayList<Pedido> listarPedidos() {
		Pedido pedido;
		ArrayList<Pedido> lista = new ArrayList<>();
		String sqlSelect = "SELECT idPedido, ValorTotal FROM Pedido";
		try (Connection conn = ConnectionFactory.obtemConexao();
				PreparedStatement stm = conn.prepareStatement(sqlSelect);) {
			try (ResultSet rs = stm.executeQuery();) {
				while (rs.next()) {
					pedido = new Pedido();
					pedido.setIdPedido(rs.getInt("idPedido"));
					pedido.setValorTotal(rs.getFloat("valorTotal"));
					lista.add(pedido);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			System.out.print(e1.getStackTrace());
		}
		return lista;
	}

	public ArrayList<Pedido> listarPedidos(String chave) {
		Pedido pedido;
		ArrayList<Pedido> lista = new ArrayList<>();
		String sqlSelect = "SELECT idPedido, ValorTotal FROM pedido where upper(idPedido)like ?";
		try (Connection conn = ConnectionFactory.obtemConexao();
				PreparedStatement stm = conn.prepareStatement(sqlSelect);) {
			stm.setString(1, "%" + chave.toUpperCase() + "%");
			try (ResultSet rs = stm.executeQuery();) {
				while (rs.next()) {
					pedido = new Pedido();
					pedido.setIdPedido(rs.getInt("idAdministrador"));
					pedido.setValorTotal(rs.getFloat("valorTotal"));
					lista.add(pedido);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			System.out.print(e1.getStackTrace());
		}
		return lista;
	}
	
}