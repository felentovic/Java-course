package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Sphere;

import org.junit.Assert;
import org.junit.Test;

public class SphereTest {
	@Test
	public void findClosestRayIntersection_True_OuterIntersection() {
		Point3D startPoint = new Point3D(0, 10, 0);
		Point3D endPoint = new Point3D(0, 0, 0);
		Ray ray = Ray.fromPoints(startPoint, endPoint);
		Point3D center = new Point3D(0, 0, 0);
		Sphere sphere = new Sphere(center, 2, 0, 0, 0, 0, 0, 0, 0);
		RayIntersection intersection = sphere.findClosestRayIntersection(ray);
		Point3D expected = new Point3D(0, 2, 0);
		Assert.assertEquals("x",expected.x, intersection.getPoint().x,1e-4);
		Assert.assertEquals("y",expected.y, intersection.getPoint().y,1e-4);
		Assert.assertEquals("z",expected.z, intersection.getPoint().z,1e-4);

	}
	
	@Test
	public void findClosestRayIntersection_True_InnerIntersection() {
		Point3D startPoint = new Point3D(0, 10, 0);
		Point3D endPoint = new Point3D(0, 0, 0);
		Ray ray = Ray.fromPoints(startPoint, endPoint);
		Point3D center = new Point3D(0, 0, 0);
		Sphere sphere = new Sphere(center, 15, 0, 0, 0, 0, 0, 0, 0);
		RayIntersection intersection = sphere.findClosestRayIntersection(ray);
		Point3D expected = new Point3D(0, -15, 0);
		Assert.assertEquals("x",expected.x, intersection.getPoint().x,1e-4);
		Assert.assertEquals("y",expected.y, intersection.getPoint().y,1e-4);
		Assert.assertEquals("z",expected.z, intersection.getPoint().z,1e-4);

	}
	
	@Test
	public void findClosestRayIntersection_True_NoIntersection() {
		Point3D startPoint = new Point3D(0, 10, 0);
		Point3D endPoint = new Point3D(0, 0, 100);
		Ray ray = Ray.fromPoints(startPoint, endPoint);
		Point3D center = new Point3D(0, 0, 0);
		Sphere sphere = new Sphere(center, 2, 0, 0, 0, 0, 0, 0, 0);
		RayIntersection intersection = sphere.findClosestRayIntersection(ray);
		Assert.assertEquals(null, intersection);

	}
	
	@Test
	public void findClosestRayIntersection_True_OneIntersection() {
		Point3D startPoint = new Point3D(10, 0, 0);
		Point3D endPoint = new Point3D(0, 1e-15, 0);
		Ray ray = Ray.fromPoints(startPoint, endPoint);
		Point3D center = new Point3D(0, 10, 0);
		Sphere sphere = new Sphere(center, 10, 0, 0, 0, 0, 0, 0, 0);
		RayIntersection intersection = sphere.findClosestRayIntersection(ray);
		Point3D expected = new Point3D(0, 0, 0);
		Assert.assertEquals("x",expected.x, intersection.getPoint().x,1e-4);
		Assert.assertEquals("y",expected.y, intersection.getPoint().y,1e-4);
		Assert.assertEquals("z",expected.z, intersection.getPoint().z,1e-4);

	}
}
