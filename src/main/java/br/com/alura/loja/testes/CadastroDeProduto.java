package br.com.alura.loja.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.dao.CategoriaDao;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;

public class CadastroDeProduto {
    
    public static void main(String[] args) {
        cadastrarProduto();

        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDao protutoDao = new ProdutoDao(em);

        Produto p = protutoDao.buscarPorId(3l);
        System.out.println(p.getPreco());

        List<Produto> todos = protutoDao.buscarPorNomeDaCategoria("CELULARES");

        todos.forEach(p2 -> System.out.println(p2.getNome()));
    }

    private static void cadastrarProduto() {
        Categoria celulares = new Categoria("CELULARES");

        
        Produto celular = new Produto("Xiaomi Redmi", 
                "Muito Legal", new BigDecimal("800"), celulares);

        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDao protutoDao = new ProdutoDao(em);
        CategoriaDao categoriaDao = new CategoriaDao(em);
        
        em.getTransaction().begin();
        categoriaDao.cadastrar(celulares);
        protutoDao.cadastrar(celular);
        em.getTransaction().commit();
        em.close();
    }
}
