package hugo;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class MapPanel extends JPanel {

    DataStore ds;
    ControlUI cui;
    

    public MapPanel(DataStore ds) {
        this.ds = ds;
    }
    

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        final Color LIGHT_COLOR = new Color(150, 150, 150);
        final Color DARK_COLOR = new Color(0, 0, 0);
        final Color RED_COLOR = new Color(255, 0, 0);
        final Color BLUE_COLOR = new Color(20, 0, 247);
        final Color GREEN_COLOR = new Color(14, 163, 16);
        final Color YELLOW_COLOR= new Color(255, 215, 0);
        
        int x, y;
        int x1, y1;
        int x2, y2;

        final int circlesize = 20;
        final int ysize = 350;
        final int xsize = 700;
            

        if (ds.networkRead == true) { // Only try to plot is data has been properly read from file

            // Compute scale factor in order to keep the map in proportion when the window is resized
            int height = getHeight();
            int width = getWidth();
            double xscale = 1.0 * width / xsize;
            double yscale = 1.0 * height / ysize;
            

            //for (int i = 0; i < ds.nodes; i++) {
            x = (int) ds.robotX;
            y = (int) ds.robotY;
            
            g.fillOval(x-((circlesize)/2), height - y- (circlesize) / 2, circlesize, circlesize); 

            g.setColor(DARK_COLOR);
            
           // }

            // Draw nodes as circles
            for (int i = 0; i < ds.nodes; i++) {
                x = (int) (ds.nodeX[i] * xscale);
                y = (int) (ds.nodeY[i] * yscale);

                
                 if (ds.nodeColor[i] == 1) {
                    g.setColor(RED_COLOR);
                }   
                 
                else if(ds.nodeColor[i]==2){
                    g.setColor(GREEN_COLOR);
                }
                   
                else if(ds.nodeColor[i]==3){
                    g.setColor(YELLOW_COLOR);
                }
                    
                    else {
                    g.setColor(BLUE_COLOR);
                }

                g.drawOval(x - (circlesize / 2), height - y - circlesize / 2, circlesize, circlesize);
            }
            // Draw arcs
            for (int i = 0; i < ds.arcs; i++) {
                x1 = (int) (ds.nodeX[ds.arcStart[i] - 1] * xscale);
                y1 = (int) (ds.nodeY[ds.arcStart[i] - 1] * yscale);
                x2 = (int) (ds.nodeX[ds.arcEnd[i] - 1] * xscale);
                y2 = (int) (ds.nodeY[ds.arcEnd[i] - 1] * yscale);
                
                if(ds.arcColor[i] == 1){
                    g.setColor(RED_COLOR);
                }
                else
                    g.setColor(BLUE_COLOR);

                
                g.drawLine(x1, height - y1, x2, height - y2);
                // System.out.println("Arc "+i+": "+ds.arcStart[i]+" "+ds.arcEnd[i]);

            }
        
        }     
    } // end paintComponent
}


