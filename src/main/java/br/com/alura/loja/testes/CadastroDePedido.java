package br.com.alura.loja.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.dao.ClienteDao;
import br.com.alura.loja.dao.PedidoDao;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.modelo.Cliente;
import br.com.alura.loja.modelo.ItemPedido;
import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;
import br.com.alura.loja.vo.RelatorioDeVendasVo;

public class CadastroDePedido {
    
    public static void main(String[] args) {
        
        EntityManager em = JPAUtil.getEntityManager();

        ProdutoDao produtoDao = new ProdutoDao(em);
        ClienteDao clienteDao = new ClienteDao(em);
        Cliente cliente = clienteDao.buscarPorId(1l);
        Pedido pedido = new Pedido(cliente);

        Produto produto = produtoDao.buscarPorId(3l);
        
        pedido.adicionarItem(new ItemPedido(10, pedido, produto));

        PedidoDao pedidoDao = new PedidoDao(em);
        em.getTransaction().begin();

        pedidoDao.cadastrar(pedido);
        
        em.getTransaction().commit();

        

        BigDecimal totalVendido = pedidoDao.valorTotalVendido();
        System.out.println("Valor total vendido: " + totalVendido);

        List<RelatorioDeVendasVo> relatorio = pedidoDao.relatorioDeVendas();
        relatorio.forEach(System.out::println);
        em.close();
    }

    public static void cadastrarCliente() {
        EntityManager em = JPAUtil.getEntityManager();
        Cliente cliente = new Cliente("Silas", "00000000000");
        ClienteDao clienteDao = new ClienteDao(null);

        em.getTransaction().begin();
        clienteDao.cadastrar(cliente);
        em.getTransaction().commit();
        em.close();
    }
}
