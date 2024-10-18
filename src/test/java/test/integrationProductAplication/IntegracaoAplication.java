package test.integrationProductAplication;

import com.snack.applications.ProductApplication;
import com.snack.entities.Product;
import com.snack.repositories.ProductRepository;
import com.snack.services.ProductService;
import org.junit.jupiter.api.*;

import java.nio.file.NoSuchFileException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;


public class IntegracaoAplication {

    private static Product product;
    private static ProductRepository productRepository;
    private static ProductService productService;
    private static ProductApplication application;
    private static Product product1;
    private static Product product2;
    private static Product product3;

    @BeforeAll
    static void setup(){
        productRepository = new ProductRepository();
        productService = new ProductService();
        application = new ProductApplication(productRepository, productService);

    }
    @BeforeEach
    void initialize(){
        product1 = new Product(1,"Feijão", 5.90f, "C:\\Users\\Jussara\\Downloads\\4491_1.jpg");
        product2 = new Product(2, "Arroz", 6.92f, "C:\\Users\\Jussara\\Downloads\\Arroz-Tio-Joao-Branco-1kg.png");
        application.append(product1);
        application.append(product2);
    }
    //1. Listar todos os produtos do repositório
    @Test
    @DisplayName("Listar todos os produtos do repositório")
    public void TestListAll(){
        assertEquals(2, application.getAll().size());
    }
    //2. Obter um produto por ID válido
    @Test
    @DisplayName("Obter um produto por ID válido")
    public void TestGetWithValidId(){
        assertNotNull(application.getById(1));
    }
    //3.Retornar nulo ou erro ao tentar obter produto por ID inválido
    @Test
    @DisplayName("Retornar nulo ou erro ao tentar obter produto por ID inválido")
    public void TestReturnErrorOrNUllAfterInvalidId(){
        assertThrows(Exception.class, () -> application.getById(-1));
    }
    //4. Verificar se um produto existe por ID válido
    @Test
    @DisplayName("Verificar se um produto existe por ID válido")
    public void TestExistsIdTrue(){
        assertTrue(application.exists(1));
    }
    //5. Retornar falso ao verificar a existência de um produto com ID inválido
    @Test
    @DisplayName("Retornar falso ao verificar a existência de um produto com ID inválido")
    public void TestFalseAfterInvalidId(){
        assertFalse(application.exists(-1));
    }
    //6. Adicionar um novo produto e salvar sua imagem corretamente
    @Test
    @DisplayName("Adicionar um novo produto e salvar sua imagem corretamente")
    public void TestSaveWithImage(){
        assertNotNull(application.getById(1));
        assertNotNull(application.getById(1).getImage());

    }
    //7. Remover um produto existente e deletar sua imagem
    @Test
    @DisplayName("Remover um produto existente e deletar sua imagem")
    public void TestRemoveProductAndImage(){
        application.remove(2);
        assertThrows(NoSuchElementException.class, () -> application.getById(2));
    }
    //8. Não alterar o sistema ao tentar remover um produto com ID inexistente
    @Test
    @DisplayName(" Não alterar o sistema ao tentar remover um produto com ID inexistente")
     public void NotAffectSystemAfterRemoveInvalidId(){
        assertThrows(NoSuchElementException.class, () -> application.remove(4));
    }
    //9. Atualizar um produto existente e substituir sua imagem
    @Test
    @DisplayName("Atualizar um produto existente e substituir sua imagem")
     public void TestUpdateProductAndImage(){
        String newPhoto = "C:\\Users\\Jussara\\Downloads\\AgentSmith.jpg";
        product2.setImage(newPhoto);
        product2.setDescription("Atualizado");
        application.update(2, product2);
        System.out.println(application.getById(2).getImage());
        assertEquals("Atualizado", application.getById(2).getDescription());
    }

    @AfterEach
     void reset(){
        if (application.exists(1)) application.remove(1);
        if (application.exists(2)) application.remove(2);
    }
}
