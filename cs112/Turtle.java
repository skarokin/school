import java.awt.Color;

/*
 * This is an Abstract Data Type (ADT) Turtle
 * Describes a turtle with a position on an (x, y) coordinate plane and an orientation in degrees 
 */

public class Turtle {

    /***** Instance variables: characteristics of the object *****/
    /***** Instance variables have values that, together, identify an object (instance of the class) *****/
    /***** Use the private modifier to hide the characteristics from other classes *****/
    /***** Instance variables are accessible in any methods within the same class *****/

    // the (x, y) position of the turtle (instance variables)
    private double x;   
    private double y;
    
    // the angle the turtle faces (instance variable)
    private double angle;

    // the color of the turtle (instance variable, also need to import the Color class)
    private Color color;

    /***** CONSTRUCTORS *****/
    /***** Constructors have the same class name *****/
    /***** Constructors create and initialize the object (initializes the instance variables) *****/

    // Default constructor (no argument constructor)
    public Turtle (){

        x = 0.0;
        y = 0.0;
        angle = 90.0;
        color = new Color (0, 255, 255);

    }

    // 4 argument constructor 
    public Turtle (double xVal, double yVal, double angleDeg, Color colorTurt){

        // you can use this.x = x; where this.x is the instance variable and x is the constructor argument

        x = xVal;
        y = yVal;
        angle = angleDeg;
        color = colorTurt;

    }

    /***** INSTANCE METHODS: the operations of this data type  *****/
    /***** DO NOT have the word static, because static is a function that belongs to a class *****/
    /***** Instance methods manipulate (have access to) instance variables *****/

    // toString() returns the String representation of the object
    public String toString(){

        String str = "Turtle at (" + x + ", " + y + ")" + " facing angle " + angle + " has color " + color.getRed() + ", " 
        + color.getGreen() + ", " + color.getBlue();
        return str;

    }

    // turnLeft by updating (NOT returning) the turtle's angle (an instance variable)
    public void turnLeft (double delta){

        angle += delta;     // the instance variable is being updated 

    }

    // moveForward by updating (NOT returning) the turtle's position (instance variables)
    // draw a line from old (x, y) to new (x, y)
    public void moveForward (double step){

        double xOld = x;
        double yOld = y;

        // the instance variables x and y are now updated by step size 
        x += step * Math.cos(Math.toRadians(angle));
        y += step * Math.sin(Math.toRadians(angle));

        // draw the path the turtle takes   
        StdDraw.setPenColor(color);
        StdDraw.line(xOld, yOld, x, y);

    }

    /***** GETTER METHOD *****/
    /***** Getter methods allow other classes to view instance variable values *****/
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public double getAngle(){
        return angle;
    }
    public Color getColor(){
        return color;
    }

    /***** SETTER METHOD *****/
    /***** Setter methdos allow other classes to change the value of the instance variables *****/
    public void setX(double x){
        // to differentiate between the argument value x and the instanc value x, 
        // use this.x 
        // this.x refers to the instance variable 
        this.x = x;
    }
    public void setY(double y){
        this.y = y;
    }
    public void setAngle(double angle){
        this.angle = angle;
    }
    // not writing a setColor because turtle will forever be same color 

    /***** EQUALS METHOD *****/
    /***** Equals method finds if two instantiations of object Turtle is the same *****/
    public boolean equals (Object other) {

        // is other of type turtle?

        if (other instanceof Turtle){
            // cast other as Turtle
            Turtle o = (Turtle)other;

            // two turtles are the same if: same position, same color, same angle, same color
            boolean turtlesSame = (this.x == o.x) && (this.y == o.y) && (this.angle == o.angle)
            && (this.color.equals(o.color));
            
            return turtlesSame;

        } else {
            // other is not of the type turtle because other is its own object and it knows its own type
            return false;
        }

    }

    /***** TEST CLIENT *****/
    /***** Static methods DO NOT have access to the instance variables *****/
    public static void main (String [] args){

        // t1 is a reference to an object of type Turtle 
        Turtle t1 = new Turtle();       // invoke default constructor

        // t2 is a reference to another object of type Turtle 
        Turtle t2 = new Turtle(0.5, 0.5, 45, new Color(255, 0, 0));

        // set canvas size
        StdDraw.setXscale(-1, 5);
        StdDraw.setYscale(-1, 5);

        t1.turnLeft(30);          // t1 turns left
        t1.moveForward(0.4);      // t1 steps forward

        t2.moveForward(0.1);

        StdOut.println(t1.toString());
        StdOut.println(t2.toString());

        StdOut.println(t1.equals(t2));

    }

}
