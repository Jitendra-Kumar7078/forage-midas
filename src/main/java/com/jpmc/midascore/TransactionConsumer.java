package com.jpmc.midascore;

import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.foundation.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.UserRecordRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TransactionConsumer {
    private final UserRecordRepository userRepository;
    private final TransactionRecordRepository transactionRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    public TransactionConsumer(UserRecordRepository userRepository, TransactionRecordRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    @KafkaListener(topics = "${general.kafka-topic}", groupId = "midas-group")
    public void listen(Transaction transaction) {
        UserRecord sender = userRepository.findById(transaction.getSenderId());
        UserRecord recipient = userRepository.findById(transaction.getRecipientId());

        if (sender != null && recipient != null && sender.getBalance() >= transaction.getAmount()) {
            // Incentive API call
            String url = "http://localhost:8080/incentive";
            Incentive incentive = restTemplate.postForObject(url, transaction, Incentive.class);
            float reward = (incentive != null) ? incentive.getAmount() : 0f;

            // Balance updates
            sender.setBalance(sender.getBalance() - transaction.getAmount());
            recipient.setBalance(recipient.getBalance() + transaction.getAmount() + reward);

            userRepository.save(sender);
            userRepository.save(recipient);
            transactionRepository.save(new TransactionRecord(sender, recipient, transaction.getAmount(), reward));
        }
    }
}