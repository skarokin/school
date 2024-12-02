import java.awt.Color;

public class DrunkenTurtles {
    
    // this is a client program because it uses the Turtle.java
    public static void main (String [] args){

        StdOut.println("Enter the sanctuary number of turtles");
        int numTurtles = StdIn.readInt();

        StdOut.println("Enter the number of steps each turtle takes");
        int numSteps = StdIn.readInt();

        StdOut.println("Choose a step size for each turtle");
        double stepSize = StdIn.readDouble();

        // allocate enough space to hold numTurtles of array type Turtle
        Turtle [] turtles = new Turtle [numTurtles];

        // instantiate the turtles object
        // each turtle is one object, and each turtle is in one array index 
        for (int i = 0; i < turtles.length; i++){
            
            // positions
            double x = StdRandom.uniform(0.0, 1.0);
            double y= StdRandom.uniform(0.0, 1.0);

            // color
            int randomRed = StdRandom.uniform(0, 256);
            int randomGreen = StdRandom.uniform(0, 256);
            int randomBlue = StdRandom.uniform(0, 256);
            Color randomColor = new Color(randomRed, randomGreen, randomBlue);

            // now, instantiate the turtle object             
            turtles[i] = new Turtle(x, y, 0.0, randomColor);
        }

        // make each turtle turn left by a random angle
        // make each turtle take one sizeStep for a total of numSteps

        StdDraw.setXscale(-1, 5);
        StdDraw.setYscale(-1, 5);

        for (int s = 0; s < numSteps; s++){
            for (int i = 0; i < turtles.length; i++){

                // angle
                double angle = StdRandom.uniform(0.0, 361.0);

                // turn left
                turtles[i].turnLeft(angle);
                turtles[i].moveForward(stepSize);

            }
        }

    }

}
