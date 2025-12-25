package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TransactionListener {
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    public TransactionListener(UserRepository userRepository, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }

    @KafkaListener(topics = "${general.kafka-topic}", groupId = "midas-group")
    public void listen(Transaction transaction) {
        UserRecord sender = userRepository.findById(transaction.getSenderId());
        UserRecord receiver = userRepository.findById(transaction.getReceiverId());

        if (sender != null && receiver != null && sender.getBalance() >= transaction.getAmount()) {
            // 1. Paisa kato aur bhejo
            sender.setBalance(sender.getBalance() - transaction.getAmount());

            // 2. Incentive API (8080) ko call karo
            String url = "http://localhost:8080/incentive";
            Incentive incentive = restTemplate.postForObject(url, transaction, Incentive.class);

            // 3. Incentive add karo
            float bonus = (incentive != null) ? incentive.getAmount() : 0f;
            receiver.setBalance(receiver.getBalance() + transaction.getAmount() + bonus);

            userRepository.save(sender);
            userRepository.save(receiver);
        }
    }
}