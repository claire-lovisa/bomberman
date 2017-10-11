package client.ihm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Score extends JPanel implements MouseListener {
	private JLabel score;
	private JButton btnMenu;
	private IHM ihm;
	
	public Score(IHM ihm) {
		setBackground(Color.cyan);
		
		this.ihm = ihm;
		this.btnMenu = new JButton("Retour menu");
		this.score = new JLabel("");

		this.btnMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
    this.score.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		this.btnMenu.addMouseListener(this);
		
		JPanel panelBoutons = new JPanel();
		panelBoutons.setBackground(getBackground());
		panelBoutons.setLayout(new BoxLayout(panelBoutons, BoxLayout.Y_AXIS));
		panelBoutons.add(score);
		panelBoutons.add(Box.createRigidArea(new Dimension(100, 100)));
		panelBoutons.add(btnMenu);

		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		setLayout(new GridBagLayout());
		gridBagConstraints.anchor = GridBagConstraints.CENTER;
		add(panelBoutons, gridBagConstraints);
	}
	
	public void setScore(HashMap<String,Integer> score) {
		String affichage = "<html> <h1>MEILLEURS SCORE</h1>";
		for(Map.Entry<String, Integer> entry : score.entrySet()) {
			affichage += "<b>" + entry.getKey() + "</b> : " + entry.getValue().toString() + "<br/><br/>";
		}
		affichage += "</html>";
		this.score.setText(affichage);
	}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(btnMenu)) {
			ihm.displayPanel(PageIHM.MENU);
		}
	}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}
}
