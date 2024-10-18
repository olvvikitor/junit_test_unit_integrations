package test.repositories;

import com.snack.entities.Product;
import com.snack.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class TestProductRepository {
    private ProductRepository repo;
    private Product product;


    @BeforeEach
    public void set() {
        repo = new ProductRepository();
        product = new Product(1,"Feijão", 5.9f, "");
        repo.append(product);
    }

    @Test
    @DisplayName("Verificar se um produto é adicionado corretamente ao repositório")
    public void VerificarSeFoiSalvo() {
        Product teste = new Product(2,"Arroz", 5.9f, "");
        repo.append(teste);
        List<Product> list = repo.getAll();
        assertEquals(list.size(), 2);
    }
    @Test
    @DisplayName("Verificar se é possível recuperar um produto usando seu ID")
    public void BuscarPorId() {
        repo.append(product);
        Product product1 = repo.getById(1);
        assertEquals(product1.getId(), 1);
    }
    //    3. Confirmar se um produto existe no repositório (List)
    @Test
    @DisplayName("Confirmar se um produto existe no repositório (List)")
    public void ConfirmarSeExisteUmProdutoNaLista(){
        assertTrue(repo.exists(1));
    }
    //4. Testar se um produto é removido corretamente do repositório (List)
    @Test
    @DisplayName("Testar se um produto é removido corretamente do repositório (List)")
    public void TestarSeRemoveCorretamente(){
        repo.remove(1);
        assertFalse(repo.exists(1));
    }
    //5. Verificar se um produto é atualizado corretamente
    @Test
    @DisplayName("Verificar se um produto é atualizado corretamente\n")
    public void TestarSeAtualizaCorretamente(){
        product.setDescription("Arroz");
        repo.update(1, product);
        assertEquals("Arroz", repo.getById(1).getDescription());
    }
    //6. Testar se todos os produtos armazenados são recuperados corretamente
    @Test
    @DisplayName("Testar se todos os produtos armazenados são recuperados corretamente")
    public void TestarSeTodosEstaoSendoArmazenados(){
        Product teste = new Product(1, "Arroz", 5.9f, "");
        repo.append(teste);
        assertEquals(2, repo.getAll().size());
    }
    //7. Verificar o comportamento ao tentar remover um produto que não existe
    @Test
    @DisplayName("Verificar o comportamento ao tentar remover um produto que não existe")
    public void TestarRemoverUmProdutoInexistente(){
        repo.remove(3);
        assertEquals(1, repo.getAll().size());
    }
    //8. Testar o que acontece ao tentar atualizar um produto que não está no repositório (List)
    @Test
    @DisplayName("Testar o que acontece ao tentar atualizar um produto que não está no repositório (List)")
    public void TestarAtualizarProdutoInexistente(){
        Product product1  = repo.getById(1);
        product.setDescription("Arroz");
        assertThrows(NoSuchElementException.class, ()->{
            repo.update(3, product);
        });
    }
    //9. Verificar se o repositório aceita a adição de produtos com IDs duplicados (espera-se que não)
    @Test
    @DisplayName("Verificar se o repositório aceita a adição de produtos com IDs duplicados (espera-se que não)")
    public void TestarSeRepoitorioNaoAceitaIdDuplicado(){
        Product teste = new Product(1,"Arroz", 5.9f, "");
        repo.append(teste);
        assertNotEquals("Arroz", repo.getById(1).getDescription());
    }
    //10. Confirmar que o repositório retorna uma lista vazia ao ser inicializado (List)
    @Test
    @DisplayName("Confirmar que o repositório retorna uma lista vazia ao ser inicializado (List)")
    public void ConfirmarSeAListaInicializadaVemVazia(){
        ProductRepository novo = new ProductRepository();
        assertEquals(0,novo.getAll().size());
    }


    @BeforeEach
    void reset(){
        product.setDescription("Feijão");
        product.setPrice(5.9f);
        repo.update(1, product);

    }
}