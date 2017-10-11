package client.ihm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu extends JPanel implements MouseListener {

   private JButton boutonJouer;
   private JButton boutonScore;
   private IHM ihm;

   public Menu(IHM ihm) {
      setBackground(Color.cyan);

      this.boutonJouer = new JButton("Jouer");
      this.boutonScore = new JButton("Score");
      this.ihm = ihm;

      boutonJouer.setAlignmentX(Component.CENTER_ALIGNMENT);
      boutonScore.setAlignmentX(Component.CENTER_ALIGNMENT);

      boutonJouer.addMouseListener(this);
      boutonScore.addMouseListener(this);

      JPanel panelBoutons = new JPanel();
      panelBoutons.setBackground(getBackground());
      panelBoutons.setLayout(new BoxLayout(panelBoutons, BoxLayout.Y_AXIS));
      panelBoutons.add(boutonJouer);
      panelBoutons.add(Box.createRigidArea(new Dimension(100, 100)));
      panelBoutons.add(boutonScore);

      setLayout(new GridBagLayout());
      add(panelBoutons, new GridBagConstraints());
   }

   public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(boutonJouer)) {
         ihm.displayPanel(PageIHM.PARTIE);
      }
      else if (e.getSource().equals(boutonScore)) {
         ihm.displayPanel(PageIHM.SCORE);
      }
	}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}
}
