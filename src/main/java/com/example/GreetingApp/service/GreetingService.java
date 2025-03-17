package com.example.GreetingApp.service;

import com.example.GreetingApp.model.Greeting;
import com.example.GreetingApp.repository.GreetingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GreetingService {

    private final GreetingRepository greetingRepository;

    public GreetingService(GreetingRepository greetingRepository) {
        this.greetingRepository = greetingRepository;
    }

    public Greeting getGreeting(String firstName, String lastName) {
        String message;
        if ((firstName == null || firstName.isBlank()) && (lastName == null || lastName.isBlank())) {
            message = "Hello World";
        } else {
            String fullName = (firstName != null ? firstName : "") +
                    (lastName != null ? " " + lastName : "");
            message = "Hello " + fullName.trim();
        }

        Greeting greeting = new Greeting(message);
        return greetingRepository.save(greeting);
    }

    public Greeting getGreetingById(Long id) {
        Optional<Greeting> greeting = greetingRepository.findById(id);
        return greeting.orElseThrow(() -> new RuntimeException("Greeting not found with id: " + id));
    }

    public Greeting saveGreeting(Greeting greeting) {
        return greetingRepository.save(greeting);
    }

    public List<Greeting> getAllGreetings() {
        return greetingRepository.findAll();
    }

    public Greeting updateGreeting(Long id, Greeting newGreeting) {
        return greetingRepository.findById(id).map(existingGreeting -> {
            existingGreeting.setMessage(newGreeting.getMessage());
            return greetingRepository.save(existingGreeting);
        }).orElseThrow(() -> new RuntimeException("Greeting not found with id: " + id));
    }

    public void deleteGreeting(Long id) {
        if (!greetingRepository.existsById(id)) {
            throw new RuntimeException("Greeting not found with id: " + id);
        }
        greetingRepository.deleteById(id);
    }
}