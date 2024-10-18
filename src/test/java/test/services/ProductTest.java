package test.services;

import com.snack.entities.Product;
import com.snack.repositories.ProductRepository;
import com.snack.services.ProductService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {
    private Product product;
    private ProductRepository productRepository;
    private static ProductService productService;

    @BeforeEach
    void setup(){

        productRepository = new ProductRepository();
        productService = new ProductService();

        product = new Product(1, "Feijão", 3.9f, "C:\\Users\\Jussara\\Downloads\\4491_1.jpg\\");
        productService.save(product);
    }

    //1. Salvar um produto com imagem válida
    @Test
    @DisplayName("Salvar um produto com imagem válida")
    public void TestSalvarProdutoComImagemValida() {
        Product product1 = new Product(2, "Arroz", 4.9f, "C:\\Users\\Jussara\\Downloads\\Arroz-Tio-Joao-Branco-1kg.png");
        assertTrue(productService.save(product1));
    }
    //2. Salvar um produto com imagem inexistente
    @Test
    @DisplayName("2. Salvar um produto com imagem inexistente\n")
    public void TestSalvarProdutoComImagemInvalida() {
        Product product1 = new Product(2, "Arroz", 4.9f, "C:\\Users\\Jussara\\Downloads\\Arroz-Tio-Joao-1kg.png");
        assertFalse(productService.save(product1));
    } 
    //3. Atualizar um produto existente
    @Test
    @DisplayName("Atualizar um produto existente")
    public void TestarAtualizarproduto() {
        product.setDescription("Feijão carioca");
        productService.update(product);
        assertEquals("Feijão carioca",product.getDescription());
    }
    //4. Remover um produto existente
    @Test
    @DisplayName("Remover um produto existente")
    public void TesteRemoverProdutoExistente() {
        Path path = Paths.get(productService.getImagePathById(product.getId()));
        productService.remove(product.getId());
        assertFalse(Files.exists(path));
    }
    //5. Obter caminho da imagem por ID
    @Test
    @DisplayName("Obter caminho da imagem por ID")
    public void TesteObterCaminhoImgPorId() {
        Path path = Paths.get(productService.getImagePathById(product.getId()));
        assertTrue(Files.exists(path));

    }
    @AfterAll
    static void limpaLista(){
        productService.remove(2);
    }
}
