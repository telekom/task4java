/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: Tuple.java
 */
package com.task4java;

public class Tuple<L, R> {
    
    public final L left;
    public final R right;

    public Tuple( final L left, final R right ) {
        this.left = left;
        this.right = right;
    }

    @SuppressWarnings("rawtypes")
	@Override
    public boolean equals( final Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        final Tuple tuple = (Tuple) o;
        
        return (this.left == null ? tuple.left == null : this.left.equals(tuple.left))
            && (this.right == null ? tuple.right == null : this.right.equals(tuple.right));
    }

    @Override
    public int hashCode() {
        int result = this.left != null ? left.hashCode() : 0;
        result = 31 * result + ( this.right != null ? right.hashCode() : 0 );
        return result;
    }
    
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        
        builder.append("[left: ");
        builder.append(this.left == null ? "<null>" : left.toString());
        builder.append(", right: ");
        builder.append(this.right == null ? "<null>" : right.toString());
        builder.append("]");
        
        return builder.toString();
    }
}

