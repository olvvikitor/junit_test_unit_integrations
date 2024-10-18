package test.integrationProductFacede;

import com.snack.applications.ProductApplication;
import com.snack.entities.Product;
import com.snack.facade.ProductFacade;
import com.snack.repositories.ProductRepository;
import com.snack.services.ProductService;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class IntegracaoProductFacede {
    static ProductService productService;
    static ProductRepository productRepository;
    private static ProductFacade productFacade;
    private static ProductApplication productApplication;
    private static Product product;

    @BeforeAll
    static void config(){
        productService = new ProductService();
        productRepository = new ProductRepository();
        productApplication = new ProductApplication(productRepository, productService);
        productFacade = new ProductFacade(productApplication);
        product = new Product(1, "Feijão", 3.9f, "C:\\Users\\Jussara\\Downloads\\4491_1.jpg\\");
        productFacade.append(product);
    }

    //1. Retornar a lista completa de produtos ao chamar o método getAll.
    @Test
    @DisplayName("Retornar a lista completa de produtos ao chamar o método getAll.")
    public void RetornaListaCompleta(){
        List<Product>list = productFacade.getAll();
        assertEquals(2, list.size());
    }
    //2. Retornar o produto correto ao fornecer um ID válido no método getById.
    @Test
    @DisplayName("Retornar o produto correto ao fornecer um ID válido no método getById.")
    public void BuscaPorId(){
        Product productFind = productFacade.getById(1);
        assertEquals("Feijão", productFind.getDescription());
    }
    //3. Retornar true para um ID existente e false para um ID inexistente no método exists.
    @Test
    @DisplayName("Retornar o produto correto ao fornecer um ID válido no método getById.")
    public void TestProductExists(){
        assertTrue(productFacade.exists(1));
        assertFalse(productFacade.exists(2));
    }
    //4. Adicionar um novo produto corretamente ao chamar o método append.
    @Test
    @DisplayName("Adicionar um novo produto corretamente ao chamar o método append.")
    public void TestAdicionarNovo(){
        Product product1 = new Product(21, "Arroz", 4.9f, "C:\\Users\\Jussara\\Downloads\\Arroz-Tio-Joao-Branco-1kg.png");
        productFacade.append(product1);
        assertEquals(2, productFacade.getAll().size());
    }
    //5. Remover um produto existente ao fornecer um ID válido no método remove.
    @Test
    @DisplayName("Remover um produto existente ao fornecer um ID válido no método remove.")
    public void TestRemoveComIdValido(){
        productFacade.remove(21);
        assertEquals(1, productFacade.getAll().size());
    }
    @AfterAll
    static void limpaLista(){
        productFacade.remove(1);

    }
}
