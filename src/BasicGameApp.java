//Basic Game Application
//Version 2
// Basic Object, Image, Movement
// Astronaut moves to the right.
// Threaded

//K. Chun 8/2018

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;


//*******************************************************************************
// Class Definition Section

public class BasicGameApp implements Runnable, KeyListener {

   //Variable Definition Section
   //Declare the variables used in the program 
   //You can set their initial values too
   
   //Sets the width and height of the program window
	final int WIDTH = 1000;
	final int HEIGHT = 700;

   //Declare the variables needed for the graphics
	public JFrame frame;
	public Canvas canvas;
   public JPanel panel;
   
	public BufferStrategy bufferStrategy;
	public Image backroundpic;
	public Image duckPic;
	public Image frogPic;
	public Image sharkPic;
	public Image GameOverPic;
	public int score = 0;
	public Frog [] fFrog;

   //Declare the objects used in the program
   //These are things that are made up of more than one variable type
	private Duck duck1;
	private Frog frog1;
	private Shark shark1;

   // Main method definition
   // This is the code that runs first and automatically
	public static void main(String[] args) {
		BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
		new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method  
	}


   // Constructor Method
   // This has the same name as the class
   // This section is the setup portion of the program
   // Initialize your variables and construct your program objects here.
	public BasicGameApp() {
      
      setUpGraphics();
       
      //variable and objects
      //create (construct) the objects needed for the game and load up
		backroundpic = Toolkit.getDefaultToolkit().getImage("PondPic.jpeg");
		duckPic = Toolkit.getDefaultToolkit().getImage("DuckPic.png"); //load the picture
		frogPic = Toolkit.getDefaultToolkit().getImage("FROG.png");
		sharkPic = Toolkit.getDefaultToolkit().getImage("Shark.png");
		GameOverPic = Toolkit.getDefaultToolkit().getImage("GameOver.jpeg");
		duck1 = new Duck (300,400);
		frog1 = new Frog (200,600);
		shark1 = new Shark(100, 400);
		fFrog = new Frog[12];
		for(int i=0; i < fFrog.length; i++){
			fFrog[i]=new Frog ((int)(Math.random()*500),(int)(Math.random()*400));
		}
	}// BasicGameApp()

   
//*******************************************************************************
//User Method Section
//
// put your code to do things here.

   // main thread
   // this is the code that plays the game after you set things up
	public void run() {

      //for the moment we will loop things forever.
		while (true) {

         moveThings();  //move all the game objects
			checkIntersections();
         render();  // paint the graphics
         pause(20); // sleep for 10 ms
		}
	}


	public void moveThings() {
		//calls the move( ) code in the objects
		duck1.move();
		shark1.move();
		frog1.move();
		for (int i = 0; i < fFrog.length; i++) {
			fFrog[i].move();
			if (fFrog[i].rec.intersects(duck1.rec)){
				System.out.print("+1");
				fFrog[i].isAlive= false;
			}
		}
	}
	public void checkIntersections(){
		if(frog1.rec.intersects(duck1.rec)){
			//WRITE SOMETHING HERE
			frog1.isAlive=false;
			//System.out.println("INTERSECTED");
			System.out.println(score);
			score=score+ 1;

		}
		if(duck1.rec.intersects(shark1.rec)){
			duck1.isAlive=false;
		}
	}

   //Pauses or sleeps the computer for the amount specified in milliseconds
   public void pause(int time){
   		//sleep
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {

			}
   }

   //Graphics setup method
   private void setUpGraphics() {
      frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.
   
      panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
      panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
      panel.setLayout(null);   //set the layout
   
      // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
      // and trap input events (Mouse and Keyboard events)
      canvas = new Canvas();  
      canvas.setBounds(0, 0, WIDTH, HEIGHT);
      canvas.setIgnoreRepaint(true);
	  canvas.addKeyListener(this);
   
      panel.add(canvas);  // adds the canvas to the panel.
   
      // frame operations
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
      frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
      frame.setResizable(false);   //makes it so the frame cannot be resized
      frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!
      
      // sets up things so the screen displays images nicely.
      canvas.createBufferStrategy(2);
      bufferStrategy = canvas.getBufferStrategy();
      canvas.requestFocus();
      System.out.println("DONE graphic setup");
   
   }


	//paints things on the screen using bufferStrategy
	private void render() {
		Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
		g.clearRect(0, 0, WIDTH, HEIGHT);

		if(duck1.isAlive == false){
			g.drawImage(GameOverPic, 0,0,1000,700,null);
		}else {

			//draw the image of the astronaut
			g.drawImage(backroundpic, 0, 0, 1000, 700, null);
			g.drawImage(duckPic, duck1.xpos, duck1.ypos, duck1.width, duck1.height, null);
			//g.drawImage(frogPic, frog1.xpos, frog1.ypos, frog1.width, frog1.height, null);
			g.drawImage(sharkPic, shark1.xpos, shark1.ypos, shark1.width, shark1.height, null);
			for (int i = 0; i < fFrog.length; i++) {
				if (fFrog[i].isAlive == true) {
					g.drawImage(frogPic, fFrog[i].xpos, fFrog[i].ypos, fFrog[i].width, fFrog[i].height, null);
				}
			}


			g.setColor(Color.white);
			g.drawRect(10, 10, 200, 100);
			g.fillRect(10, 10, 200, 100);
			g.setColor(Color.black);
			g.drawString("scoreboard " + score, 100, 100);
			System.out.println(score);
		}


		g.dispose();
		bufferStrategy.show();
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed (KeyEvent e) {
		if (e.getKeyCode() == 39) {
			duck1.dx = 5;
			duck1.dy = 0;
		}
		if (e.getKeyCode() == 37) {
			duck1.dx = -5;
			duck1.dy = 0;
		}
		if (e.getKeyCode() == 38) {
			duck1.dx = 0;
			duck1.dy = -5;
		}
		if (e.getKeyCode() == 40) {
			duck1.dx = 0;
			duck1.dy = 5;
		}

	}

	public void keyReleased (KeyEvent e){
		if (e.getKeyCode() == 39) {
			duck1.dx = 0;
			duck1.dy = 0;
		}
		if (e.getKeyCode() == 37) {
			duck1.dx = 0;
			duck1.dy = 0;
		}
		if (e.getKeyCode() == 38) {
			duck1.dx = 0;
			duck1.dy = 0;
		}
		if (e.getKeyCode() == 40) {
			duck1.dx = 0;
			duck1.dy = 0;
		}
	}
}