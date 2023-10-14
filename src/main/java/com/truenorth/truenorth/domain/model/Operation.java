package com.truenorth.truenorth.domain.model;

import com.truenorth.truenorth.domain.model.enums.OperationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

@Getter
@Entity
@AllArgsConstructor()
@NoArgsConstructor
@Table(name = "operation")
public class Operation {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "cost", nullable = true, precision = 2)
    private BigDecimal cost;
    @Basic
    @Column(name = "type", nullable = true)
    @Enumerated(EnumType.STRING)
    private OperationType type;
    @OneToMany(mappedBy = "operationByOperationId")
    private Collection<Record> recordsById;

    public void setId(Long id) {
        this.id = id;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation that = (Operation) o;
        return Objects.equals(id, that.id) && Objects.equals(cost, that.cost) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cost, type);
    }

    public void setRecordsById(Collection<Record> recordsById) {
        this.recordsById = recordsById;
    }
}
