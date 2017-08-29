package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * Class is used as a ray caster.
 * 
 * @author Borna Feldšar
 * @version 1.0
 *
 */
public class RayCaster {

	/**
	 * Method started when program is run.
	 * 
	 * @param args
	 *            main method arguments
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0),
				new Point3D(0, 0, 0), new Point3D(0, 0, 10), 20, 20);
	}

	/**
	 * Returns new IRayTracerProducer
	 * 
	 * @return new IRayTracerProducer
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new RayTracerProducer();
	}

	public static class RayTracerProducer implements IRayTracerProducer {
		ForkJoinPool pool;
		
		/**
		 * Constructor creates new instance of {@link ForkJoinPool}
		 */
		public RayTracerProducer() {
			pool = new ForkJoinPool();
		}

		@Override
		public void produce(Point3D eye, Point3D view, Point3D viewUp,
				double horizontal, double vertical, int width, int height,
				long requestNo, IRayTracerResultObserver observer) {
			System.out.println("Započinjem izračune...");
			short[] red = new short[width * height];
			short[] green = new short[width * height];
			short[] blue = new short[width * height];
			Point3D og = view.sub(eye).normalize();
			Point3D vuv = viewUp.normalize();
			// j' =VUV −OG(OG*VUV)
			Point3D yAxis = vuv.sub(og.scalarMultiply(og.scalarProduct(vuv)))
					.normalize();
			Point3D xAxis = og.vectorProduct(yAxis).normalize();
			Point3D screenCorner = view.sub(
					xAxis.scalarMultiply(horizontal / 2)).add(
					yAxis.scalarMultiply(vertical / 2));
			double horizDivWidth = horizontal / (width - 1);
			double vertDivHeight = vertical / (height - 1);
			Scene scene = RayTracerViewer.createPredefinedScene();
			calculateParallel(height, width, screenCorner, xAxis, yAxis,
					horizDivWidth, vertDivHeight, red, green, blue, scene, eye);
			System.out.println("Izračuni gotovi...");
			observer.acceptResult(red, green, blue, requestNo);

			System.out.println("Dojava gotova...");
		}

		/**
		 * Method is used to calculate colors for the image.
		 * 
		 * @param scene
		 *            Scene on image
		 * @param ray
		 *            Ray from observer (eye)
		 * @param rgb
		 *            colors to paint
		 */
		private void tracer(Scene scene, Ray ray, short[] rgb) {

			RayIntersection intersectionMin = getClosestIntersection(ray, scene);
			if (intersectionMin == null) {
				rgb[0] = rgb[1] = rgb[2] = 0;
			} else {
				determineColorFor(intersectionMin, scene, rgb, ray);
			}
		}

		/**
		 * Method returns RayIntersection between closest object from objects
		 * provided and ray source. If there is no such intersection, null is
		 * returned.
		 * 
		 * @param scene
		 *            scene
		 * @param ray
		 *            Ray which intersection is looked for
		 * @return RayIntersection between closest object from objects provided
		 *         and ray source or null if there is no intersection
		 */
		private RayIntersection getClosestIntersection(Ray ray, Scene scene) {
			RayIntersection intersectionMin = null;

			double minDinstance = 0;
			for (GraphicalObject object : scene.getObjects()) {
				RayIntersection intersection = object
						.findClosestRayIntersection(ray);
				if (intersection == null) {
					continue;
				}
				double distance = intersection.getDistance();
				if (distance < minDinstance || intersectionMin == null) {
					intersectionMin = intersection;
					minDinstance = distance;
				}

			}
			return intersectionMin;
		}

		/**
		 * Method is used to add colors (diffuse + ambient)
		 * 
		 * @param scene
		 *            Scene with objects
		 * @param ray
		 *            Ray from eye (observer)
		 * @param intersectionOrig
		 *            Intersection of ray from eye(observer) and object which
		 *            pixel need to be painted.
		 * @param rgb
		 *            Colors of that pixel
		 */
		private void determineColorFor(RayIntersection intersectionOrig,
				Scene scene, short[] rgb, Ray ray) {
			rgb[0] = rgb[1] = rgb[2] = 15;
			for (LightSource light : scene.getLights()) {
				Ray lightRay = Ray.fromPoints(light.getPoint(),
						intersectionOrig.getPoint());
				RayIntersection intersectionClosest = getClosestIntersection(
						lightRay, scene);
				if (intersectionClosest == null) {
					continue;
				}
				Point3D interLight = light.getPoint().sub(
						intersectionOrig.getPoint());
				double interLightLength = interLight.norm();

				if (intersectionClosest.getDistance() + 1e-3 < interLightLength) {
					continue;
				}

				// diffuse component
				Point3D toLight = interLight.normalize();
				Point3D normal = intersectionOrig.getNormal();
				double cosinuseDiffuse = normal.scalarProduct(toLight
						.normalize());
				double maxCosDiff = Math.max(0, cosinuseDiffuse);

				// reflective component
				Point3D toEye = ray.direction.negate();
				Point3D reflected = toLight
						.negate()
						.sub(normal.scalarMultiply(toLight.negate()
								.scalarProduct(normal) * 2)).normalize();
				double cosinusReflected = reflected.scalarProduct(toEye);
				double n = intersectionOrig.getKrn();
				double maxCosRefl = 0;
				if (cosinusReflected > 0) {
					maxCosRefl = Math.pow(cosinusReflected, n);
				}

				rgb[0] += light.getR()
						* (intersectionOrig.getKdr() * maxCosDiff + intersectionOrig
								.getKrr() * maxCosRefl);
				rgb[1] += light.getG()
						* (intersectionOrig.getKdg() * maxCosDiff + intersectionOrig
								.getKrg() * maxCosRefl);
				rgb[2] += light.getB()
						* (intersectionOrig.getKdb() * maxCosDiff + intersectionOrig
								.getKrb() * maxCosRefl);

			}

		}

		/**
		 * Calculates color for points using Fork-Join Framework.
		 * 
		 * @param height
		 *            number of pixel per screen column
		 * @param width
		 *            number of pixel per screen row
		 * @param screenCorner
		 *            upper left corner of the screen
		 * @param xAxis
		 *            xAxis, vector i
		 * @param yAxis
		 *            yAxis, vector j
		 * @param horizDivWidth
		 *            value of horizontal/(width-1)
		 * @param vertDivHeight
		 *            value of vertical/(height-1)
		 * @param red
		 *            red color component
		 * @param green
		 *            green color component
		 * @param blue
		 *            blue color component
		 * @param scene
		 *            difened scene
		 * @param eye
		 *            position of human eye
		 */
		private void calculateParallel(int height, int width,
				Point3D screenCorner, Point3D xAxis, Point3D yAxis,
				double horizDivWidth, double vertDivHeight, short[] red,
				short[] green, short[] blue, Scene scene, Point3D eye) {

			int numberOfProcessors = Runtime.getRuntime().availableProcessors();
			int trackLength = 8 * numberOfProcessors;

			class Task extends RecursiveAction {

				private static final long serialVersionUID = 1L;
				int ymin;
				int ymax;

				public Task(int ymin, int ymax) {
					this.ymin = ymin;
					this.ymax = ymax;

				}

				@Override
				protected void compute() {
					if (ymax - ymin <= trackLength) {
						computeSequential(ymin, ymax, width, screenCorner,
								xAxis, yAxis, horizDivWidth, vertDivHeight,
								red, green, blue, scene, eye);
					} else {
						int mid = (ymax + ymin) >>> 1;
						RecursiveAction task1 = new Task(ymin, mid);
						RecursiveAction task2 = new Task(mid, ymax);
						invokeAll(task1, task2);
					}

				}

			}
			Task p = new Task(0, height);
			pool.invoke(p);
		}

		/**
		 * 
		 * @param ymin
		 *            position of beggining screen pixel
		 * @param ymax
		 *            position of ending screen pixel
		 * @param width
		 *            number of pixel per screen row
		 * @param screenCorner
		 *            upper left corner of the screen
		 * @param xAxis
		 *            xAxis, vector i
		 * @param yAxis
		 *            yAxis, vector j
		 * @param horizDivWidth
		 *            value of horizontal/(width-1)
		 * @param vertDivHeight
		 *            value of vertical/(height-1)
		 * @param red
		 *            red color component
		 * @param green
		 *            green color component
		 * @param blue
		 *            blue color component
		 * @param scene
		 *            difened scene
		 * @param eye
		 *            position of human eye
		 */
		private void computeSequential(int ymin, int ymax, int width,
				Point3D screenCorner, Point3D xAxis, Point3D yAxis,
				double horizDivWidth, double vertDivHeight, short[] red,
				short[] green, short[] blue, Scene scene, Point3D eye) {
			short[] rgb = new short[3];
			int offset = ymin * width;
			for (int y = ymin; y < ymax; y++) {
				for (int x = 0; x < width; x++) {
					Point3D screenPoint = screenCorner.add(
							xAxis.scalarMultiply(x * horizDivWidth)).sub(
							yAxis.scalarMultiply(y * vertDivHeight));

					Ray ray = Ray.fromPoints(eye, screenPoint);
					tracer(scene, ray, rgb);
					red[offset] = rgb[0] > 255 ? 255 : rgb[0];
					green[offset] = rgb[1] > 255 ? 255 : rgb[1];
					blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
					offset++;
				}
			}

		}
	}

}
