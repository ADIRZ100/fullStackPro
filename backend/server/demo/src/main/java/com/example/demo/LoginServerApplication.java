package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@RestController
public class LoginServerApplication {
    

      static EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.example_demo_jar_0.0.1-SNAPSHOTPU");
      static EntityManager emUsers = emf.createEntityManager();
      //database conoection to the Users table
      static Users userLogin = new Users();
      static Users getChecUsers = new Users();
      static Users update_LastCon = new Users();
      static String lastConnectionUser = null;
      static String getEmail = null;
      
      //for the token
      private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
      private static final SecureRandom RANDOM = new SecureRandom();
      static String token = null; // Adjust length as needed
      static LocalDateTime expirationTime ;

      //HouseholdElectricalProducts
      static EntityManager emHoses = emf.createEntityManager();
      static HouseholdElectricalProducts getDataHoses = new HouseholdElectricalProducts();
      //HouseholdElectricalProducts
      static EntityManager emRoom = emf.createEntityManager();
      static ElectricalProductsForRoom getDataRomm = new ElectricalProductsForRoom();
    
      //calc
      static String jsonProdacts = null;
      // Arrays to store product and time values
      static List<String> products = new ArrayList<>();
      static List<Double> times = new ArrayList<>();
      static EntityManager emCala = emf.createEntityManager();
      static HouseholdElectricalProducts getCala = new HouseholdElectricalProducts();
      
    // CORS Configuration Bean
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000") // Update with your React app's URL
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*");
            }
        };
    }
    public static void main(String[] args) {
        SpringApplication.run(LoginServerApplication.class, args);

    }

    @GetMapping
    public String hello(){
        // Creating a JSON object with email and password
        String email = "adir78@gmail.com";
        String password = "adir1425554";
        String json = "{\"email\": \"" + email + "\", \"password\": \"" + password + "\"}";
        return json;
    }
    

   @PostMapping("/api/Login")
    public boolean checkEmailAndPassword(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
         Date currentDate = new Date();
                try {
                  Query query = emUsers.createQuery("select u.id from Users u\n" +
                                "where u.email = ?1 and u.password = ?2");
                  query.setParameter(1, email);
                  query.setParameter(2, password);
                              // check the parmeaters and return user id
                getChecUsers = emUsers.find(Users.class, query.getSingleResult());
                
                    if(getChecUsers.getEmail() != null){
                      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                        // Format the Date object into a string
                        String formattedDate = dateFormat.format(currentDate);
                        emUsers.getTransaction().begin();
                        token = generateRandomString(10);
                        expirationTime = generateExpirationTime(20);
                        getChecUsers.setToken(token);
                        getEmail = getChecUsers.getEmail();
                        //add the last connection
                        lastConnectionUser =  getChecUsers.getLastConnection();
                        getChecUsers.setLastConnection(formattedDate);
                        emUsers.getTransaction().commit();
                        return true;
            }
            else{
                return false;
            }
                  
       } catch (Exception e) {
           return false;
       }

    }
   @GetMapping("/api/Login/lastConnection")
    public String lastConection (){
        return lastConnectionUser;
}
    @GetMapping("/api/Login/welcomUser")
    public String[] emailName (){
        String[] emailAfterCast = getEmail.split("@");
        System.out.println(emailAfterCast);
        return emailAfterCast;
}
  @GetMapping("/api/Login/token")
    public boolean checkToken(){
        boolean expired = isTokenExpired(expirationTime);
        if (expired) {  
            return false;
        } else {
            return true;
        }
    }
    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt((int) (Math.random() * CHARACTERS.length())));
        }
        return sb.toString();
    }

    public static LocalDateTime generateExpirationTime(int minutes) {
        return LocalDateTime.now().plus(minutes, ChronoUnit.MINUTES);
    }
    
    public static boolean isTokenExpired(LocalDateTime expirationTime) {
        LocalDateTime currentTime = LocalDateTime.now();
        return currentTime.isAfter(expirationTime);
    }
    @GetMapping("/api/Login/getHoseProdact")
    public static List<String> hosesProdact() {
        Query query = emHoses.createQuery("SELECT p.deviceType FROM HouseholdElectricalProducts p");
        List<String> deviceTypes = query.getResultList();
        return deviceTypes;
    }
        @GetMapping("/api/Login/getRoomProdact")
        public static List<String> roomProdact() {
        Query query = emRoom.createQuery("SELECT p.deviceType FROM ElectricalProductsForRoom p");
        List<String> deviceTypes = query.getResultList();
        return deviceTypes;
    }

@PostMapping("/api/Login/calcHose_Room")
public BigDecimal calc(@RequestBody Map<String, Object> requestMap) {
    try {
        // Convert the request object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonProducts = objectMapper.writeValueAsString(requestMap);

        // Print the JSON
        System.out.println("Received JSON: " + jsonProducts);

        // Extract householdProducts and roomProducts from the requestMap
        List<Map<String, Object>> householdProducts = (List<Map<String, Object>>) requestMap.get("householdProducts");
        List<Map<String, Object>> roomProducts = (List<Map<String, Object>>) requestMap.get("roomProducts");

        List<String> products = new ArrayList<>();
        List<Double> times = new ArrayList<>();

        // Process the householdProducts
        if (householdProducts != null) {
            extractProductsAndTimes(householdProducts, products, times);
        }

        // Process the roomProducts
        if (roomProducts != null) {
            extractProductsAndTimes(roomProducts, products, times);
        }

        // Print the extracted products and times
        System.out.println("Products: " + products);
        System.out.println("Times: " + times);

        BigDecimal totalSum = BigDecimal.ZERO;

        // Calculate the cost for each product in the householdProducts
        for (String product : products) {
            Query query1 = emCala.createQuery("SELECT e.costPerHourILS FROM HouseholdElectricalProducts e WHERE e.deviceType = :deviceType");
            query1.setParameter("deviceType", product);
            List<BigDecimal> costPerHourILS1 = query1.getResultList();

            // Sum up the costs from HouseholdElectricalProducts
            for (BigDecimal cost : costPerHourILS1) {
                totalSum = totalSum.add(cost);
            }

            Query query2 = emCala.createQuery("SELECT e.costPerHourILS FROM ElectricalProductsForRoom e WHERE e.deviceType = :deviceType");
            query2.setParameter("deviceType", product);
            List<BigDecimal> costPerHourILS2 = query2.getResultList();

            // Sum up the costs from ElectricalProductsForRoom
            for (BigDecimal cost : costPerHourILS2) {
                totalSum = totalSum.add(cost);
            }
        }

        System.out.println("Total sum of costs: " + totalSum);

        // Example response
        return totalSum;
    } catch (Exception ex) {
        // Handle exceptions if any
        ex.printStackTrace();
        return null;
    }
}

// Helper method to extract products and times from a list of maps
private void extractProductsAndTimes(List<Map<String, Object>> list, List<String> products, List<Double> times) {
    for (Map<String, Object> item : list) {
        String product = (String) item.get("product");
        Double time = null;
        Object timeObj = item.get("time");
        if (timeObj instanceof Number) {
            time = ((Number) timeObj).doubleValue();
        }
        if (product != null) {
            products.add(product);
        }
        if (time != null) {
            times.add(time);
        }
    }
}


}








  


