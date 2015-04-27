/*  framework to be extended to make desired
    graphical, event-driven application

    Should 

        make a constructor that calls super and initializes things
        that should happen before Display is created

    and override

        init:  initialize things that happen after Display is created
        processInputs:  respond to input events
        update:  update instance variables to simulate advancing
                 time and/or response to inputs
        display:  draw graphics showing current state of things

    @author: Joshua Michael Waggoner (@rabbitfighter81)
*/

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Basic {
  // test this basic application 
  public static void main(String[] args) {
    Basic app = new Basic("Basic", 1000, 500, 60);
    app.start();
  } 

  // instance variables 
  private String title; // title of the application
  private int frameNumber; // total number of frames displayed
  private int framesPerSecond; // target fps
  private int pixelWidth, pixelHeight; // pixel dimension of drawing area
  private boolean running; // is the application running
  private boolean closeable; // is the window manually closeable
  private boolean resizeable; // is the window manually resizable

  // construct basic application with given title, pixel width and height
  // of drawing area, and frames per second
  public Basic(String appTitle, int pw, int ph, int fps) {
    title = appTitle;
    frameNumber = 0;
    framesPerSecond = fps;
    pixelWidth = pw;
    pixelHeight = ph;
    running = false;
    closeable = true;
    resizeable = false;
  }

  public void start() {
    // create the window
    try {
      Display.setDisplayMode(new DisplayMode(pixelWidth, pixelHeight));
      Display.create();
      Display.setTitle(title);
    } catch (LWJGLException e) {
      System.out.println("Basic constructor failed to create window");
      e.printStackTrace();
      System.exit(1);
    }

    init();

    running = true;

    while (running && (!closeable || !Display.isCloseRequested())) {
      frameNumber++;

      // these three methods should be overridden by extending application:
      processInputs();
      update();
      display();

      Display.update();
      Display.sync(framesPerSecond);
    }

    Display.destroy();

  } 

  // ------------------ these methods can be called by the application --------

  protected int getPixelWidth() {
    return pixelWidth;
  }

  protected int getPixelHeight() {
    return pixelHeight;
  }

  // report which frame 
  protected int getFrameNumber() {
    return frameNumber;
  }

  // report fps (useful for timing in the app)
  protected int getFPS() {
    return framesPerSecond;
  }

  // set the title (perhaps use for successive levels)
  protected void setTitle(String name) {
    title = name;
    Display.setTitle(title);
  }

  // set whether the window can be closed manually
  protected void setCloseable(boolean value) {
    closeable = value;
  }

  // halt the application under program control
  protected void halt() {
    running = false;
  }

  // ------------------ these methods should be overridden ------------------

  protected void init() {}

  protected void processInputs() {}

  protected void update() {}

  protected void display() {}

} 