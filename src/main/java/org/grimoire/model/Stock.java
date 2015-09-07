package org.grimoire.model;

import static org.grimoire.util.Maps.entry;
import static org.grimoire.util.Maps.unmodifiableMapOf;
import java.util.Map;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import org.grimoire.json.MoneyDeserializer;
import org.grimoire.json.MoneySerializer;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.joda.money.Money;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
public class Stock extends BaseEntity {

    @ManyToOne(optional = false)
    private Card card;

    @Enumerated(EnumType.STRING)
    private Condition condition;

    @Basic
    private boolean premium;

    @Basic
    private int onHand;

    @Column
    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyAmount",
        parameters = @Parameter(name = "currencyCode", value = "USD") )
    @JsonSerialize(using = MoneySerializer.class)
    @JsonDeserialize(using = MoneyDeserializer.class)
    private Money price;

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public int getOnHand() {
        return onHand;
    }

    public void setOnHand(int onHand) {
        this.onHand = onHand;
    }

    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }

    @Override
    protected Map<String, Object> toStringParts() {
        //@formatter:off
        return unmodifiableMapOf(
                entry("card", card.getName()),
                entry("condition", condition),
                entry("premium", premium),
                entry("onHand", onHand),
                entry("price", price));
        //@formatter:on
    }

}
