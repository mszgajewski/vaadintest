package com.example.application.data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AbstractEntity)) {
            return false;
        }
        AbstractEntity other = (AbstractEntity) o;

        if(id != null){
            return id.equals(other.id);
        }
        return super.equals(other);
    }

    @Override
    public int hashCode() {
        if (id != null) {
            return id.hashCode();
        }
        return super.hashCode();
    }
}
