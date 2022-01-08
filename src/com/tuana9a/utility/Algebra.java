// 
// Decompiled by Procyon v0.5.36
// 

package com.tuana9a.utility;

public class Algebra
{
    public static double rotateX(final double xOld, final double yOld, final double radian) {
        return xOld * Math.cos(radian) - yOld * Math.sin(radian);
    }
    
    public static double rotateY(final double xOld, final double yOld, final double radian) {
        return xOld * Math.sin(radian) + yOld * Math.cos(radian);
    }
    
    public static double getRotate(final double sourceX, final double sourceY, final double desX, final double desY) {
        final double deltaX = desX - sourceX;
        final double deltaY = desY - sourceY;
        final double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        final double radian = Math.acos(deltaX / distance);
        final double resultRadian = (deltaY < 0.0) ? (-radian) : radian;
        return resultRadian;
    }
}
