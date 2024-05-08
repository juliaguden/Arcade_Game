import java.awt.*;

public class Shark {

        public String name;                //holds the name of the hero
        public int xpos;                //the x position
        public int ypos;                //the y position
        public int dx;                    //the speed of the hero in the x direction
        public int dy;                    //the speed of the hero in the y direction
        public int width;
        public int height;
        public boolean isAlive;
        public Rectangle rec;
        //a boolean to denote if the hero is alive or dead.


        // METHOD DEFINITION SECTION

        // Constructor Definition
        // A constructor builds the object when called and sets variable values.


        //This is a SECOND constructor that takes 3 parameters.  This allows us to specify the hero's name and position when we build it.
        // if you put in a String, an int and an int the program will use this constructor instead of the one above.
        public Shark (int pXpos, int pYpos) {
            xpos = pXpos;
            ypos = pYpos;
            dx =1;
            dy =0;
            width = 70;
            height = 70;
            isAlive = true;
            rec = new Rectangle(xpos, ypos, width, height);
        } // constructor

        //The move method.  Everytime this is run (or "called") the hero's x position and y position change by dx and dy
        public void move() {
            xpos = xpos + dx;
            ypos = 315;
            if (xpos >= 965) {
                xpos = 0;

            }
            if (xpos <= 0) {
                dx = 5;
                width = width + 10;
                height = height + 10;
            }
            rec = new Rectangle(xpos, ypos, width, height);
        }
}
