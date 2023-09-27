package com.epam.rd.autotasks.collections;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class NewPostOfficeStorageImpl implements NewPostOfficeStorage {
    private List<Box> parcels;

    /**
     * Creates internal storage for becoming parcels
     */
    public NewPostOfficeStorageImpl() {
        parcels = new LinkedList<>();
    }

    /**
     * Creates own storage and appends all parcels into own storage.
     * It must add either all the parcels or nothing, if an exception occurs.
     *
     * @param boxes a collection of parcels.
     * @throws NullPointerException if the parameter is {@code null}
     *                              or contains {@code null} values.
     */
    public NewPostOfficeStorageImpl(Collection<Box> boxes) {
        if (boxes == null )throw new NullPointerException();
        for (Box box : boxes) {
            if (box == null)throw new NullPointerException();
        }
        parcels = new LinkedList<>(boxes);
    }

    @Override
    public boolean acceptBox(Box box) {
        if (box == null)throw new NullPointerException();
        parcels.add(box);
        return true;
    }

    @Override
    public boolean acceptAllBoxes(Collection<Box> boxes) {
        if (boxes == null)throw new NullPointerException();
        for (Box box : boxes) {
            if (box == null)throw new NullPointerException();
        }
        parcels.addAll(boxes);
        return true;
    }

    @Override
    public boolean carryOutBoxes(Collection<Box> boxes) {
        if (boxes == null)throw new NullPointerException();
        for (Box box : boxes) {
            if (box == null) throw new NullPointerException();
        }
        return parcels.removeAll(boxes);
    }

    @Override
    public List<Box> carryOutBoxes(Predicate<Box> predicate) {

        List<Box> boxes = new LinkedList<>();
        Iterator<Box> itr = parcels.iterator();
        while (itr.hasNext()){
            Box box = itr.next();
            if (predicate.test(box))boxes.add(box);
        }
        parcels.removeAll(boxes);
        return boxes;
    }

    @Override
    public List<Box> getAllWeightLessThan(double weight) {
        if (weight <= 0)throw new IllegalArgumentException();
        Predicate<Box> predicate = e -> e.getWeight() < weight;
        return searchBoxes(e -> e.getWeight() < weight);
    }

    @Override
    public List<Box> getAllCostGreaterThan(BigDecimal cost) {
        if (cost.max(BigDecimal.ZERO).equals(BigDecimal.ZERO) && !cost.equals(BigDecimal.ZERO))throw new IllegalArgumentException();
        Predicate<Box> predicate = e -> (e.getCost().max(cost).equals(e.getCost()) && !cost.equals(e.getCost()));
        return searchBoxes(predicate);
    }

    @Override
    public List<Box> getAllVolumeGreaterOrEqual(double volume) {
        if (volume < 0)throw new IllegalArgumentException();
        return searchBoxes(e -> e.getVolume() >= volume);
    }

    @Override
    public List<Box> searchBoxes(Predicate<Box> predicate) {
        if (parcels == null)return null;
        List<Box> boxes = new LinkedList<>();
        Iterator<Box> itr = parcels.iterator();
        while (itr.hasNext()){
            Box box = itr.next();
            if (predicate.test(box)) boxes.add(box);
        }
        return boxes;
    }

    @Override
    public void updateOfficeNumber(Predicate<Box> predicate, int newOfficeNumber) {
        Iterator<Box> itr = parcels.iterator();
        while (itr.hasNext()){
            Box box = itr.next();
            if (predicate.test(box)) box.setOfficeNumber(newOfficeNumber);
        }
    }
}
