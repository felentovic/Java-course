package hr.fer.zemris.java.raytracer.model;

/**
 * Sphere implements {@link GraphicalObject} and represents sphere with center
 * point, radius and coefficents for RGB colors.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class Sphere extends GraphicalObject {
	private Point3D center;
	private double radius;

	/**
	 * diffuse components
	 */
	private double kdr;
	private double kdg;
	private double kdb;
	/**
	 * reflective components
	 */
	private double krr;
	private double krg;
	private double krb;
	private double krn;

	/**
	 * Constructor for sphere object.
	 * 
	 * @param center
	 *            center point of sphere
	 * @param radius
	 *            sphere radius
	 * @param kdr
	 *            diffuse coefficient for red color
	 * @param kdg
	 *            diffuse coefficient for green color
	 * @param kdb
	 *            diffuse coefficient for blue color
	 * @param krr
	 *            reflective coefficient for red color
	 * @param krg
	 *            reflective coefficient for green color
	 * @param krb
	 *            reflective coefficient for blue color
	 * @param krn
	 *            reflective coefficient n
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg,
			double kdb, double krr, double krg, double krb, double krn) {
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		// d=-(l*(o-c)) +/- sqrt((l*(o-c))^2-||o-c||^2+r^2)
		Point3D oc = ray.start.sub(center);
		double lMultyoc = ray.direction.scalarProduct(oc);
		double determinant = lMultyoc * lMultyoc - Math.pow(oc.norm(), 2)
				+ radius * radius;
		if (determinant < 0) {
			return null;
		}

		double d0 = 0;
		double d1 = 0;
		double d2 = 0;
		boolean oneIntersection = false;
		if (determinant < 1e-12) {
			oneIntersection = true;
			d0 = -lMultyoc;
		} else {
			double sqrtdeterminant = Math.sqrt(determinant);
			oneIntersection = false;
			d1 = -lMultyoc + sqrtdeterminant;
			d2 = -lMultyoc - sqrtdeterminant;
		}
		Point3D tmp = ray.direction;
		if (oneIntersection) {
			Point3D intersection = ray.start.add(tmp.scalarMultiply(d0));
			return new RayIntersectionImpl(intersection, d0, true);

		} else {
			Point3D intersection1 = ray.start.add(tmp.scalarMultiply(d1));
			Point3D intersection2 = ray.start.add(tmp.scalarMultiply(d2));

			double ocNorm = oc.norm();
			boolean outer = ocNorm <= center.sub(intersection1).norm()
					&& ocNorm <= center.sub(intersection2).norm();

			if (outer) {
				return new RayIntersectionImpl(intersection1, d1, true);
			} else {
				return new RayIntersectionImpl(intersection2, d2, false);

			}
		}
	}

	/**
	 * Class extendes abstract class {@link RayIntersection} and implements
	 * abstract methods
	 * 
	 * @author Borna Feldšar
	 * @version 1.0
	 *
	 */
	class RayIntersectionImpl extends RayIntersection {

		protected RayIntersectionImpl(Point3D point, double distance,
				boolean outer) {
			super(point, distance, outer);

		}

		@Override
		public Point3D getNormal() {
			return getPoint().sub(center).normalize();
		}

		@Override
		public double getKrr() {
			return krr;
		}

		@Override
		public double getKrn() {
			return krn;
		}

		@Override
		public double getKrg() {
			return krg;
		}

		@Override
		public double getKrb() {
			return krb;
		}

		@Override
		public double getKdr() {
			return kdr;
		}

		@Override
		public double getKdg() {
			return kdg;
		}

		@Override
		public double getKdb() {
			return kdb;
		}
	}

}
