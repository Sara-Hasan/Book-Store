package Book;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.List;


public class BookStoreTest {
	
	    public WebDriver driver;

	    @BeforeTest
	    public void setUp() {
	        driver = new HtmlUnitDriver(true); // Enable JavaScript
	        String virtualHtmlContent = VirtualBookStoreOnline(3); // Generate HTML with 3 books
	        try {
	            ((HtmlUnitDriver) driver).getWebClient().getPage("data:text/html," + virtualHtmlContent);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    @Test
	    public void testBookListDisplayedCorrectly() {
	    	
	    	// Locate the book list by its ID
	        WebElement bookList = driver.findElement(By.id("book-list"));
	        
	        // Assert that the book list element is not null
	        Assert.assertNotNull(bookList, "The book list should be displayed.");
	        
	        // Get the list of books
	        List<WebElement> books = bookList.findElements(By.tagName("li"));
	        
	        // Assert that there is at least one book in the list
	        Assert.assertTrue(books.size() > 0, "The book list should contain at least one book.");	     
	        
	    }

	    @Test
	    public void testAddBookToCart() {
	    	
	    	// Locate the button of add the book by its class name
	        WebElement addBook1 = driver.findElement(By.cssSelector("#book-1 .add-to-cart"));
	        // Add book to the cart
	        addBook1.click();
	        
	        // Locate the button of add the book by its class name
	        WebElement addBook2 = driver.findElement(By.cssSelector("#book-2 .add-to-cart"));
	        // Add book to the cart
	        addBook2.click();
	        
	        // Locate the book list by its ID
	        WebElement cartItems = driver.findElement(By.id("cart-items"));
	        // Get text from the cart
	        String cart = cartItems.getText();
//	        System.out.println(cart);
	        // Assert that the book has been added to the cart.
	        Assert.assertTrue(cart.contains("Book 1"), "The cart should contain 'Book 1'.");
	    }

	    @Test
	    public void testCartUpdatesCorrectlyWhenBookAdded() {
	        WebElement addBook1 = driver.findElement(By.cssSelector("#book-1 .add-to-cart"));
	        addBook1.click();
	        WebElement addBook2 = driver.findElement(By.cssSelector("#book-2 .add-to-cart"));
	        addBook2.click();
	        WebElement cartItems = driver.findElement(By.id("cart-items"));
	        String cartText = cartItems.getText();
	        
	        Assert.assertTrue(cartText.contains("Book 1"), "The cart should contain 'Book 1'.");
	        Assert.assertTrue(cartText.contains("Book 2"), "The cart should contain 'Book 2'.");
	    }

	    @Test
	    public void testUserProceedToCheckout() {
	    	
	    	// Add books to cart
	        WebElement addBook1 = driver.findElement(By.cssSelector("#book-1 .add-to-cart"));
	        addBook1.click();
	        WebElement addBook2 = driver.findElement(By.cssSelector("#book-2 .add-to-cart"));
	        addBook2.click();
	        WebElement addBook3 = driver.findElement(By.cssSelector("#book-3 .add-to-cart"));
	        addBook3.click();
	        WebElement addBook4 = driver.findElement(By.cssSelector("#book-3 .add-to-cart"));
	        addBook4.click();

	        // Locate the button of (Proceed to Checkout) by its ID
	        WebElement checkoutButton = driver.findElement(By.id("proceed-to-checkout"));
	        // Go to the checkout page
	        checkoutButton.click();

	        // locate the content of the checkout page by its id
	        WebElement checkoutPage = driver.findElement(By.tagName("body"));
	        // Get Content of checkout page
	        String ContentCheckoutPage = checkoutPage.getText();
			
	        // Split string into array
		    String[] itemsCart = ContentCheckoutPage.split("\n");
//		    System.out.println(Arrays.toString(checkoutLines));
		   
		    // Search for price
		    String totalPrice = null;
		    for (String item : itemsCart) {
		        if (item.contains("Total Price:")) {
		            totalPrice = item;
		            break;
		        }
		    }
//		    System.out.println(totalPriceLine); 

		    //Check if totalPriceLine is exist
		    Assert.assertNotNull(totalPrice, "Total Price line should be present in the checkout page.");

		    // Get the total price 
		    String TheTotal = totalPrice.split(":")[1].trim();
//		    System.out.println(totalPrice); 


	        Assert.assertTrue(ContentCheckoutPage.contains("Book 1 - $10"), "Checkout page should contain 'Book 1 - $10'.");
	        Assert.assertTrue(ContentCheckoutPage.contains("Book 2 - $20"), "Checkout page should contain 'Book 2 - $20'.");
	        Assert.assertTrue(ContentCheckoutPage.contains("Book 3 - $30"), "Checkout page should contain 'Book 3 - $30'.");
	        Assert.assertTrue(ContentCheckoutPage.contains("Total Price: " + TheTotal));
	    }

	    @AfterTest
	    public void tearDown() {
	        if (driver != null) {
	            driver.quit();
	        }
	    }
	    
	    
	    /**
	     * Generates a dynamic HTML string representing a simple online bookstore page.
	     * 
	     * This function creates an HTML document that includes:
	     * - A list of books, each with an "Add to Cart" button.
	     * - A shopping cart section that displays added items and the total price.
	     * - JavaScript functions to handle adding items to the cart and proceeding to checkout.
	     * 
	     * @param numberOfBooks The number of books to be displayed in the list.
	     * @return A string containing the complete HTML structure.
	     */
	    private String VirtualBookStoreOnline(int numberOfBooks) {
	    	
	    	// Initialize StringBuilder to generate HTML content
	        StringBuilder htmlBuilder = new StringBuilder();

	        // Start of the HTML document
	        htmlBuilder.append("<html>")
	                   .append("<head><title>Test Page</title></head>")
	                   .append("<body>")
	                   .append("<ul id='book-list'>");

	        // Create List of books
	        for (int i = 1; i <= numberOfBooks; i++) {
	            htmlBuilder.append("<li id='book-")
	                       .append(i)
	                       .append("'>Book ")
	                       .append(i)
	                       .append(" <button class='add-to-cart' onclick='addToCart(\"Book ")
	                       .append(i)
	                       .append("\", ")
	                       .append(10 * i) 
	                       .append(")'>Add to Cart</button></li>");
	        }

	        // Close the book list and add the shopping cart section
	        htmlBuilder.append("</ul>")
	                   .append("<div id='cart'>")
	                   .append("<h2>Shopping Cart</h2>")
	                   .append("<ul id='cart-items'></ul>")
	                   .append("<p id='total-price'>Total Price: $0</p>")
	                   .append("<button id='proceed-to-checkout' onclick='proceedToCheckout()'>Proceed to Checkout</button>")
	                   .append("</div>")
	                    // JavaScript functions to handle cart operations
	                   .append("<script>")
	                   .append("var totalPrice = 0;")
	                   .append("function addToCart(book, price) {")
	                   .append("  var cartItems = document.getElementById('cart-items');")
	                   .append("  var newItem = document.createElement('li');")
	                   .append("  newItem.textContent = book + ' - $' + price;")
	                   .append("  cartItems.appendChild(newItem);")
	                   .append("  totalPrice += price;")
	                   .append("  document.getElementById('total-price').textContent = 'Total Price: $' + totalPrice;")
	                   .append("}")
	                   .append("function proceedToCheckout() {")
	                   .append("  document.body.innerHTML = '<h1>Checkout Page</h1>' + ")
	                   .append("  '<ul>' + document.getElementById('cart-items').innerHTML + '</ul>' + ")
	                   .append("  '<p>Total Price: $' + totalPrice + '</p>';")
	                   .append("}")
	                   .append("</script>")
	                   .append("</body>")
	                   .append("</html>");

	        return htmlBuilder.toString();
	    }

	}

