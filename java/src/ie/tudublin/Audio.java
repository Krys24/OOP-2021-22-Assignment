/* OOP 2022 Assignment by:
Ciaran McBride - C20457886
Krystian Pakos - *********
David Niculita - *********

OOP Assignment to show knowledge of the Processing library and PApplet

This program displays many different uses of PApplet to create an Audio Visualizer

*/

package ie.tudublin;

// Importing all relevant packages
import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;

// Audio class
public class Audio extends PApplet
{
    // Adding Variables
    Minim minim;
    AudioPlayer ap;
    AudioInput ai;
    AudioBuffer ab;

    float[] lerpedBuffer;
    float y = 0;
    float smoothedY = 0;
    float smoothedAmplitude = 0;

    String[] names = {"Rose Pattern", "Phyllotaxis", "Square", "Dancing Box", "Noise Loop"};

    // Settings to set the size of the window to fullscreen
    public void settings()
	{
		fullScreen(P3D, SPAN);
        // size(1024,1000);
	}

    // Setup to load the mp3 file, to declare some variables and to chnage the colorMode to HSB
	public void setup() 
	{
        minim = new Minim(this);
        ap = minim.loadFile("noMoney.mp3", 1024);
        ap.play();
        ab = ap.mix;
        colorMode(HSB);

        y = height / 2;
        smoothedY = y;

        lerpedBuffer = new float[width];

		colorMode(HSB);
    }

    // Defining the int mode to be an Int with a value of 0
    int mode = 0;

    // Key pressed which allows the user to pick a mode or exit from the program using the ESC key
    public void keyPressed() {
		if (key >= '0' && key <= '9') {
			mode = key - '0';
		}
        if (keyCode == ESC) 
        {
            exit();
        }
        // Printing out the mode to show the user what they have selected
		println(mode);
	}

    // Mouse clicked function to allow the user to click on a number of options for the current display
    public void mouseClicked()
    {
        int j = height/2 - height/5 -50;

        System.out.println(mouseX + "    " + mouseY);

            for(int i = 0 ; i < names.length; i ++)
            {
                // j = j + height /10;

                int space = height/10;
                    
                // if statements to make sure the mouse clicked function changes the mode for whatever option is selected
                if (mouseX >= width/2 - height/3 - 75 && mouseX <= width/2 - height/3 + 75 && mouseY >= height/2 - height/5 - 25 && mouseY <= height/2 - height/5 + 25)
                {
                    mode = 0; 
                }

                if (mouseX >= width/2 - height/3 - 75 && mouseX <= width/2 - height/3 + 75 && mouseY >= height/2 - height/5 + space - 25 && mouseY <= height/2 - height/5 + space + 25)
                {
                    mode = 1; 
                }

                if (mouseX >= width/2 - height/3 - 75 && mouseX <= width/2 - height/3 + 75 && mouseY >= height/2 - height/5 + (space)*2 - 25 && mouseY <= height/2 - height/5 + (space)*2 + 25)
                {
                    mode = 2; 
                }

                if (mouseX >= width/2 - height/3 - 75 && mouseX <= width/2 - height/3 + 75 && mouseY >= height/2 - height/5 + (space)*3 - 25 && mouseY <= height/2 - height/5 + (space)*3 + 25)
                {
                    mode = 3; 
                }

                if (mouseX >= width/2 - height/3 - 75 && mouseX <= width/2 - height/3 + 75 && mouseY >= height/2 - height/5 + (space)*4 - 25 && mouseY <= height/2 - height/5 + (space)*4 + 25)
                {
                    mode = 4; 
                }
            }
    }

    // Initalizing the float off to the value 0
    float off = 0;

    // Draw class to put drawings onto Standard Output
    public void draw()
	{	
        float average = 0;
        float sum = 0;
        off += 1;
        // Calculate sum and average of the samples
        // Also lerp each element of buffer;
        for(int i = 0 ; i < ab.size() ; i ++)
        {
            sum += abs(ab.get(i));
            lerpedBuffer[i] = lerp(lerpedBuffer[i], ab.get(i), 0.05f);
        }

        average= sum / (float) ab.size();

        smoothedAmplitude = lerp(smoothedAmplitude, average, 0.1f);

        switch (mode) {

            // Case if 0 is selected
			case 0:
            {
                background(0);
                strokeWeight(2);
                translate(width/2, height/2);

                beginShape();
                for (float a = 0; a < TWO_PI; a += 0.02)
                {
                    float f = map(smoothedAmplitude, 0, 0.5f, 0, 750);
                    float cc = map(smoothedAmplitude, 0, 0.5f, 0, 255);

                    noFill();
                    
                    float r = f/3 * cos(f*2 * a);
                    float x = r * sin(a);
                    float y = r * cos(a);
                    stroke(cc, 255, 255);
                    strokeWeight(4);
                    vertex(x,y);
                }
                endShape(CLOSE);

                strokeWeight(20);
                stroke(255);
                noFill();
                rectMode(CENTER);
                square(0, 0, height/2);

                int j = - height/5;

                for(int i = 0 ; i < names.length; i ++)
                {
                    strokeWeight(2);
                    fill(50,255,255);
                    rect(- height/3, j, 150, 50);
                    fill(0);
                    textAlign(CENTER, CENTER);
                    textSize(25);
                    text(names[i],- height/3, j);
                    j = j + height /10;
                }
                break;
            }

            // Case if 1 is selected
            case 1:
            {
                background(0);
                // strokeWeight(2);
                // fill(255);

                double n = 0;
                float c = 4;

                float f = map(smoothedAmplitude, 0, 0.5f, 0, 750);
                float cc = map(smoothedAmplitude, 0, 0.5f, 0, 255);

                for(int i=0 ; i<f*2; i++)
                {
                    float a = (float) (n * 137.5);
                    float r = c * sqrt((float) n);
                    float x = r * cos(a) + width/2;
                    float y = r * sin(a) + height/2;

                    fill(cc,255,255);
                    stroke(0);

                    ellipse(x,y,5,5);
                    n++;
                }


                

                strokeWeight(20);
                stroke(255);
                noFill();
                rectMode(CENTER);
                square(width/2, height/2, height/2);

                int j = height/2 - height/5;

                for(int i = 0 ; i < names.length; i ++)
                {
                    strokeWeight(2);
                    fill(50,255,255);
                    rect(width/2 - height/3, j, 150, 50);
                    fill(0);
                    textAlign(CENTER, CENTER);
                    textSize(25);
                    text(names[i], width/2 - height/3, j);
                    j = j + height /10;
                }
                break;
            }

            // Case if 2 is selected
            case 2:
            {
                background(0);
                strokeWeight(2);

                float f = map(smoothedAmplitude, 0, 0.5f, 0, 750);
                float cc = map(smoothedAmplitude, 0, 0.5f, 0, 255);
                int w = (int) random(-height/4 + 20, height/4 - 20);

                int cx = width/2;

                for(int i = 0 ; i < 100 ; i += 10)
                {
                    stroke(cc,255,255); 
                    fill(cc,255,255);
                    circle(cx + w, height/2 + w, f/4);
                    circle(cx - w, height/2 - w, f/4);
                    circle(cx - w, height/2 + w, f/4);
                    circle(cx + w, height/2 - w, f/4);

                    cx += 1;

                }


                strokeWeight(20);
                stroke(255);
                noFill();
                rectMode(CENTER);
                square(width/2, height/2, height/2);

                int j = height/2 - height/5;

                for(int i = 0 ; i < names.length; i ++)
                {
                    strokeWeight(2);
                    fill(50,255,255);
                    rect(width/2 - height/3, j, 150, 50);
                    fill(0);
                    textAlign(CENTER, CENTER);
                    textSize(25);
                    text(names[i], width/2 - height/3, j);
                    j = j + height /10;
                }
                break;
            }

            case 3:
            {
                background(0);
                strokeWeight(2);
                for(int i = 0 ; i < ab.size() ; i += 10)
                {
                    float cx = width/2;
                    float cy = height/2;
                    float f = map(smoothedAmplitude, 0, 0.5f, 0, 750);
                    float j = lerpedBuffer[i] * height/2 / 4.0f;
                    float cc = map(smoothedAmplitude, 0, 0.5f, 0, 255);
                    int msec = millis();
                    stroke(cc, 255, 255);
                    noFill();
                    float halfSize = f/2;

                    float p = random(-270 + cy/4 + j, 270 - cy/4 + j);
                    // stroke(255);
                    // square(cx + p, cy + p, f*5);
                    // square(cx - p, cy + p, f*6);

                    background(0);
                    rectMode(CENTER);
                    // fill(255);
                    // circle(cx, cy, halfH);
                    // square(p * 2, cy, f);
                    // circle(cx, cy, f);
                    square(cx, cy, f/2);
                    line(cx + halfSize/2, cy + halfSize/2, width/2 + height/4 , height/2 + height/4);
                    line(cx + halfSize/2, cy - halfSize/2, width/2 + height/4, height/2 - height/4);
                    line(cx - halfSize/2, cy - halfSize/2, width/2 - height/4, height/2 - height/4);
                    line(cx - halfSize/2, cy + halfSize/2, width/2 - height/4, height/2 + height/4);

                        if (msec > 55000)
                        {
                            background(0);
                            square(cx, cy, f/2);
                        }

                        if (msec > 10)
                        {
                            float random = random(0, 255);
                            background(0);
                            stroke(random,255,255);
                            // square(cx + p, cy + p, cx/4 + j);
                            // square(cx - p, cy + p, cx/4 + j);
                            square(cx + p, cy + p, cy/4 + j);
                            square(cx - p, cy + p, cy/4 + j);
                            square(cx - p, cy - p, cy/4 + j);
                            square(cx + p, cy - p, cy/4 + j);

                        }
                        break;
                }
                    
                strokeWeight(20);
                stroke(255);
                noFill();
                rectMode(CENTER);
                square(width/2, height/2, height/2);

                int j = height/2 - height/5;

                for(int i = 0 ; i < names.length; i ++)
                {
                    strokeWeight(2);
                    fill(50,255,255);
                    rect(width/2 - height/3, j, 150, 50);
                    fill(0);
                    textAlign(CENTER, CENTER);
                    textSize(25);
                    text(names[i], width/2 - height/3, j);
                    j = j + height /10;
                }
                break;
            }

            case 4:
            {
                background(0);

                strokeWeight(20);
                stroke(255);
                noFill();
                rectMode(CENTER);
                square(width/2, height/2, height/2);

                int j = height/2 - height/5;

                for(int i = 0 ; i < names.length; i ++)
                {
                    strokeWeight(2);
                    fill(50,255,255);
                    rect(width/2 - height/3, j, 150, 50);
                    fill(0);
                    textAlign(CENTER, CENTER);
                    textSize(25);
                    text(names[i], width/2 - height/3, j);
                    j = j + height /10;
                }

                translate(width/2, height/2);
                float cc = map(smoothedAmplitude, 0, 0.5f, 0, 255);

                beginShape(); 
                for(float i = 0; i < TWO_PI ; i += 0.1)
                {
                    float d = map(ab.get((int) i), 0, 1, 100, 200);
                    noFill();
                    stroke(cc, 255, 255);
                     
                    float x = d * cos(i);
                    float y = d * sin(i);
                    vertex(x, y);
                }
                endShape(CLOSE);

                
                break;
            }
	    }
    }
}