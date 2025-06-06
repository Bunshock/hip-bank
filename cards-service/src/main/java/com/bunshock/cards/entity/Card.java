package com.bunshock.cards.entity;

import com.bunshock.cards.enums.CardType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "cards")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@SuperBuilder
public class Card extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id", unique = true, nullable = false)
    private Long cardId;

    @Column(name = "mobile_number", nullable = false)
    private String mobileNumber;

    @Column(name = "card_number", unique = true, nullable = false)
    private String cardNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_type", nullable = false)
    private CardType cardType;

    @Column(name = "card_limit", nullable = false)
    private Double cardLimit;

    @Column(name = "amount_used", nullable = false)
    private Double amountUsed;

    @Column(name = "available_amount", nullable = false)
    private Double availableAmount;

}
