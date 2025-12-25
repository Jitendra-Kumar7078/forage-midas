package com.jpmc.midascore.foundation;

import com.jpmc.midascore.entity.UserRecord;
import jakarta.persistence.*;

@Entity // Iska matlab ye database mein ek table banayega
public class TransactionRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // Ek user kayi transactions kar sakta hai
    private UserRecord sender;

    @ManyToOne
    private UserRecord recipient;

    private float amount;

    private float incentive;

    // Khali constructor (Hibernate ke liye zaroori hai)
    protected TransactionRecord() {}

    public TransactionRecord(UserRecord sender, UserRecord recipient, float amount, float incentive) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.incentive = incentive;
    }
}