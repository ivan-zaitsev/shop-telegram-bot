package ua.ivan909020.bot.domain.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "order_states")
public class OrderState {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_states_seq")
    @SequenceGenerator(name = "order_states_seq", sequenceName = "order_states_id_seq", allocationSize = 1)
    private Integer id;

    @Column(nullable = false)
    private String state;

    public OrderState() {
    }

    public OrderState(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderState that = (OrderState) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, state);
    }

    @Override
    public String toString() {
        return "OrderState{" +
                "id=" + id +
                ", state='" + state + '\'' +
                '}';
    }

}
