package org.gradle.rabbitfighter.game;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import static org.lwjgl.opengl.GL11.*;

/**
 * @author Joshua Mivhael Waggoner and Dylan OttoKrider
 * 
 */
public class Ex6 extends Basic {

	public static void main(String[] args) {
		Ex6 app = new Ex6();
		app.start();
	}
	
	public static final String IMAGE_NAME = "res/images/future.png";

	// instance variables for camera stuff:
	private Triple camPos;
	private Triple penPos;
	private double camAzimuth;

	// texture
	private Texture texture3;

	// Array Lists
	private ArrayList<Triple> list;
	private ArrayList<Triple> blocksIn;

	// draw. erase
	private boolean draw, erase;

	// Filename
	private String filename;

	// Input scanner
	Scanner scan;

	// File scanner
	Scanner file;

	// Number of entries in the file we're reading from
	int numEntries;

	// keeps track of number of boxes
	int numberOfBoxes;

	// filnal variables
	final double CAMERA_DISTANCE = 120;
	final double BLOCK_SIZE = 40;

	public Ex6() {
		super(
				"Ex6 - By Dylan & Josh --- Pen Keys: Up, Down, Left, Right, Minus, Plus --- "
						+ "Ship Keys: L, R, U, D, F, B --- Erase: E --- Create: C --- "
						+ "Move Freely: M --- Reset Camera: Z", 1000, 500, 60);
		draw = true;
		erase = false;
		camPos = new Triple(600, 600, 0);
		penPos = new Triple(camPos.x, camPos.y + CAMERA_DISTANCE, camPos.z);
		camAzimuth = 90;
	}

	public void init() {

		// try to open the file and get a scanner
		try {
			// file = openFile();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// assign a texture
		texture3 = getTexture("PNG", IMAGE_NAME, true);

		// new array list for boxes
		list = new ArrayList<Triple>();

		// get the blocks from the file, if it exists
		if (file != null) {
			blocksIn = getBlocks(file);
			for (int i = 0; i < blocksIn.size(); i++) {
				list.add(blocksIn.get(i));
			}
		}

		// ******OPEN GL STUFF*******//

		// Allow depth buffer
		glEnable(GL_DEPTH_TEST);

		// set up projection once and for all:
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(-600, 600, -600, 600, -1000, 1000);

		// set the background color to dark blue
		glClearColor(0.05f, .011f, 0.05f, 1.0f);

		// ******END OPEN GL STUFF*******//

	}

	public void display() {

		// clear the screen to background color and clear the depth buffer:
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		// map view:

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(-600, 600, -600, 600, -1000, 1000);

		// set viewport:
		glViewport(0, 0, 500, 500);

		// switch to model view from this point on:
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		// draw the city = a bunch of transformed clown boxes

		// view transform:
		glTranslated(-500, -500, -550);

		// draw some things with model transforms:
		// city();
		Box();
		// =================================================

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glFrustum(-1, 1, -1, 1, 1, 1000);

		// set viewport:
		glViewport(500, 0, 500, 500);

		// switch to model view from this point on:
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		// view transform:
		// gluLookAt( eye point, a point along line of sight, up vector )
		GLU.gluLookAt((float) camPos.x, (float) camPos.y, (float) camPos.z,
				(float) (camPos.x + Math.cos(Math.toRadians(camAzimuth))),
				(float) (camPos.y + Math.sin(Math.toRadians(camAzimuth))),
				(float) camPos.z, 0, 0, 1);

		// draw some things with model transforms:

		// System.out.println("Number of boxes: " + numberOfBoxes);
		// call the box method that...
		Box();

	}

	public void processInputs() {
		Keyboard.poll();

		double amount = BLOCK_SIZE;

		// Keyboard.getEventKey();
		while (Keyboard.next()) {

			if (Keyboard.isKeyDown(Keyboard.KEY_L)) {
				camPos.x -= amount;

			}

			if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
				camPos.x += amount;

			}

			if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
				camPos.y += amount;

			}

			if (Keyboard.isKeyDown(Keyboard.KEY_B)) {
				camPos.y -= amount;

			}

			if (Keyboard.isKeyDown(Keyboard.KEY_U)) {
				camPos.z += amount;

			}

			if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
				camPos.z -= amount;

			}

			if (Keyboard.isKeyDown(Keyboard.KEY_P)) {
				camAzimuth += 10;
				// penAzimuth += 5;

			}

			if (Keyboard.isKeyDown(Keyboard.KEY_O)) {
				camAzimuth -= 10;

			}

			if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
				draw = true;
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
				erase = true;
				draw = false;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
				penPos.y += amount;

			}
			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
				penPos.y -= amount;

			}
			if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
				penPos.x += amount;

			}
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
				penPos.x -= amount;

			}
			if (Keyboard.isKeyDown(Keyboard.KEY_MINUS)) {
				penPos.z -= amount;

			}
			if (Keyboard.isKeyDown(Keyboard.KEY_EQUALS)) {
				penPos.z += amount;

			}

			if (Keyboard.isKeyDown(Keyboard.KEY_M)) {
				draw = false;
				erase = false;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
				saveBlocks();
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
				if (draw == true)
					draw = false;
				else
					draw = true;

			}
			if (Keyboard.isKeyDown(Keyboard.KEY_Z)) {
				resetCamera();

			}
			Keyboard.next();
		}

		if (draw) {

			boolean overlap = false;

			for (int i = 0; i < list.size(); i++) {
				// if the pen is on a box that's already there.
				if (((list.get(i).x == penPos.x && list.get(i).y == penPos.y) && (list
						.get(i).z == penPos.z))) {
					overlap = true;
				}
			}// end for

			// if there's no overlap
			if (!overlap) {
				list.add(new Triple(penPos.x, penPos.y, penPos.z));
				numberOfBoxes++;
			} else {
				// System.out.println(overlap);
			}// end else/if

		} else if (erase == true) {
			erase();
		}
	}

	// gets called every frame
	private void Box() {

		// the block in fron of us, or the pen if you will
		block(new Triple(penPos.x, penPos.y, penPos.z), 0);

		for (int i = 0; i < list.size() - 1; i++) {
			block(new Triple(list.get(i).x, list.get(i).y, list.get(i).z), 0);
		}

	}// end box

	private void erase() {
		for (int i = 0; i < list.size() - 1; i++) {
			if (penPos.x == list.get(i).x && penPos.y == list.get(i).y
					&& penPos.z == list.get(i).z) {
				list.remove(i);
				numberOfBoxes--;
			}
		}
	}// end erase

	private void resetCamera() {
		// camPos = new Triple(600, 600, 0);
		camPos.x = penPos.x;
		camPos.y = penPos.y - CAMERA_DISTANCE;
		camPos.z = penPos.z;
		camAzimuth = 90;
	}// end reset camera

	@SuppressWarnings("resource")
	public Scanner openFile() {

		// Scanner fo input
		Scanner scannerIn = new Scanner(System.in);
		System.out
				.println("\nPlease enter a filename, or 'new' to start fresh");
		filename = scannerIn.next();

		Scanner fileScanner;

		try {
			// Open the new scanner with the file
			fileScanner = new Scanner(new File(filename));

		} catch (FileNotFoundException e) {

			System.out.println("\nCould not find the specified file. "
					+ "\nMust be located within the resources folder.\n");
			e.printStackTrace();
			return null;
		}// end try/catch

		return fileScanner;

	}// end openFile

	private void saveBlocks() {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(filename));
			out.write("" + numberOfBoxes + "\n");
			// PrintWriter out = new PrintWriter("exampleTest.txt");
			for (int i = 0; i < list.size(); i++) {
				// out.write("test " + "\n");
				out.write(list.get(i).x + " " + list.get(i).y + " "
						+ list.get(i).z);
				// out.write("\n");
				out.newLine();
			}
			out.close();
		} catch (IOException e) {
		}
		System.exit(0);
	}// end saveBlocks

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private ArrayList<Triple> getBlocks(Scanner file) {

		numEntries = file.nextInt();

		ArrayList l = new ArrayList<Triple>();

		if (numEntries > 0) {
			for (int i = 0; i < numEntries; i++) {
				l.add(new Triple(file.nextDouble(), file.nextDouble(), file
						.nextDouble()));
			}
		}

		// return the points from file
		return l;

	}// end getPoints

	private void block(Triple triple, double angle) {
		glPushMatrix();
		glTranslated(triple.x, triple.y, triple.z);
		glRotated(angle, 0, 0, 1);
		glScaled(20, 20, 20);
		standardCube();
		glPopMatrix();
	}// end block

	// draw a 2 by 2 by 2 axis-aligned rectangular prism
	// centered at the origin
	private void standardCube() {
		// front face
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		// texture2.bind();
		// glColor3f(1.0f, 0.0f, 0.0f);
		texture3.bind();
		glBegin(GL_POLYGON);
		glTexCoord2f(0, 0);
		glVertex3d(-1, -1, -1);
		glTexCoord2f(.5f, 0);
		glVertex3d(1, -1, -1);
		glTexCoord2f(.5f, .5f);
		glVertex3d(1, -1, 1);
		glTexCoord2f(0, .5f);
		glVertex3d(-1, -1, 1);
		glEnd();

		// rear face
		texture3.bind();
		glBegin(GL_POLYGON);
		glTexCoord2f(0, 0);
		glVertex3d(1, 1, -1);
		glTexCoord2f(.5f, 0);
		glVertex3d(-1, 1, -1);
		glTexCoord2f(.5f, .5f);
		glVertex3d(-1, 1, 1);
		glTexCoord2f(0, .5f);
		glVertex3d(1, 1, 1);
		glEnd();

		// right face
		texture3.bind();
		glBegin(GL_POLYGON);
		glTexCoord2f(0, 0);
		glVertex3d(1, -1, -1);
		glTexCoord2f(.5f, 0);
		glVertex3d(1, 1, -1);
		glTexCoord2f(.5f, .5f);
		glVertex3d(1, 1, 1);
		glTexCoord2f(0, .5f);
		glVertex3d(1, -1, 1);
		glEnd();

		// left face
		// glColor3f( 1.0f, 0.0f, 1.0f );
		texture3.bind();
		glBegin(GL_POLYGON);
		glTexCoord2f(0, 0);
		glVertex3d(-1, 1, -1);
		glTexCoord2f(.5f, 0);
		glVertex3d(-1, -1, -1);
		glTexCoord2f(.5f, .5f);
		glVertex3d(-1, -1, 1);
		glTexCoord2f(0, .5f);
		glVertex3d(-1, 1, 1);
		glEnd();

		// glDisable( GL_TEXTURE_2D );
		// glDisable( GL_BLEND );

		// top face
		// glColor3f( 1.0f, 1.0f, 0.0f );
		texture3.bind();
		glBegin(GL_POLYGON);
		glTexCoord2f(0, 0);
		glVertex3d(-1, -1, 1);
		glTexCoord2f(.75f, 0);
		glVertex3d(1, -1, 1);
		glTexCoord2f(.75f, .5f);
		glVertex3d(1, 1, 1);
		glTexCoord2f(0, .5f);
		glVertex3d(-1, 1, 1);
		glEnd();

		// bottom face
		// glColor3f( 0.0f, 1.0f, 1.0f );
		texture3.bind();
		glBegin(GL_POLYGON);
		glTexCoord2f(.75f, 0);
		glVertex3d(-1, -1, -1);
		glTexCoord2f(.75f, .5f);
		glVertex3d(-1, 1, -1);
		glTexCoord2f(0, .5f);
		glVertex3d(1, 1, -1);
		glTexCoord2f(0, 0);
		glVertex3d(1, -1, -1);
		glEnd();

	}

	private Texture getTexture(String type, String fileName, boolean showInfo) {
		Texture texture = null;

		try {
			texture = TextureLoader.getTexture(type,
					ResourceLoader.getResourceAsStream(fileName), true);

			if (showInfo) {
				System.out.println("Texture loaded: " + texture);
				System.out
						.println(">> Image width: " + texture.getImageWidth());
				System.out.println(">> Image height: "
						+ texture.getImageHeight());
				System.out.println(">> Texture width: "
						+ texture.getTextureWidth());
				System.out.println(">> Texture height: "
						+ texture.getTextureHeight());
				System.out.println(">> Texture ID: " + texture.getTextureID());
			}
		} catch (IOException e) {
			System.out.println("Loading of texture from file [" + fileName
					+ "] failed");
			e.printStackTrace();
			System.exit(1);
		}

		return texture;

	}// getTexture

}