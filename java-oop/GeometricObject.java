
public class GeometricObject {
	
	// Private data fields 
	private String color = "yellow";
	private boolean filled;
	
	// Declare method for subclass
	double getArea(){
		return 0;
	}
	
	// Default GeometricObject constructor 
	public GeometricObject() {
	} // Default constructor close 
	
	// GeometricObject with specified color and filled value
	public GeometricObject(String color, boolean filled) {
		this.color = color;
		this.filled = filled;
	} // Specified constructor close 
	
	// Get color 
	public String getColor() {
		return color;
	} // Get color close
	
	// Set new color 
	public void setColor(String color) {
		this.color = color;
	} // Set new color close 
	
	// Get filled 
	public boolean isFilled() {
		return filled;
	} // Get filled close 
	
	// Set new filled 
	public void setFilled(boolean filled) {
		this.filled = filled;
	} // Set new filled close
	

	
// ***********************************************//

public class Triangle extends GeometricObject {
	
	// Private data field
	private double side1;
	private double side2;
	private double side3;
	
	// Default Triangle constructor 
	public Triangle() {
		this.side1 = 0;
		this.side2 = 0;
		this.side3 = 0;
	} // Default Triangle close 
	
	// Triangle with specified data
	public Triangle(double side1, double side2, double side3) {
		this.side1 = side1;
		this.side2 = side2;
		this.side3 = side3;
	} // Specified constructor close 
	
	// Inherit superclass public methods 
	public Triangle (double side1, double side2, double side3, String color, boolean filled) {
		this.side1 = side1;
		this.side2 = side2;
		this.side3 = side3;
		setColor(color);
		setFilled(filled);
	} // Inheritance closing bracket 
	
	// Get side1
	public double side1() {
		return side1;
	} // Get side1 close
	
	// Set side1
	public void setSide1(double side1) {
		this.side1 = side1;
	} // Set side1 close
	
	// Get side2
	public double side2() {
		return side2;
	} // Get side2 close
		
	// Set side2
	public void setSide2(double side2) {
			this.side2 = side2;
	} // Set side2 close
	
	// Get side3
	public double side3() {
		return side3;
	} // Get side3 close
		
	// Set side1
	public void setSide3(double side3) {
		this.side3 = side3;
	} // Set side3 close
	
	// Area method 
	@Override
	public double getArea() {
		double s = (side1 + side2 + side3) / 2.0;
		double area = Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));
		return area;
	} // Area method closing bracket 
	
	// Perimeter method 
	public double getPerimeter() {
		return side1 + side2 + side3;
	} // Perimeter method closing bracket 
		
	public String toString() {
        return "Triangle [side1 = " + side1 + ", side2 = " + side2 + ", side3 = " + side3 + "]";
    }
	
} // Subclass closing bracket 


// ************************************************//

	public static void main(String[] args) {
		
		// Create GeoObject instance 
		GeometricObject GeoObject = new GeometricObject();
		
		// Create triangle object 
	    GeometricObject.Triangle myTriangle = GeoObject.new Triangle();
			
		myTriangle.setSide1(1);
		myTriangle.setSide2(1.5);
		myTriangle.setSide3(1);
		myTriangle.setColor("Yellow");
		myTriangle.setFilled(true);
	
		System.out.println(myTriangle.toString());
		System.out.println("Triangle Area: " + myTriangle.getArea());
		System.out.println("Triangle Perimeter: " + myTriangle.getPerimeter());
		System.out.println("Is Triangle Filled: " + myTriangle.isFilled());
		System.out.println("Triangle Color: " + myTriangle.getColor());
		
		
	} // Main method close 

	

} // Superclass closing bracket 

