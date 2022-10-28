package net.dirtcraft.plugins.dirtplaytime.utils.gradient;

@FunctionalInterface
public interface Interpolator {

	double[] interpolate(double from, double to, int max);
}
