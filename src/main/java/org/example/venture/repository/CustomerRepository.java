package org.example.venture.repository;

import org.example.venture.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// why is this component not repossitory?
@Component
public class CustomerRepository {
    private static final Logger LOG =
            LoggerFactory.getLogger(CustomerRepository.class);
    public CustomerRepository() {
        File file = new File(DATABASE_NAME);
        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
    }

    private static final String NEW_LINE = System.lineSeparator();
    private static final String DATABASE_NAME = "users/users.txt";
    private static void appendToFile(Path path, String content)
            throws IOException {
        Files.write(path,
                content.getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);
    }
    public void save(User customer) throws Exception {
        User c = findByUsername(customer.getUsername());
        if (c != null) {
            throw new Exception("This username already exists. " +
                    "Please choose another one.");
        }
        Path path = Paths.get(DATABASE_NAME);
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        String passwordEncoded = bc.encode(customer.getPassword());
        String data = customer.getUsername() + "," + passwordEncoded + "," + customer.getEmail();
        appendToFile(path, data + NEW_LINE);
    }

    public void updatePassword(User customer) throws Exception {
        User c = findByEmail(customer.getEmail());
        if (c == null) {
            throw new Exception("This username does not exist.");
        }
        Path path = Paths.get(DATABASE_NAME);
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        String passwordEncoded = bc.encode(customer.getPassword());
        String data = customer.getUsername() + "," + passwordEncoded + "," + customer.getEmail() + "," + customer.getSettingsConfig();
        appendToFile(path, data + NEW_LINE);
    }


    public List<User> findAll() throws IOException {
        List<User> result = new ArrayList<>();
        Path path = Paths.get(DATABASE_NAME);
        List<String> data = Files.readAllLines(path);
        for (String line : data) {
            if(!line.trim().isEmpty()) {
                String[] tokens = line.split(",");
                User c = new User(tokens[0], tokens[1], tokens[2], Collections.singletonList(tokens[3]));
                result.add(c);
            }
        }
        return result;
    }

    public User findByUsername(String username) throws IOException {
        List<User> customers = findAll();
        for(User customer : customers) {
            if (customer.getUsername().trim().equalsIgnoreCase(username.trim())) {
                return customer;
            }
        }
        return null;
    }
    public User findByEmail(String email) throws IOException {
        List<User> customers = findAll();
        for(User customer : customers) {
            if (customer.getEmail().trim().equalsIgnoreCase(email.trim())) {
                return customer;
            }
        }
        return null;
    }

    public void deleteAccount(User customer) {
        try {
            User c = findByEmail(customer.getEmail());
            if (c == null) {
                throw new Exception("This username does not exist.");
            }
            List<User> customers = findAll();
            Path path = Paths.get(DATABASE_NAME);
            Files.delete(path);
            for (User cust : customers) {
                if (!cust.getEmail().trim().equalsIgnoreCase(customer.getEmail().trim())) {
                    save(cust);
                }
            }
        } catch (IOException e) {
            LOG.error(e.getMessage());
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }
}
