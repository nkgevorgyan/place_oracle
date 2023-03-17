package org.processmining.plugins.workshop.helloworld;

public class Pair {
    // Pair attributes
    public int index;
    public int value;
 
    // Constructor to initialize pair
    public Pair(int index, int value)
    {
        // This keyword refers to current instance
        this.index = index;
        this.value = value;
    }

    @Override
    public int hashCode() {
       int hash = 3;
       hash = 47 * hash + this.index;
       hash = 47 * hash + this.value;
       return hash;
    }

    @Override
    public boolean equals(Object obj) {
       if (obj == null) {
          return false;
       }
       if (getClass() != obj.getClass()) {
          return false;
       }
       final Pair other = (Pair) obj;
       if (this.index != other.index) {
          return false;
       }
       if (this.value != other.value) {
          return false;
       }
       return true;
    }
}
