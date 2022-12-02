package br.com.sw2you.realmeet.domain.entity;

import br.com.sw2you.realmeet.domain.model.Employee;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(name = "allocation")
public class Allocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Room room;
    private Employee employee;
    private String subject;
    private OffsetDateTime startAt;
    private OffsetDateTime endAt;
    private OffsetDateTime createdAt;
    private OffsetDateTime updateAt;

    private Allocation(Builder builder) {
        id = builder.id;
        room = builder.room;
        employee = builder.employee;
        subject = builder.subject;
        startAt = builder.startAt;
        endAt = builder.endAt;
        createdAt = builder.createdAt;
        updateAt = builder.updateAt;
    }

    public Allocation() {

    }

    public Long getId() {
        return id;
    }

    public Room getRoom() {
        return room;
    }

    public Employee getEmployee() {
        return employee;
    }

    public String getSubject() {
        return subject;
    }

    public OffsetDateTime getStartAt() {
        return startAt;
    }

    public OffsetDateTime getEndAt() {
        return endAt;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdateAt() {
        return updateAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Allocation that = (Allocation) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(room, that.room) &&
                Objects.equals(employee, that.employee) &&
                Objects.equals(subject, that.subject) &&
                Objects.equals(startAt, that.startAt) &&
                Objects.equals(endAt, that.endAt) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updateAt, that.updateAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, room, employee, subject, startAt, endAt, createdAt, updateAt);
    }

    @Override
    public String toString() {
        return "Allocation{" +
                "id=" + id +
                ", room=" + room +
                ", employee=" + employee +
                ", subject='" + subject + '\'' +
                ", startAt=" + startAt +
                ", endAt=" + endAt +
                ", createdAt=" + createdAt +
                ", updateAt=" + updateAt +
                '}';
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private Room room;
        private Employee employee;
        private String subject;
        private OffsetDateTime startAt;
        private OffsetDateTime endAt;
        private OffsetDateTime createdAt;
        private OffsetDateTime updateAt;

        private Builder() {
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withRoom(Room room) {
            this.room = room;
            return this;
        }

        public Builder withEmployee(Employee employee) {
            this.employee = employee;
            return this;
        }

        public Builder withSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder withStartAt(OffsetDateTime startAt) {
            this.startAt = startAt;
            return this;
        }

        public Builder withEndAt(OffsetDateTime endAt) {
            this.endAt = endAt;
            return this;
        }

        public Builder withCreatedAt(OffsetDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder withUpdateAt(OffsetDateTime updateAt) {
            this.updateAt = updateAt;
            return this;
        }

        public Allocation build() {
            return new Allocation(this);
        }
    }
}
