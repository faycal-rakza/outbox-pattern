package com.faycal.outboxsave.entity;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.avro.specific.SpecificRecord;
import org.apache.avro.Schema;

import java.io.Serializable;

/**
 * This class is needed to deserialize old TruckEvent objects from the database.
 * It has the same serialVersionUID as the original TruckEvent class.
 */
public class TruckEvent extends SpecificRecordBase implements SpecificRecord, Serializable {
    private static final long serialVersionUID = 8865506859680235858L;
    
    private String licensePlate;
    private String color;
    private String eventType;
    
    public TruckEvent() {
    }
    
    public TruckEvent(String licensePlate, String color, String eventType) {
        this.licensePlate = licensePlate;
        this.color = color;
        this.eventType = eventType;
    }
    
    @Override
    public Schema getSchema() {
        return TruckOutEvent.getClassSchema();
    }
    
    @Override
    public Object get(int field) {
        switch (field) {
            case 0: return licensePlate;
            case 1: return color;
            case 2: return eventType;
            default: throw new IndexOutOfBoundsException("Invalid field index: " + field);
        }
    }
    
    @Override
    public void put(int field, Object value) {
        switch (field) {
            case 0: licensePlate = (String) value; break;
            case 1: color = (String) value; break;
            case 2: eventType = (String) value; break;
            default: throw new IndexOutOfBoundsException("Invalid field index: " + field);
        }
    }
}