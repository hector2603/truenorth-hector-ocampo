package com.truenorth.truenorth.domain.model;

import com.truenorth.truenorth.domain.model.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Entity
@Table(name = "record")
public class Record {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "amount", nullable = true, precision = 2)
    private BigDecimal amount;
    @Basic
    @Column(name = "date", nullable = true)
    private Timestamp date;
    @Basic
    @Column(name = "operation_response", nullable = true, length = 255)
    private String operationResponse;
    @Basic
    @Column(name = "user_balance", nullable = true, precision = 2)
    private BigDecimal userBalance;
    @Basic
    @Column(name = "status", nullable = true)
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    @JoinColumn(name = "operation_id", referencedColumnName = "id", nullable = false)
    private Operation operationByOperationId;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User userByUserId;

    public void setStatus(Status status) {
        this.status = status;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public void setOperationResponse(String operationResponse) {
        this.operationResponse = operationResponse;
    }

    public void setUserBalance(BigDecimal userBalance) {
        this.userBalance = userBalance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Record that = (Record) o;
        return Objects.equals(id, that.id) && Objects.equals(amount, that.amount) && Objects.equals(date, that.date) && Objects.equals(operationResponse, that.operationResponse) && Objects.equals(userBalance, that.userBalance) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, date, operationResponse, userBalance);
    }

    public void setOperationByOperationId(Operation operationByOperationId) {
        this.operationByOperationId = operationByOperationId;
    }

    public void setUserByUserId(User userByUserId) {
        this.userByUserId = userByUserId;
    }
}
